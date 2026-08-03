package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.client.HandgrenadeRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HandgrenadeItem extends AbstractWeapon{

    public HandgrenadeItem(Properties properties) {
        super(properties);
    }

    @Override
    protected String animationPrefix() {
        return "handgrenade";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new HandgrenadeRenderer();
    }

    @Override
    protected void executeWeaponFire(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration) {

    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}