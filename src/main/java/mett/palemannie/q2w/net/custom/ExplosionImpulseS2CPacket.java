package mett.palemannie.q2w.net.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.network.CustomPayloadEvent;

/** Adds a blast impulse to the locally predicted player movement. */
public record ExplosionImpulseS2CPacket(Vec3 impulse) {

    public static void encode(ExplosionImpulseS2CPacket packet, FriendlyByteBuf buffer) {
        buffer.writeDouble(packet.impulse.x);
        buffer.writeDouble(packet.impulse.y);
        buffer.writeDouble(packet.impulse.z);
    }

    public static ExplosionImpulseS2CPacket decode(FriendlyByteBuf buffer) {
        return new ExplosionImpulseS2CPacket(new Vec3(
                buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
    }

    public static void handle(ExplosionImpulseS2CPacket packet, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            var player = Minecraft.getInstance().player;
            if (player == null) return;

            player.setDeltaMovement(player.getDeltaMovement().add(packet.impulse));
            if (packet.impulse.y > 0.0D) player.setOnGround(false);
        });
        context.setPacketHandled(true);
    }
}
