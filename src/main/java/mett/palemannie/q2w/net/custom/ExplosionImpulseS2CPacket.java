package mett.palemannie.q2w.net.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Adds a blast impulse to the locally predicted player movement. */
public class ExplosionImpulseS2CPacket {

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

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            var player = Minecraft.getInstance().player;
            if (player == null) return;

            player.setDeltaMovement(player.getDeltaMovement().add(this.impulse));
            if (this.impulse.y > 0.0D) player.setOnGround(false);
        });
        context.setPacketHandled(true);
        return true;
    }
}
