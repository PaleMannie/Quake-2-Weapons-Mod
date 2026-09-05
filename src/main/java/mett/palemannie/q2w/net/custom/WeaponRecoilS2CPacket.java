package mett.palemannie.q2w.net.custom;

import mett.palemannie.q2w.client.ClientWeaponRecoil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WeaponRecoilS2CPacket {

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

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ClientWeaponRecoil.kick(this.pitchKick, this.rollKick, this.yawKick);
        });

        context.setPacketHandled(true);
        return true;
    }
}