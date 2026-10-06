package mett.palemannie.q2w.net.custom;

import mett.palemannie.q2w.gui.ClientSilencerData;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class SilencedShotsSyncS2CPacket implements CustomPacketPayload {
    public static final Type<SilencedShotsSyncS2CPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("q2w", "q2w_silenced_shots_sync_s2_c_packet"));
    public static final StreamCodec<FriendlyByteBuf, SilencedShotsSyncS2CPacket> STREAM_CODEC = StreamCodec.of((buf, packet) -> packet.toBytes(buf), SilencedShotsSyncS2CPacket::new);

    @Override
    public Type<SilencedShotsSyncS2CPacket> type() { return TYPE; }

    private final int shotsLeft;

    public SilencedShotsSyncS2CPacket(int shotsLeft) {
        this.shotsLeft = shotsLeft;
    }

    public SilencedShotsSyncS2CPacket(FriendlyByteBuf buf) {
        this.shotsLeft = buf.readVarInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarInt(this.shotsLeft);
    }

    public void handle(IPayloadContext context) {

        context.enqueueWork(() -> {
            ClientSilencerData.setSilencedShotsLeft(this.shotsLeft);
        });

    }
}
