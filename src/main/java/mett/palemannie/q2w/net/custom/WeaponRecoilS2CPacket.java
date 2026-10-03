package mett.palemannie.q2w.net.custom;

import mett.palemannie.q2w.client.ClientWeaponRecoil;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class WeaponRecoilS2CPacket implements CustomPacketPayload {
    public static final Type<WeaponRecoilS2CPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("q2w", "weapon_recoil_s2_c_packet"));
    public static final StreamCodec<FriendlyByteBuf, WeaponRecoilS2CPacket> STREAM_CODEC = StreamCodec.of((buf, packet) -> packet.toBytes(buf), WeaponRecoilS2CPacket::new);

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
        this.pitchKick = buf.readFloat();
        this.rollKick = buf.readFloat();
        this.yawKick = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(this.pitchKick);
        buf.writeFloat(this.rollKick);
        buf.writeFloat(this.yawKick);
    }

    public void handle(IPayloadContext context) {

        context.enqueueWork(() -> {
            ClientWeaponRecoil.kick(this.pitchKick, this.rollKick, this.yawKick);
        });

    }
}
