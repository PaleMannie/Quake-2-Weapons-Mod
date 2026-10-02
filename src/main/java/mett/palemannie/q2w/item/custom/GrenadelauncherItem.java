package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.GrenadelauncherRenderer;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.WeaponRecoilS2CPacket;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.renderer.GeoItemRenderer;

import java.util.function.Consumer;

public class GrenadelauncherItem extends AbstractQ2Weapon{

    public GrenadelauncherItem(Properties pProperties) {
        super(pProperties,24,22);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private GrenadelauncherRenderer renderer;

            @Override
            public GeoItemRenderer<@NotNull GrenadelauncherItem> getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new GrenadelauncherRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public net.minecraft.world.item.Item getAmmoItem() {
        return ModItems.GRENADE.get();
    }

    @Override
    protected String animationPrefix() {
        return "grenadelauncher";
    }

    @Override
    protected Item ammoItem() {
        return ModItems.GRENADE.get();
    }

    @Override
    protected TagKey<Item> ammoTag() {
        return QWAmmoTags.GRENADES;
    }

    @Override
    protected int ammoCostPerShot() {
        return 1;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {

        ServerPlayHandler.handleGrenadeLauncherShoot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                4f,
                0f,
                player.getRandom().nextBoolean() ? 0.33f : -0.33f), player);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}
