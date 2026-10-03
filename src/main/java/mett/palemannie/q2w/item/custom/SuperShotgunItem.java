package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
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

public class SuperShotgunItem extends AbstractQ2Weapon{

    public SuperShotgunItem(Properties properties) {
        super(properties, 31, 29);
    }

    @Override
    public net.minecraft.world.item.Item getAmmoItem() {
        return ModItems.SHELL.get();
    }

    @Override
    protected String animationPrefix() {
        return "super_shotgun";
    }

    @Override
    protected Item ammoItem() {
        return ModItems.SHELL.get();
    }

    @Override
    protected TagKey<Item> ammoTag() {
        return ShellAmmo.TAG;
    }

    @Override
    protected int ammoCostPerShot() {
        return 2;
    }

    @Override
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {

        ServerPlayHandler.handleSuperShotgunShoot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                4f,
                0f,
                player.getRandom().nextBoolean() ? 0.33f : -0.33f), player);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}
