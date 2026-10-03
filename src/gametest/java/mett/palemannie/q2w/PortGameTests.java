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
                "grenadelauncher", "rocketlauncher", "hyperblaster", "railgun", "bfg10k", "bullet", "cell", "grenade")) {
            helper.assertTrue(helper.getLevel().getRecipeManager().byKey(ResourceLocation.fromNamespaceAndPath("q2w", name)).isPresent(),
                    "Recipe did not load: " + name);
        }
        helper.succeed();
    }
}
