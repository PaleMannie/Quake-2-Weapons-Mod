package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.client.BlasterRenderer;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.WeaponRecoilS2CPacket;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.renderer.GeoItemRenderer;

import java.util.function.Consumer;

public class BlasterItem extends AbstractQ2Weapon {

    public BlasterItem(Properties properties) {
        super(properties, 10, 8);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private BlasterRenderer renderer;

            @Override
            public GeoItemRenderer<@NotNull BlasterItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new BlasterRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    protected String animationPrefix() {
        return "blaster";
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {

        ServerPlayHandler.handleBlasterShoot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                2f,                                                   //pitch
                0f,                                                            //roll
                player.getRandom().nextBoolean() ? 0.25f : -0.25f), player);   //yaw

    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}
