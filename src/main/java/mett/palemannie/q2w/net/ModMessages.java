package mett.palemannie.q2w.net;

import mett.palemannie.q2w.Quake2Weapons;
import mett.palemannie.q2w.net.custom.SilencedShotsSyncS2CPacket;
import mett.palemannie.q2w.net.custom.WeaponRecoilS2CPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class ModMessages {

    private static int PacketID = 0;
    private static int id(){
        return PacketID++;
    }
    final static int version = 1;

    public static final SimpleChannel INSTANCE = ChannelBuilder.named(Identifier.fromNamespaceAndPath(Quake2Weapons.MODID, "messages"))
            .networkProtocolVersion(version)
            .clientAcceptedVersions(((status, version1) -> true))
            .serverAcceptedVersions(((status, version1) -> true))
            .simpleChannel();

    public static void register(){

        INSTANCE.messageBuilder(SilencedShotsSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SilencedShotsSyncS2CPacket::decode)
                .encoder(SilencedShotsSyncS2CPacket::encode)
                .consumerMainThread(SilencedShotsSyncS2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(WeaponRecoilS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(WeaponRecoilS2CPacket::decode)
                .encoder(WeaponRecoilS2CPacket::encode)
                .consumerMainThread(WeaponRecoilS2CPacket::handle)
                .add();
    }

    public static void sendToServer(Object message){
        INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }

    public static <MSG> void sendToTrackingEntityAndSelf(MSG message, LivingEntity entity) {
        INSTANCE.send(message, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(entity));
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer entity) {
        INSTANCE.send(message, PacketDistributor.PLAYER.with(entity));
    }
}
