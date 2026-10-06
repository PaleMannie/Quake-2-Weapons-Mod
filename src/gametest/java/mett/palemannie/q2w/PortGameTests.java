package mett.palemannie.q2w;

import io.netty.buffer.Unpooled;
import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.custom.PowershieldItem;
import mett.palemannie.q2w.net.custom.*;
import mett.palemannie.q2w.util.ItemData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("q2w")
@PrefixGameTestTemplate(false)
public final class PortGameTests {
    @GameTest(template = "empty")
    public static void itemComponents(GameTestHelper helper) {
        ItemStack shield = ModItems.POWERSHIELD_ITEM.get().getDefaultInstance();
        PowershieldItem.setActive(shield, true);
        helper.assertTrue(PowershieldItem.isActive(shield), "Shield activation must persist in components");
        ItemStack copy = shield.copy();
        PowershieldItem.setActive(copy, false);
        helper.assertTrue(PowershieldItem.isActive(shield), "Changing a copy must not mutate the original");
        helper.assertTrue(!PowershieldItem.isActive(copy), "Copied shield must deactivate");
        helper.assertTrue(!copy.has(DataComponents.CUSTOM_DATA), "Removing the last flag must restore stackability");
        ItemData.putBoolean(shield, "Q2WHasAmmo", true);
        PowershieldItem.setActive(shield, false);
        helper.assertTrue(ItemData.read(shield).getBoolean("Q2WHasAmmo"), "Removing a flag must preserve other data");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void networkPayloads(GameTestHelper helper) {
        roundTrip(helper, WeaponRecoilS2CPacket.STREAM_CODEC, new WeaponRecoilS2CPacket(2.5f, -0.75f, 0.5f));
        roundTrip(helper, ExplosionImpulseS2CPacket.STREAM_CODEC, new ExplosionImpulseS2CPacket(new Vec3(-2.5, 4, 0.125)));
        roundTrip(helper, SilencedShotsSyncS2CPacket.STREAM_CODEC, new SilencedShotsSyncS2CPacket(300));
        helper.succeed();
    }

    private static <T> void roundTrip(GameTestHelper helper, StreamCodec<FriendlyByteBuf, T> codec, T packet) {
        FriendlyByteBuf first = new FriendlyByteBuf(Unpooled.buffer());
        FriendlyByteBuf second = new FriendlyByteBuf(Unpooled.buffer());
        try {
            codec.encode(first, packet);
            byte[] expected = new byte[first.readableBytes()];
            first.getBytes(0, expected);
            T decoded = codec.decode(first);
            helper.assertTrue(first.readableBytes() == 0, "Decoder must consume the entire packet");
            codec.encode(second, decoded);
            byte[] actual = new byte[second.readableBytes()];
            second.readBytes(actual);
            helper.assertTrue(java.util.Arrays.equals(expected, actual), "Payload round trip changed the wire data");
        } finally {
            first.release();
            second.release();
        }
    }

    @GameTest(template = "empty")
    public static void registeredEntities(GameTestHelper helper) {
        for (var holder : ModEntities.ENTITY_TYPES.getEntries()) {
            var entity = holder.get().create(helper.getLevel());
            helper.assertTrue(entity != null, "Entity factory failed: " + holder.getId());
            helper.assertTrue(entity.getType() == holder.get(), "Entity type mismatch: " + holder.getId());
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void recipesLoaded(GameTestHelper helper) {
        for (String name : java.util.List.of("blaster", "shotgun", "super_shotgun", "machinegun", "chaingun",
                "grenadelauncher", "rocketlauncher", "hyperblaster", "railgun", "bfg10k", "bullet", "cell", "grenade", "rocket", "shell", "slug",
                "powershield_smelting", "rebreather_smelting", "silencer_smelting")) {
            helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath("q2w", "q2w_" + name)).isPresent(),
                    "Recipe did not load: " + name);
        }
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void legacySaveMigration(GameTestHelper helper) {
        var level = helper.getLevel();
        for (var registry : java.util.List.of(
                net.minecraft.core.registries.BuiltInRegistries.ITEM,
                net.minecraft.core.registries.BuiltInRegistries.BLOCK,
                net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE,
                net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT,
                net.minecraft.core.registries.BuiltInRegistries.SOUND_EVENT,
                net.minecraft.core.registries.BuiltInRegistries.PARTICLE_TYPE)) {
            for (var id : registry.keySet()) {
                if (!id.getNamespace().equals("q2w") || !id.getPath().startsWith("q2w_")) continue;
                var old = ResourceLocation.fromNamespaceAndPath("q2w", id.getPath().substring(4));
                helper.assertTrue(registry.get(old) == registry.get(id), "Legacy alias failed: " + old);
            }
        }
        for (var item : ModItems.ITEMS.getEntries()) {
            ItemStack original = item.get().getDefaultInstance();
            original.setCount(Math.min(3, original.getMaxStackSize()));
            ItemData.putBoolean(original, "MigrationMarker", true);
            var tag = (net.minecraft.nbt.CompoundTag) original.save(level.registryAccess());
            tag.putString("id", "q2w:" + item.getId().getPath().substring(4));
            ItemStack loaded = ItemStack.parseOptional(level.registryAccess(), tag);
            helper.assertTrue(ItemStack.matches(original, loaded), "Legacy item lost data: " + item.getId());
            var saved = (net.minecraft.nbt.CompoundTag) loaded.save(level.registryAccess());
            helper.assertTrue(saved.getString("id").equals(item.getId().toString()), "Item did not save current ID");
        }
        var block = mett.palemannie.q2w.block.ModBlocks.QUAKE_LIGHT_AIR.get();
        var blockTag = net.minecraft.nbt.NbtUtils.writeBlockState(block.defaultBlockState());
        blockTag.putString("Name", "q2w:quake_light_air");
        var loadedBlock = net.minecraft.nbt.NbtUtils.readBlockState(
                level.holderLookup(net.minecraft.core.registries.Registries.BLOCK), blockTag);
        helper.assertTrue(loadedBlock.is(block), "Legacy block palette failed");
        var entity = ModEntities.HANDGRENADE_PROJECTILE.get().create(level);
        var entityTag = new net.minecraft.nbt.CompoundTag();
        entity.save(entityTag);
        entityTag.putString("id", "q2w:handgrenade_projectile");
        var loadedEntity = net.minecraft.world.entity.EntityType.loadEntityRecursive(entityTag, level, e -> e);
        helper.assertTrue(loadedEntity != null && loadedEntity.getType() == entity.getType(), "Legacy entity failed");
        var effect = new net.minecraft.world.effect.MobEffectInstance(
                mett.palemannie.q2w.effect.ModEffects.QUAD_DAMAGE, 123, 1);
        var effectTag = (net.minecraft.nbt.CompoundTag) effect.save();
        effectTag.putString("id", "q2w:quad_damage_effect");
        var loadedEffect = net.minecraft.world.effect.MobEffectInstance.load(effectTag);
        helper.assertTrue(loadedEffect != null && loadedEffect.getEffect().equals(effect.getEffect())
                && loadedEffect.getDuration() == 123 && loadedEffect.getAmplifier() == 1, "Legacy effect failed");
        var recipeTag = new net.minecraft.nbt.CompoundTag();
        var recipes = new net.minecraft.nbt.ListTag();
        recipes.add(net.minecraft.nbt.StringTag.valueOf("q2w:rocket"));
        recipeTag.put("recipes", recipes);
        recipeTag.put("toBeDisplayed", recipes.copy());
        var book = new net.minecraft.stats.ServerRecipeBook();
        book.fromNbt(recipeTag, level.getRecipeManager());
        var savedBook = book.toNbt();
        helper.assertTrue(savedBook.getList("recipes", 8).getString(0).equals("q2w:q2w_rocket"), "Legacy recipe unlock failed");
        helper.assertTrue(savedBook.getList("toBeDisplayed", 8).getString(0).equals("q2w:q2w_rocket"), "Legacy recipe highlight failed");
        helper.assertTrue(level.getRecipeManager().byKey(ResourceLocation.parse("quakeweapons:rocket")).isEmpty(),
                "Migration must not redirect QW recipes");
        helper.succeed();
    }
}
