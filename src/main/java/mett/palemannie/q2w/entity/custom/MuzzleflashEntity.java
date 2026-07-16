package mett.palemannie.q2w.entity.custom;

import mett.palemannie.q2w.block.ModBlocks;
import mett.palemannie.q2w.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MuzzleflashEntity extends Projectile {

    public MuzzleflashEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public MuzzleflashEntity(Level level, Player player){
        this(ModEntities.MUZZLE_FLASH.get(), level);
        this.setOwner(player);
        this.setPos(player.getX(), player.getEyeY()-0.2d, player.getZ());
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public boolean isNoGravity() {
        return true;
    }

    private BlockPos lightPos;

    void tryPlaceLight() {

        BlockPos origin = this.blockPosition();
        Level level = this.level();

        int[] dyOrder = {0, -1, 1};
        for (int dy : dyOrder) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos candidate = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(candidate);

                    if (state.isAir()) {
                        level.setBlock(candidate, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15), 3);
                        this.lightPos = candidate;
                        return;
                    } else if (state.getBlock() == Blocks.WATER) {
                        level.setBlock(candidate, ModBlocks.LIGHT_WATER.get().defaultBlockState(), 3);
                        this.lightPos = candidate;
                        return;
                    }
                }
            }
        }
    }

    void cleanupLight() {

        if (this.lightPos != null) {
            BlockState state = this.level().getBlockState(this.lightPos);
            if (state.getBlock() == Blocks.LIGHT) {
                this.level().removeBlock(this.lightPos, false);
            } else if (state.getBlock() == ModBlocks.LIGHT_WATER.get()) {
                this.level().setBlock(this.lightPos, Blocks.WATER.defaultBlockState(), 3);
            }
        }
    }

    void muzzleFlashHandler(){

        if (!this.level().isClientSide) {
            if (this.tickCount == 1) {
                this.tryPlaceLight();
            }

            if (this.tickCount == 4) {
                this.cleanupLight();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        muzzleFlashHandler();

        if(this.tickCount > 10) {
            this.discard();
        }
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
    }
}