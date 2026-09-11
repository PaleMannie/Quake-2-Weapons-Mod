package mett.palemannie.q2w.net.custom;

import mett.palemannie.q2w.client.ClientWeaponRecoil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class WeaponRecoilS2CPacket {

    private static float pitchKick = 0;
    private static float rollKick = 0;
    private static float yawKick = 0;

    public WeaponRecoilS2CPacket(float pitchKick, float rollKick, float yawKick) {
        WeaponRecoilS2CPacket.pitchKick = pitchKick;
        WeaponRecoilS2CPacket.rollKick = rollKick;
        WeaponRecoilS2CPacket.yawKick = yawKick;
    }

    public WeaponRecoilS2CPacket(FriendlyByteBuf buf) {
        pitchKick = buf.readFloat();
        rollKick = buf.readFloat();
        yawKick = buf.readFloat();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(pitchKick);
        buf.writeFloat(rollKick);
        buf.writeFloat(yawKick);
    }

    public static WeaponRecoilS2CPacket decode(FriendlyByteBuf buf) {
        return new WeaponRecoilS2CPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static void handle(WeaponRecoilS2CPacket packet, CustomPayloadEvent.Context ctx) {

        ctx.enqueueWork(() -> handleClient(packet));
        ctx.setPacketHandled(true);
    }

    public static void handleClient(WeaponRecoilS2CPacket packet) {
        ClientWeaponRecoil.kick(pitchKick, rollKick, yawKick);
    }
}