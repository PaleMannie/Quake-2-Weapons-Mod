package mett.palemannie.q2w.net.custom;

import mett.palemannie.q2w.client.ClientWeaponRecoil;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class WeaponRecoilS2CPacket implements CustomPacketPayload {
    public static final Type<WeaponRecoilS2CPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath("q2w", "weaponrecoils2cpacket"));
    public static final StreamCodec<FriendlyByteBuf, WeaponRecoilS2CPacket> STREAM_CODEC = StreamCodec.of((buffer, packet) -> packet.encode(buffer), WeaponRecoilS2CPacket::decode);
    @Override
    public Type<WeaponRecoilS2CPacket> type() { return TYPE; }

    private final float pitchKick;
    private final float rollKick;
    private final float yawKick;

    public WeaponRecoilS2CPacket(float pitchKick, float rollKick, float yawKick) {
        this.pitchKick = pitchKick;
        this.rollKick = rollKick;
        this.yawKick = yawKick;
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

    public static void handle(WeaponRecoilS2CPacket packet, IPayloadContext ctx) {

        ctx.enqueueWork(() -> handleClient(packet));

    }

    public static void handleClient(WeaponRecoilS2CPacket packet) {
        ClientWeaponRecoil.kick(packet.pitchKick, packet.rollKick, packet.yawKick);
    }
}
