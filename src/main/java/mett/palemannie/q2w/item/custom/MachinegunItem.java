package mett.palemannie.q2w.item.custom;

import mett.palemannie.q2w.item.ModItems;
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

public class MachinegunItem extends AbstractQ2Weapon{

    public MachinegunItem(Properties pProperties) {
        super(pProperties,2,1);
    }

    @Override
    protected String animationPrefix() {
        return "machinegun";
    }

    @Override
    protected BlockEntityWithoutLevelRenderer createRenderer() {
        return new MachinegunRenderer();
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
    protected void fireWeapon(ServerLevel level, ServerPlayer player, ItemStack stack, int useTicks) {
        ServerPlayHandler.handleMachinegunShoot(player);
        ModMessages.sendToPlayer(new WeaponRecoilS2CPacket(
                player.getRandom().nextBoolean() ? 0.25f : -0.25f,
                player.getRandom().nextBoolean() ? 0.25f : -0.25f,
                player.getRandom().nextBoolean() ? 0.25f : -0.25f), player);
    }

    @Override
    protected void afterShooting(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {

    }
}
