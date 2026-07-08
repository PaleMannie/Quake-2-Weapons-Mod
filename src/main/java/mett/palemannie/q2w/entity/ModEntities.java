package mett.palemannie.q2w.entity;

import mett.palemannie.q2w.Quake2Weapons;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Quake2Weapons.MODID);



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
