package mett.palemannie.q2w.event;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.client.ClientWeaponRecoil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.CameraType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Quake2Weapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WeaponRecoilClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END) { return; }
        ClientWeaponRecoil.clientTick();
    }

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        if (minecraft.options.getCameraType() != CameraType.FIRST_PERSON) {
            return;
        }

        float partialTick = (float) event.getPartialTick();

        event.setPitch(event.getPitch() - ClientWeaponRecoil.getPitchKick(partialTick));
        event.setRoll(event.getRoll() + ClientWeaponRecoil.getRollKick(partialTick));
        event.setYaw(event.getYaw() + ClientWeaponRecoil.getYawKick(partialTick));
    }
}