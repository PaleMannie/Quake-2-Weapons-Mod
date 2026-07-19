package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
import mett.palemannie.q2w.item.client.Bfg10kRenderer;
import mett.palemannie.q2w.sound.ModSounds;
import mett.palemannie.q2w.util.ServerPlayHandler;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Bfg10kItem extends AbstractQ2Weapon {

    private static final int FIRE_INTERVAL_TICKS = 50;
    private static final int RELEASE_COOLDOWN_TICKS = 50;
    private static final int WINDUP_TICKS = 16;
    private static final int AMMO_COST = 50;

    public Bfg10kItem(Properties properties) {
        super(properties, FIRE_INTERVAL_TICKS, RELEASE_COOLDOWN_TICKS);
    }

    @Override
    protected String animationPrefix() {
        return "bfg10k";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new Bfg10kRenderer();
    }

    @Override
    protected Item ammoItem() {
        return ModItems.CELL.get();
    }

    @Override
    protected int ammoCostPerShot() {
        return AMMO_COST;
    }

    @Override
    protected boolean shouldAttemptFire(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        if (useTicks == 0) {
            level.playSound(player, player.getOnPos(), ModSounds.BFG10K_SHOOT.get(), SoundSource.PLAYERS);
        }

        if (useTicks < WINDUP_TICKS) {
            return false;
        }

        return (useTicks - WINDUP_TICKS) % fireIntervalTicks() == 0;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleBfg10kShoot(player);
    }
}