package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.RailgunRenderer;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.WeaponRecoilS2CPacket;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.renderer.GeoItemRenderer;

import java.util.function.Consumer;

public class RailgunItem extends AbstractQ2Weapon{

    public RailgunItem(Properties properties) {
        super(properties, 36, 34);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private RailgunRenderer renderer;

            @Override
            public GeoItemRenderer<@NotNull RailgunItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new RailgunRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public net.minecraft.world.item.Item getAmmoItem() {
        return ModItems.SLUG.get();
    }

    @Override
    protected String animationPrefix() {
        return "railgun";
    }

    @Override
    protected Item ammoItem() {
        return ModItems.SLUG.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return 1;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {

        ServerPlayHandler.handleRailgunShoot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                5f,
                0f,
                player.getRandom().nextBoolean() ? 0.33f : -0.33f), player);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}
