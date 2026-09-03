package mett.palemannie.q2w.net.custom;

import mett.palemannie.q2w.gui.ClientSilencerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SilencedShotsSyncS2CPacket {

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

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {

        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ClientSilencerData.setSilencedShotsLeft(this.shotsLeft);
        });

        context.setPacketHandled(true);
        return true;
    }
}