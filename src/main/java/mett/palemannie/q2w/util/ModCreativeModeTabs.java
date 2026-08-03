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

    public static final RegistryObject<CreativeModeTab> Q2W_TAB = CREATIVE_MODE_TABS.register("q2w_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BULLET.get()))
                    .title(Component.translatable("q2w.creativetab.q2w_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        /// Weapons sorted in gameplay order

                        pOutput.accept(ModItems.SHELL.get());
                        pOutput.accept(ModItems.BULLET.get());
                        pOutput.accept(ModItems.GRENADE.get());
                        pOutput.accept(ModItems.ROCKET.get());
                        pOutput.accept(ModItems.CELL.get());
                        pOutput.accept(ModItems.SLUG.get());
                        pOutput.accept(ModItems.BLASTER.get());
                        pOutput.accept(ModItems.SHOTGUN.get());
                        pOutput.accept(ModItems.SUPER_SHOTGUN.get());
                        pOutput.accept(ModItems.MACHINEGUN.get());
                        pOutput.accept(ModItems.CHAINGUN.get());
                        pOutput.accept(ModItems.RAILGUN.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}