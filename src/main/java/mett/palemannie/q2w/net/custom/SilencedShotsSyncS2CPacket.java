package mett.palemannie.q2w.net.custom;

import mett.palemannie.q2w.gui.ClientSilencerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SilencedShotsSyncS2CPacket {

    private static int shotsLeft = 0;

    public SilencedShotsSyncS2CPacket(int shotsLeft) {
        SilencedShotsSyncS2CPacket.shotsLeft = shotsLeft;
    }

    public SilencedShotsSyncS2CPacket(FriendlyByteBuf buf) {
        shotsLeft = buf.readVarInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(shotsLeft);
    }

    public static SilencedShotsSyncS2CPacket decode(FriendlyByteBuf buf) {
        return new SilencedShotsSyncS2CPacket(buf.readVarInt());
    }

    public static void handle(SilencedShotsSyncS2CPacket packet, CustomPayloadEvent.Context ctx) {

        ctx.enqueueWork(() -> handleClient(packet));
        ctx.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleClient(SilencedShotsSyncS2CPacket packet) {

        ClientSilencerData.setSilencedShotsLeft(shotsLeft);
    }
}