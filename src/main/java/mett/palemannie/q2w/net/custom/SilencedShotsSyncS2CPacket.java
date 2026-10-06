package mett.palemannie.q2w.net.custom;

import mett.palemannie.q2w.gui.ClientSilencerData;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class SilencedShotsSyncS2CPacket implements CustomPacketPayload {
    public static final Type<SilencedShotsSyncS2CPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath("q2w", "q2w_silencedshotssyncs2cpacket"));
    public static final StreamCodec<FriendlyByteBuf, SilencedShotsSyncS2CPacket> STREAM_CODEC = StreamCodec.of((buffer, packet) -> packet.encode(buffer), SilencedShotsSyncS2CPacket::decode);
    @Override
    public Type<SilencedShotsSyncS2CPacket> type() { return TYPE; }

    private final int shotsLeft;

    public SilencedShotsSyncS2CPacket(int shotsLeft) {
        this.shotsLeft = shotsLeft;
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

    public static void handle(SilencedShotsSyncS2CPacket packet, IPayloadContext ctx) {

        ctx.enqueueWork(() -> handleClient(packet));

    }

    public static void handleClient(SilencedShotsSyncS2CPacket packet) {

        ClientSilencerData.setSilencedShotsLeft(packet.shotsLeft);
    }
}
