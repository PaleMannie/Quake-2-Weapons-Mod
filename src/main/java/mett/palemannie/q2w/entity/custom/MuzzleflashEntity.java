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

    private void tryPlaceLight() {
        BlockPos origin = this.blockPosition();
        Level level = this.level();

        int[] dyOrder = {0, -1, 1};
        for (int dy : dyOrder) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos candidate = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(candidate);

                    if (state.isAir()) {
                        level.setBlock(candidate, ModBlocks.QUAKE_LIGHT_AIR.get().defaultBlockState(), 3);
                        this.lightPos = candidate;
                        return;
                    } else if (state.getBlock() == Blocks.WATER) {
                        level.setBlock(candidate, ModBlocks.QUAKE_LIGHT_WATER.get().defaultBlockState(), 3);
                        this.lightPos = candidate;
                        return;
                    }
                }
            }
        }
    }

    private void cleanupLight() {

        if (this.level().isClientSide) {
            return;
        }

        if (this.lightPos != null) {
            cleanupLightAround(this.lightPos);
        }

        cleanupLightAround(this.blockPosition());

        this.lightPos = null;
    }

    private void cleanupLightAround(BlockPos center) {

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    cleanupLightBlock(center.offset(dx, dy, dz));
                }
            }
        }
    }

    private void cleanupLightBlock(BlockPos pos) {

        BlockState state = this.level().getBlockState(pos);

        if (state.getBlock() == ModBlocks.QUAKE_LIGHT_AIR.get()) {
            this.level().setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        } else if (state.getBlock() == ModBlocks.QUAKE_LIGHT_WATER.get()) {
            this.level().setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
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