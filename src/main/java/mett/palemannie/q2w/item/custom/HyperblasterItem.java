package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.HyperblasterRenderer;
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

public class HyperblasterItem extends AbstractQ2Weapon {

    public HyperblasterItem(Properties pProperties) {
        super(pProperties,FIRE_INTERVAL_TICKS,40);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private HyperblasterRenderer renderer;

            @Override
            public GeoItemRenderer<@NotNull HyperblasterItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new HyperblasterRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public net.minecraft.world.item.Item getAmmoItem() {
        return ModItems.CELL.get();
    }

    public static final int FIRE_INTERVAL_TICKS = 2;
    public static final float DRUM_SPIN_HZ = 1.51f;
    public static final float DRUM_SPIN_RADIANS_PER_TICK = (net.minecraft.util.Mth.TWO_PI * DRUM_SPIN_HZ / 20f);

    public static final int DRUM_RETURN_TICKS = 38;

    @Override
    protected String animationPrefix() {
        return "hyperblaster";
    }

    @Override
    protected Item ammoItem() {
        return ModItems.CELL.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return 1;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {

        ServerPlayHandler.handleHyperblasterShoot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                player.getRandom().nextBoolean() ? 0.2f : -0.2f,
                player.getRandom().nextBoolean() ? 0.2f : -0.2f,
                player.getRandom().nextBoolean() ? 0.2f : -0.2f), player);
    }
    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}
