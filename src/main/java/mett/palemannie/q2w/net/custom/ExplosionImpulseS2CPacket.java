package mett.palemannie.q2w.net.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

/** Adds a blast impulse to the locally predicted player movement. */
public record ExplosionImpulseS2CPacket(Vec3 impulse) implements CustomPacketPayload {
    public static final Type<ExplosionImpulseS2CPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath("q2w", "explosionimpulses2cpacket"));
    public static final StreamCodec<FriendlyByteBuf, ExplosionImpulseS2CPacket> STREAM_CODEC = StreamCodec.of((buffer, packet) -> encode(packet, buffer), ExplosionImpulseS2CPacket::decode);
    @Override
    public Type<ExplosionImpulseS2CPacket> type() { return TYPE; }

    public static void encode(ExplosionImpulseS2CPacket packet, FriendlyByteBuf buffer) {
        buffer.writeDouble(packet.impulse.x);
        buffer.writeDouble(packet.impulse.y);
        buffer.writeDouble(packet.impulse.z);
    }

    public static ExplosionImpulseS2CPacket decode(FriendlyByteBuf buffer) {
        return new ExplosionImpulseS2CPacket(new Vec3(
                buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
    }

    public static void handle(ExplosionImpulseS2CPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = Minecraft.getInstance().player;
            if (player == null) return;

            player.setDeltaMovement(player.getDeltaMovement().add(packet.impulse));
            if (packet.impulse.y > 0.0D) player.setOnGround(false);
        });

    }
}
