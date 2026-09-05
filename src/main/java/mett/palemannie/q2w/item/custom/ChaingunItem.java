package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.ChaingunRenderer;
import mett.palemannie.q2w.net.ModMessages;
import mett.palemannie.q2w.net.custom.WeaponRecoilS2CPacket;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ChaingunItem extends AbstractQ2Weapon {

    public static final int FIRE_INTERVAL_TICKS = 2;
    public static final int AFTERSPIN_TICKS = 35;
    public static final int STAGE_1_END_TICKS = 10;
    public static final int STAGE_2_END_TICKS = 22;

    public ChaingunItem(Properties properties) {

        super(properties, FIRE_INTERVAL_TICKS, 40);
    }

    @Override
    protected String animationPrefix() {

        return "chaingun";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {

        return new ChaingunRenderer();
    }

    @Override
    protected Item ammoItem() {

        return ModItems.BULLET.get();
    }

    @Override
    protected int ammoCostPerShot() {

        return 1;
    }

    @Override
    protected int shotsPerTrigger(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {

        return getShotsPerTriggerForUseTicks(useTicks);
    }

    public static int getShotsPerTriggerForUseTicks(int useTicks) {

        if (useTicks < STAGE_1_END_TICKS) { return 1; }
        if (useTicks < STAGE_2_END_TICKS) { return 2; }

        return 3;
    }

    public static float getVisualSpinSpeedRadiansPerTick(int useTicks) {

        float base = Mth.HALF_PI / 2 / FIRE_INTERVAL_TICKS;
        return base * getShotsPerTriggerForUseTicks(useTicks);
    }

    public static int useticks = 0;

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {

        useticks = useTicks;
        ServerPlayHandler.handleChaingunShoot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                player.getRandom().nextBoolean() ? 0.15f : -0.15f,
                player.getRandom().nextBoolean() ? 0.15f : -0.15f,
                player.getRandom().nextBoolean() ? 0.15f : -0.15f), player);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

        useticks = 0;
    }
}