package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.block.ModBlocks;
import mett.palemannie.q2w.effect.ModEffects;
import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.particle.ModParticles;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Loads pre-prefix save IDs as the current entries; subsequent saves use the new IDs. */
public final class LegacyIdMigration {
    private static final String PREFIX = "q2w_";

    private LegacyIdMigration() {}

    public static void registerAliases() {
        addAliases(ModItems.ITEMS);
        addAliases(ModBlocks.BLOCKS);
        addAliases(ModEntities.ENTITY_TYPES);
        addAliases(ModEffects.MOB_EFFECTS);
        addAliases(ModSounds.SOUND_EVENTS);
        addAliases(ModParticles.PARTICLES);
    }

    private static void addAliases(DeferredRegister<?> register) {
        for (var entry : register.getEntries()) {
            ResourceLocation current = entry.getId();
            if (current.getNamespace().equals(Quake2Weapons.MODID) && current.getPath().startsWith(PREFIX)) {
                ResourceLocation legacy = ResourceLocation.fromNamespaceAndPath(
                        Quake2Weapons.MODID, current.getPath().substring(PREFIX.length()));
                register.addAlias(legacy, current);
            }
        }
    }

    public static ResourceLocation prefixedId(ResourceLocation id) {
        if (!id.getNamespace().equals(Quake2Weapons.MODID) || id.getPath().startsWith(PREFIX)) return id;
        return ResourceLocation.fromNamespaceAndPath(Quake2Weapons.MODID, PREFIX + id.getPath());
    }
}
