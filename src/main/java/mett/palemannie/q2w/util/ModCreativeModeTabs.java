package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Quake2Weapons.MODID);

    public static final RegistryObject<CreativeModeTab> QUAKEWEAPONS_TAB = CREATIVE_MODE_TABS.register("quakeweapons_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BULLET.get()))
                    .title(Component.translatable("quakeweapons.creativetab.quakeweapons_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        /// Weapons ordered in gameplay order

                        pOutput.accept(ModItems.SHELL.get());
                        pOutput.accept(ModItems.BULLET.get());
                        pOutput.accept(ModItems.GRENADE.get());
                        pOutput.accept(ModItems.ROCKET.get());
                        pOutput.accept(ModItems.CELL.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}