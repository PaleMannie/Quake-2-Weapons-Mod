package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.HyperblasterRenderer;
import mett.palemannie.q2w.item.client.MachinegunRenderer;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.WeaponRecoilS2CPacket;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HyperblasterItem extends AbstractQ2Weapon {

    public static final int FIRE_INTERVAL_TICKS = 2;

    public static final float DRUM_SPIN_HZ = 1.66f;
    public static final float DRUM_SPIN_RADIANS_PER_TICK = (net.minecraft.util.Mth.TWO_PI * DRUM_SPIN_HZ / 20f);
    public static final int DRUM_RETURN_TICKS = 38;

    public HyperblasterItem(Properties pProperties) {
        super(pProperties,FIRE_INTERVAL_TICKS,40);
    }

    @Override
    protected String animationPrefix() {
        return "hyperblaster";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new HyperblasterRenderer();
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
                player.getRandom().nextBoolean() ? 0.15f : -0.15f,
                player.getRandom().nextBoolean() ? 0.15f : -0.15f,
                player.getRandom().nextBoolean() ? 0.15f : -0.15f), player);
    }
    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}
