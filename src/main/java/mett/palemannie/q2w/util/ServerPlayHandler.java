package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.entity.client.LaserProjectileModel;
import mett.palemannie.q2w.entity.custom.*;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class ServerPlayHandler {

    /// All in one class handling all the weapons shooting which should happen on server side

    /// helper functions

    private static boolean isMuzzleFlashEnabled(){
        return Q2WConfig.COMMON.enableMuzzleFlash.get();
    }

    private static Vec3 getShotgunNormalizedSpreadDirection(Vec3 look, double spreadDegrees, RandomSource random) {

        double spreadRad = Math.toRadians(spreadDegrees);
        double angle = random.nextDouble() * Math.PI * 2;
        double radius = random.nextDouble() * Math.sin(spreadRad);

        Vec3 up = new Vec3(0, 1, 0);
        Vec3 right = look.cross(up).normalize();
        up = right.cross(look).normalize();

        Vec3 offset = right.scale(Math.cos(angle) * radius).add(up.scale(Math.sin(angle) * radius));

        return look.add(offset).normalize();
    }

    private static Vec3 getSuperShotgunNormalizedSpreadDirection(Vec3 forward, double spreadH, double spreadV, RandomSource random) {

        double yaw = Math.toRadians((random.nextDouble() - 0.5) * 2 * spreadH);
        double pitch = Math.toRadians((random.nextDouble() - 0.5) * 2 * spreadV);

        Vec3 up = new Vec3(0, 1, 0);
        Vec3 right = forward.cross(up).normalize();
        up = right.cross(forward).normalize();

        Vec3 dir = forward
                .add(right.scale(Math.tan(yaw)))
                .add(up.scale(Math.tan(pitch)));

        return dir.normalize();
    }

    private static void shootFromRotationNoMomentum(Projectile projectile, ServerPlayer player, float xRot, float yRot, float velocity, float inaccuracy) {

        projectile.setOwner(player);
        projectile.setPos(player.getEyePosition().x, player.getEyeY() - 0.2d, player.getEyePosition().z);

        float f = -Mth.sin(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));
        float f1 = -Mth.sin(xRot * ((float)Math.PI / 180F));
        float f2 =  Mth.cos(yRot * ((float)Math.PI / 180F)) * Mth.cos(xRot * ((float)Math.PI / 180F));

        projectile.setYRot(player.getYRot());
        projectile.yRotO = player.getYRot();
        projectile.setXRot(player.getXRot());
        projectile.xRotO = player.getXRot();

        projectile.shoot(f, f1, f2, velocity, inaccuracy);
    }

    private static Vec3 getMachinegunSpreadDirection(Vec3 forward, double spreadDegrees, RandomSource random) {
        if (spreadDegrees <= 0.0D) {
            return forward.normalize();
        }

        double spreadRad = Math.toRadians(spreadDegrees);
        double angle = random.nextDouble() * Math.PI * 2.0D;
        double radius = random.nextDouble() * Math.sin(spreadRad);

        Vec3 up = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 right = forward.cross(up);

        if (right.lengthSqr() < 1.0E-7D) {
            right = new Vec3(1.0D, 0.0D, 0.0D);
        } else {
            right = right.normalize();
        }

        up = right.cross(forward).normalize();

        Vec3 offset = right.scale(Math.cos(angle) * radius)
                .add(up.scale(Math.sin(angle) * radius));

        return forward.add(offset).normalize();
    }

    private static void spawnMuzzleFlash(ServerLevel serverLevel, ServerPlayer player, Vec3 shotDir) {
        if (!isMuzzleFlashEnabled()) {
            return;
        }

        double forwardOffset = 0.35D;
        double rightOffset = 0.25D;
        double downOffset = 0.20D;

        Vec3 up = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 right = shotDir.cross(up);

        if (right.lengthSqr() < 1.0E-7D) {
            right = new Vec3(1.0D, 0.0D, 0.0D);
        } else {
            right = right.normalize();
        }

        Vec3 spawnPos = player.getEyePosition()
                .add(shotDir.scale(forwardOffset))
                .add(right.scale(rightOffset))
                .add(0.0D, -downOffset, 0.0D);

        MuzzleflashEntity flash = new MuzzleflashEntity(serverLevel, player);
        flash.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        serverLevel.addFreshEntity(flash);
    }

    /// Weapon shooting handlers

    public static void handleMachinegunShoot(ServerPlayer serverPlayer) {

        ServerLevel serverLevel = serverPlayer.serverLevel();

        final double RANGE = 96d;
        final float DAMAGE = Q2WConfigStats.MachinegunDamage;

        Vec3 eyePos = serverPlayer.getEyePosition();
        Vec3 look = serverPlayer.getLookAngle();

        final double INACCURACY_DEGREES = 2f;
        Vec3 shotDir = getMachinegunSpreadDirection(look, INACCURACY_DEGREES, serverLevel.random);

        Vec3 endPos = eyePos.add(shotDir.scale(RANGE));

        BlockHitResult blockHit = serverLevel.clip(new ClipContext(eyePos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, serverPlayer));

        double blockDistance = RANGE;

        if (blockHit.getType() != HitResult.Type.MISS) {
            blockDistance = blockHit.getLocation().distanceTo(eyePos);
        }

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(serverLevel, serverPlayer, eyePos, endPos, new AABB(eyePos, endPos).inflate(0.35d), entity -> entity instanceof LivingEntity && entity != serverPlayer && !entity.isSpectator() && entity.isPickable());

        if (entityHit != null && entityHit.getLocation().distanceTo(eyePos) < blockDistance) {
            LivingEntity target = (LivingEntity) entityHit.getEntity();

            target.hurt(serverLevel.damageSources().source(ModDamageTypes.MACHINEGUN_DAMAGE, serverPlayer, serverPlayer), DAMAGE);

            Vec3 hitPos = entityHit.getLocation();

            if (Q2WConfig.COMMON.enableGore.get()) {
                serverLevel.sendParticles(serverPlayer, ParticleTypes.LANDING_LAVA, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.25d, 0.25d, 0.25d, 0.0d);
            }

            serverLevel.sendParticles(serverPlayer, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.05d, 0.05d, 0.05d, 0.0d);

        } else if (blockHit.getType() != HitResult.Type.MISS) {
            Vec3 hitPos = blockHit.getLocation();

            serverLevel.sendParticles(serverPlayer, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.05d, 0.05d, 0.05d, 0.0d);
        }

        spawnMuzzleFlash(serverLevel, serverPlayer, shotDir);

        serverLevel.playSound(null,serverPlayer.getX(),serverPlayer.getY(),serverPlayer.getZ(),ModSounds.CHAINGUN_SHOOT.get(),SoundSource.PLAYERS,1.0f, 1.0f);
    }

    public static void handleChaingunShoot(ServerPlayer player){

    }

    public static void handleRailgunShoot(ServerPlayer player){

    }

    public static void handleBfg10kShoot(ServerPlayer player){

    }

    public static void handleRocketLauncherShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.2f + right.y + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look.z * forwardOffset;

        RocketProjectileEntity rocket = new RocketProjectileEntity(ModEntities.ROCKETLAUNCHER_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(rocket, player, player.getXRot(), player.getYRot(), 1f, 0.0f);
        sevel.addFreshEntity(rocket);

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.ROCKETLAUNCHER_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleHandgrenadeShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        HandgrenadeProjectileEntity grenade = new HandgrenadeProjectileEntity(ModEntities.HANDGRENADE_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(grenade, player, player.getXRot(), player.getYRot(), 0.8f, 0.0f);
        sevel.addFreshEntity(grenade);

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.HANDGRENADE_TOSS.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleGrenadeLauncherShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        GrenadelauncherProjectileEntity grenade = new GrenadelauncherProjectileEntity(ModEntities.GRENADELAUNCHER_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(grenade, player, player.getXRot(), player.getYRot(), 0.8f, 0.0f);
        sevel.addFreshEntity(grenade);

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.GRENADELAUNCHER_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleSuperShotgunShoot(ServerPlayer player) {

        ServerLevel sevel = player.serverLevel();
        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        final int PELLETS = 20;
        final double RANGE = 64.0;
        final double SPREAD_H = 11.0;
        final double SPREAD_V = 7.0;
        final float DAMAGE_PER_PELLET = Q2WConfigStats.SuperShotgunDamage;

        for (int i = 0; i < PELLETS; i++) {
            Vec3 pelletDir = getSuperShotgunNormalizedSpreadDirection(look, SPREAD_H, SPREAD_V, sevel.random);
            Vec3 endPos = eyePos.add(pelletDir.scale(RANGE));

            BlockHitResult blockHit = sevel.clip(new ClipContext(
                    eyePos, endPos,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            ));

            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                    sevel, player, eyePos, endPos,
                    new AABB(eyePos, endPos).inflate(1.0),
                    e -> e instanceof LivingEntity && e != player
            );

            if (entityHit != null && (blockHit == null || entityHit.getLocation().distanceTo(eyePos) < blockHit.getLocation().distanceTo(eyePos))) {
                LivingEntity target = (LivingEntity) entityHit.getEntity();
                target.hurt(sevel.damageSources().source(ModDamageTypes.SUPER_SHOTGUN_DAMAGE, player, player), DAMAGE_PER_PELLET);

                Vec3 hitPos = entityHit.getLocation();

                if(Q2WConfig.COMMON.enableGore.get()){
                sevel.sendParticles(player, ParticleTypes.LANDING_LAVA, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.5d, 0.5d, 0.5d, 0d); }

                sevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.5d, 0.5d, 0.5d, 0d);
            }
            else if (blockHit != null && blockHit.getType() != HitResult.Type.MISS) {
                Vec3 hitPos = blockHit.getLocation();
                sevel.sendParticles(ParticleTypes.SMOKE, hitPos.x, hitPos.y, hitPos.z,
                        1, 0.1, 0.1, 0.1, 0.0);
            }
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        sevel.playSound(null, posX, posY, posZ, ModSounds.SUPER_SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look1 = player.getLookAngle();
        Vec3 right = look1.cross(new Vec3(0, 1.5f, 0)).normalize();

        double spawnX = player.getX() + right.x+ look1.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y + look1.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look1.z * forwardOffset;

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }
    }

    public static void handleShotgunShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level level = player.level();


        ///Hitscan
        final int PELLETS = 12;
        final float DAMAGE_PER_PELLET = Q2WConfigStats.ShotgunDamage;
        final double RANGE = 64.0;
        final double SPREAD_DEGREES = 10.0;

        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        for (int i = 0; i < PELLETS; i++) {

            Vec3 pelletDir = getShotgunNormalizedSpreadDirection(look, SPREAD_DEGREES, level.random);
            Vec3 endPos = eyePos.add(pelletDir.scale(RANGE));

            BlockHitResult blockHit = level.clip(new ClipContext(
                    eyePos, endPos,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            ));

            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                    level, player, eyePos, endPos,
                    new AABB(eyePos, endPos).inflate(1.0),
                    e -> e instanceof LivingEntity && e != player
            );

            if (entityHit != null && (blockHit == null || entityHit.getLocation().distanceTo(eyePos) < blockHit.getLocation().distanceTo(eyePos))) {
                LivingEntity target = (LivingEntity) entityHit.getEntity();
                target.hurt(level.damageSources().source(ModDamageTypes.SHOTGUN_DAMAGE, player, player), DAMAGE_PER_PELLET);

                Vec3 hitPos = entityHit.getLocation();

                if(Q2WConfig.COMMON.enableGore.get()){
                sevel.sendParticles(player, ParticleTypes.LANDING_LAVA, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.5d, 0.5d, 0.5d, 0d);}

                sevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.5d, 0.5d, 0.5d, 0d);
            }

            else if (blockHit != null && blockHit.getType() != HitResult.Type.MISS) {

                Vec3 hitPos = blockHit.getLocation();
                sevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.1d, 0.1d, 0.1d, 0d);
            }
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        level.playSound(null, posX, posY, posZ, ModSounds.SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);

        ///Entity
        double forwardOffset = 0.2;

        Vec3 look1 = player.getLookAngle();
        Vec3 right = look1.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look1.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y + look1.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look1.z * forwardOffset;

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }
    }

    public static void handleHyperblasterShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        /*double forwardOffset = 0.2;

        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 0, 0)).normalize();

        double spawnX = player.getX() + right.x+ look.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z + look.z * forwardOffset;

        HyperblasterProjectileEntity supernail = new HyperblasterProjectileEntity(ModEntities.HYPERBLASTER_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(supernail, player, player.getXRot(), player.getYRot(), 1f, 0.0f);
        sevel.addFreshEntity(supernail);

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }*/

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.HYPERBLASTER_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void handleBlasterShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        double forwardOffset = 0.3f;

        Vec3 look = player.getLookAngle();
        float rightOffset = 0.3f;
        Vec3 right = look.cross(new Vec3(0, 1.5, 0)).normalize();

        double spawnX = player.getX() + right.x * rightOffset + look.x * forwardOffset;
        double spawnY = player.getEyeY() - 0.25 + right.y * rightOffset + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z * rightOffset + look.z * forwardOffset;

        LaserProjectileEntity laser = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE.get(), sevel, Q2WConfigStats.BlasterDamage);

        shootFromRotationNoMomentum(laser, player, player.getXRot(), player.getYRot(), 1f, 0.0f);
        laser.setPos(spawnX, spawnY, spawnZ);
        sevel.addFreshEntity(laser);

        if(isMuzzleFlashEnabled()){

            MuzzleflashEntity flash = new MuzzleflashEntity(sevel, player);
            flash.setPos(spawnX, spawnY, spawnZ);

            sevel.addFreshEntity(flash);
        }

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.BLASTER_SHOOT.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    public static void playAmmoEmptySound(ServerPlayer player){

        Level level = player.level();
        level.playSound(null, player.blockPosition(), ModSounds.AMMOEMPTY.get(), SoundSource.NEUTRAL, 1f, 1f);
    }
}