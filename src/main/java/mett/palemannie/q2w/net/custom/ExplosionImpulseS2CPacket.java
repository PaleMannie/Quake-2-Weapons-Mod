package mett.palemannie.q2w.net.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

/** Adds a blast impulse to the locally predicted player movement. */
public class ExplosionImpulseS2CPacket implements CustomPacketPayload {
    public static final Type<ExplosionImpulseS2CPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("q2w", "q2w_explosion_impulse_s2_c_packet"));
    public static final StreamCodec<FriendlyByteBuf, ExplosionImpulseS2CPacket> STREAM_CODEC = StreamCodec.of((buf, packet) -> packet.toBytes(buf), ExplosionImpulseS2CPacket::new);

    @Override
    public Type<ExplosionImpulseS2CPacket> type() { return TYPE; }

    private final Vec3 impulse;

    public ExplosionImpulseS2CPacket(Vec3 impulse) {
        this.impulse = impulse;
    }

    public ExplosionImpulseS2CPacket(FriendlyByteBuf buffer) {
        this(new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.impulse.x);
        buffer.writeDouble(this.impulse.y);
        buffer.writeDouble(this.impulse.z);
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = Minecraft.getInstance().player;
            if (player == null) return;

            player.setDeltaMovement(player.getDeltaMovement().add(this.impulse));
            if (this.impulse.y > 0.0D) player.setOnGround(false);
        });
    }
}
