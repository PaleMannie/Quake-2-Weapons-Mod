package mett.palemannie.q2w.util;

import mett.palemannie.q2w.Q2WConfig;
import mett.palemannie.q2w.entity.ModEntities;
import mett.palemannie.q2w.entity.custom.*;
import mett.palemannie.q2w.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ServerPlayHandler {

    /// All in one class handling all the weapons shooting which should happen on server side

    /// helper methods

    public static float weaponSoundVolume(Player player, float normalVolume) {
        if (player instanceof ServerPlayer serverPlayer) {
            return WeaponAggroHandler.getWeaponSoundVolume(serverPlayer, normalVolume);
        }

        return normalVolume;
    }

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

        float f = -Mth.sin(yRot * ((float)Math.PI / 180f)) * Mth.cos(xRot * ((float)Math.PI / 180f));
        float f1 = -Mth.sin(xRot * ((float)Math.PI / 180f));
        float f2 =  Mth.cos(yRot * ((float)Math.PI / 180f)) * Mth.cos(xRot * ((float)Math.PI / 180f));

        projectile.setYRot(player.getYRot());
        projectile.yRotO = player.getYRot();
        projectile.setXRot(player.getXRot());
        projectile.xRotO = player.getXRot();

        projectile.shoot(f, f1, f2, velocity, inaccuracy);
    }

    private static Vec3 getMachinegunSpreadDirection(Vec3 forward, double spreadDegrees, RandomSource random) {

        if (spreadDegrees <= 0d) {
            return forward.normalize();
        }

        double spreadRad = Math.toRadians(spreadDegrees);
        double angle = random.nextDouble() * Math.PI * 2d;
        double radius = random.nextDouble() * Math.sin(spreadRad);

        Vec3 up = new Vec3(0d, 1d, 0d);
        Vec3 right = forward.cross(up);

        if (right.lengthSqr() < 1e-7d) {
            right = new Vec3(1d, 0d, 0d);
        } else {
            right = right.normalize();
        }

        up = right.cross(forward).normalize();

        Vec3 offset = right.scale(Math.cos(angle) * radius)
                .add(up.scale(Math.sin(angle) * radius));

        return forward.add(offset).normalize();
    }

    private static void spawnMuzzleFlash(ServerLevel serverLevel, ServerPlayer player, Vec3 shotDir) {

        if (!isMuzzleFlashEnabled()) { return; }

        double forwardOffset = 0.35d;
        double rightOffset = 0.25d;
        double downOffset = 0.2d;

        Vec3 up = new Vec3(0d, 1d, 0d);
        Vec3 right = shotDir.cross(up);

        if (right.lengthSqr() < 1e-7d) {

            right = new Vec3(1d, 0d, 0d);
        } else {

            right = right.normalize();
        }

        Vec3 spawnPos = player.getEyePosition()
                .add(shotDir.scale(forwardOffset))
                .add(right.scale(rightOffset))
                .add(0d, -downOffset, 0d);

        MuzzleflashEntity flash = new MuzzleflashEntity(serverLevel, player);
        flash.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        serverLevel.addFreshEntity(flash);
    }

    private static void spawnRailgunTrail(ServerLevel serverLevel, ServerPlayer player, Vec3 start, Vec3 end) {

        Vec3 axis = end.subtract(start);
        double length = axis.length();

        if (length <= 0.01d) { return; }

        Vec3 direction = axis.normalize();

        Vec3 worldUp = new Vec3(0d, 1d, 0d);
        Vec3 right = direction.cross(worldUp);

        if (right.lengthSqr() < 1e-7d) {

            right = new Vec3(1d, 0d, 0d);
        } else {

            right = right.normalize();
        }

        Vec3 up = right.cross(direction).normalize();

        final double STEP = 0.35d;
        final double SMOKE_RADIUS = 0.35d;
        final double ANGLE_STEP = 0.75d;

        int index = 0;

        for (double distance = 0d; distance <= length; distance += STEP) {

            Vec3 center = start.add(direction.scale(distance));

            serverLevel.sendParticles(player, ParticleTypes.SMOKE, true, center.x, center.y, center.z, 1, 0d, 0d, 0d, 0d);

            double angle = index * ANGLE_STEP;
            Vec3 smokeOffset = right.scale(Math.cos(angle) * SMOKE_RADIUS).add(up.scale(Math.sin(angle) * SMOKE_RADIUS));
            Vec3 smokePos = center.add(smokeOffset);

            serverLevel.sendParticles(player, ParticleTypes.END_ROD, true, smokePos.x, smokePos.y, smokePos.z, 1, 0.02d, 0.02d, 0.02d, 0d);

            index++;
        }
    }

    private record RailHit(LivingEntity target, Vec3 hitPos, double distance) {}

    private static Vec3 getOffsetShotStart(ServerPlayer player, Vec3 direction, double rightOffset, double downOffset, double forwardOffset) {
        Vec3 worldUp = new Vec3(0d, 1d, 0d);
        Vec3 right = direction.cross(worldUp);

        if (right.lengthSqr() < 1e-7d) {

            right = new Vec3(1d, 0d, 0d);
        } else {

            right = right.normalize();
        }

        return player.getEyePosition()
                .add(direction.scale(forwardOffset))
                .add(right.scale(rightOffset))
                .add(0d, -downOffset, 0d);
    }

    private static boolean hurtWithScaledKnockback(LivingEntity target, DamageSource damageSource, float damage, double knockbackScale) {

        Vec3 motionBefore = target.getDeltaMovement();

        boolean hurt = target.hurt(damageSource, damage);

        if (hurt) {

            Vec3 motionAfter = target.getDeltaMovement();
            Vec3 addedKnockback = motionAfter.subtract(motionBefore);
            Vec3 scaledMotion = motionBefore.add(addedKnockback.scale(knockbackScale));

            target.setDeltaMovement(scaledMotion);
            target.hurtMarked = true;
        }

        return hurt;
    }

    /// Weapon shooting handlers

    public static void handleMachinegunShoot(ServerPlayer player) {

        ServerLevel serverLevel = player.serverLevel();

        final double RANGE = 96d;
        final double INACCURACY_DEGREES = 2f;
        final float DAMAGE = Q2WConfigStats.MachinegunDamage;

        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 shotDir = getMachinegunSpreadDirection(look, INACCURACY_DEGREES, serverLevel.random);
        Vec3 endPos = eyePos.add(shotDir.scale(RANGE));

        BlockHitResult blockHit = serverLevel.clip(new ClipContext(eyePos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        double blockDistance = RANGE;

        if (blockHit.getType() != HitResult.Type.MISS) {

            blockDistance = blockHit.getLocation().distanceTo(eyePos);
        }

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(serverLevel, player, eyePos, endPos, new AABB(eyePos, endPos).inflate(0.35d), entity -> entity instanceof LivingEntity && entity != player && !entity.isSpectator() && entity.isPickable());

        if (entityHit != null && entityHit.getLocation().distanceTo(eyePos) < blockDistance) {

            LivingEntity target = (LivingEntity) entityHit.getEntity();
            Vec3 hitPos = entityHit.getLocation();

            hurtWithScaledKnockback(target, serverLevel.damageSources().source(ModDamageTypes.MACHINEGUN_DAMAGE, player, player), DAMAGE, 0.25d);

            if (Q2WConfig.COMMON.enableGore.get()) {

                serverLevel.sendParticles(player, ParticleTypes.LANDING_LAVA, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.25d, 0.25d, 0.25d, 0.0d);
            }

            serverLevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.05d, 0.05d, 0.05d, 0.0d);
            serverLevel.playSound(null, hitPos.x, hitPos.y, hitPos.z, ModSounds.BULLET_HIT.get(), SoundSource.NEUTRAL, 0.5f, 1f);

        } else if (blockHit.getType() != HitResult.Type.MISS) {

            Vec3 hitPos = blockHit.getLocation();
            serverLevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.05d, 0.05d, 0.05d, 0.0d);
            player.level().playSound(null, hitPos.x, hitPos.y, hitPos.z, ModSounds.BULLET_HIT.get(), SoundSource.NEUTRAL, 0.5f, 1f);
        }

        spawnMuzzleFlash(serverLevel, player, shotDir);

        player.level().playSound(null,player.getX(),player.getY(),player.getZ(),ModSounds.CHAINGUN_SHOOT.get(),SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);
    }

    public static void handleChaingunShoot(ServerPlayer player){

        ServerLevel serverLevel = player.serverLevel();

        final double RANGE = 96d;
        final double INACCURACY_DEGREES = 4f;
        final float DAMAGE = Q2WConfigStats.ChaingunDamage;

        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 shotDir = getMachinegunSpreadDirection(look, INACCURACY_DEGREES, serverLevel.random);
        Vec3 endPos = eyePos.add(shotDir.scale(RANGE));

        BlockHitResult blockHit = serverLevel.clip(new ClipContext(eyePos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        double blockDistance = RANGE;

        if (blockHit.getType() != HitResult.Type.MISS) {

            blockDistance = blockHit.getLocation().distanceTo(eyePos);
        }

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(serverLevel, player, eyePos, endPos, new AABB(eyePos, endPos).inflate(0.35d), entity -> entity instanceof LivingEntity && entity != player && !entity.isSpectator() && entity.isPickable());

        if (entityHit != null && entityHit.getLocation().distanceTo(eyePos) < blockDistance) {

            LivingEntity target = (LivingEntity) entityHit.getEntity();
            Vec3 hitPos = entityHit.getLocation();

            hurtWithScaledKnockback(target, serverLevel.damageSources().source(ModDamageTypes.MACHINEGUN_DAMAGE, player, player), DAMAGE, 0.25d);

            if (Q2WConfig.COMMON.enableGore.get()) {

                serverLevel.sendParticles(player, ParticleTypes.LANDING_LAVA, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.25d, 0.25d, 0.25d, 0.0d);
            }

            serverLevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.05d, 0.05d, 0.05d, 0.0d);
            serverLevel.playSound(null, hitPos.x, hitPos.y, hitPos.z, ModSounds.BULLET_HIT.get(), SoundSource.NEUTRAL, 0.25f, 1f);

        } else if (blockHit.getType() != HitResult.Type.MISS) {

            Vec3 hitPos = blockHit.getLocation();
            serverLevel.sendParticles(player, ParticleTypes.SMOKE, true, hitPos.x, hitPos.y, hitPos.z, 1, 0.05d, 0.05d, 0.05d, 0.0d);
            player.level().playSound(null, hitPos.x, hitPos.y, hitPos.z, ModSounds.BULLET_HIT.get(), SoundSource.NEUTRAL, 0.25f, 1f);
        }

        spawnMuzzleFlash(serverLevel, player, shotDir);

        player.level().playSound(null,player.getX(),player.getY(),player.getZ(),ModSounds.CHAINGUN_SHOOT.get(),SoundSource.PLAYERS,weaponSoundVolume(player, 0.25f), 1f);
    }

    public static void handleRailgunShoot(ServerPlayer player){

        ServerLevel serverLevel = player.serverLevel();

        final double RANGE = 128d;
        final double HITBOX_INFLATE = 0.3d;
        final float DAMAGE = Q2WConfigStats.RailgunDamage;

        final double RIGHT_OFFSET = 0.35d;
        final double DOWN_OFFSET = 0.25d;
        final double FORWARD_OFFSET = 0.45d;

        Vec3 direction = player.getLookAngle().normalize();
        Vec3 shotStart = getOffsetShotStart(player, direction, RIGHT_OFFSET, DOWN_OFFSET, FORWARD_OFFSET);
        Vec3 wantedEndPos = shotStart.add(direction.scale(RANGE));

        BlockHitResult blockHit = serverLevel.clip(new ClipContext(shotStart, wantedEndPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        Vec3 endPos = wantedEndPos;

        if (blockHit.getType() != HitResult.Type.MISS) {

            endPos = blockHit.getLocation();
        }

        double maxDistance = shotStart.distanceTo(endPos);

        AABB searchBox = new AABB(shotStart, endPos).inflate(1d);

        List<RailHit> hits = new ArrayList<>();

        for (Entity entity : serverLevel.getEntities(player, searchBox, candidate ->
                candidate instanceof LivingEntity && candidate != player && candidate.isAlive() && !candidate.isSpectator() && candidate.isPickable())) {

            LivingEntity livingTarget = (LivingEntity) entity;

            Optional<Vec3> optionalHitPos = livingTarget.getBoundingBox()
                    .inflate(HITBOX_INFLATE)
                    .clip(shotStart, endPos);

            if (optionalHitPos.isEmpty()) { continue; }

            Vec3 hitPos = optionalHitPos.get();
            double distance = shotStart.distanceTo(hitPos);

            if (distance <= maxDistance) {

                hits.add(new RailHit(livingTarget, hitPos, distance));
            }
        }

        hits.sort(Comparator.comparingDouble(RailHit::distance));

        for (RailHit hit : hits) {

            hit.target().hurt(serverLevel.damageSources().source(ModDamageTypes.RAILGUN_DAMAGE, player, player), DAMAGE);
            Vec3 hitPos = hit.hitPos();

            if (Q2WConfig.COMMON.enableGore.get()) {

                serverLevel.sendParticles(ParticleTypes.LANDING_LAVA, hitPos.x, hitPos.y, hitPos.z, 4, 0.25d, 0.25d, 0.25d, 0.0d);
            }

            serverLevel.sendParticles(ParticleTypes.END_ROD, hitPos.x, hitPos.y, hitPos.z, 3, 0.12d, 0.12d, 0.12d, 0.0d);
        }

        spawnRailgunTrail(serverLevel, player, shotStart, endPos);

        if (blockHit.getType() != HitResult.Type.MISS) {

            Vec3 blockHitPos = blockHit.getLocation();
            serverLevel.sendParticles(ParticleTypes.SMOKE, blockHitPos.x, blockHitPos.y, blockHitPos.z, 8, 0.15d, 0.15d, 0.15d, 0d);
        }

        //TODO: ganzen Railgunsound finden

        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.RAILGUN_SHOOT.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);
    }

    public static void handleBfg10kShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity

        final double RIGHT_OFFSET = 0.25d;
        final double DOWN_OFFSET = 0.10d;
        final double FORWARD_OFFSET = 0.6d;

        Vec3 direction = player.getLookAngle().normalize();
        Vec3 shotStart = getOffsetShotStart(player, direction, RIGHT_OFFSET, DOWN_OFFSET, FORWARD_OFFSET);

        Bfg10kProjectileEntity ball = new Bfg10kProjectileEntity(ModEntities.BFG10K_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(ball, player, player.getXRot(), player.getYRot(), Bfg10kProjectileEntity.SPEED_BLOCKS_PER_TICK, 0f);
        ball.setPos(shotStart);
        sevel.addFreshEntity(ball);

        Vec3 shootDir = player.getLookAngle();
        spawnMuzzleFlash(sevel, player, shootDir);

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.BFG10K_SHOOT.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);
    }

    public static void handleRocketLauncherShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity

        final double RIGHT_OFFSET = 0.25d;
        final double DOWN_OFFSET = 0.10d;
        final double FORWARD_OFFSET = 0.6d;

        Vec3 direction = player.getLookAngle().normalize();
        Vec3 shotStart = getOffsetShotStart(player, direction, RIGHT_OFFSET, DOWN_OFFSET, FORWARD_OFFSET);

        RocketProjectileEntity rocket = new RocketProjectileEntity(ModEntities.ROCKETLAUNCHER_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(rocket, player, player.getXRot(), player.getYRot(), 1.5f, 0f);
        rocket.setPos(shotStart);

        sevel.addFreshEntity(rocket);

        Vec3 shootDir = player.getLookAngle();
        spawnMuzzleFlash(sevel, player, shootDir);

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.ROCKETLAUNCHER_SHOOT.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);
    }

    public static void handleHandgrenadeThrow(ServerPlayer player, int remainingFuseTicks, float velocity) {

        ServerLevel serverLevel = player.serverLevel();
        Level level = player.level();

        HandgrenadeProjectileEntity grenade =
                new HandgrenadeProjectileEntity(ModEntities.HANDGRENADE_PROJECTILE.get(), serverLevel);

        grenade.setOwner(player);
        grenade.setFuseTicks(remainingFuseTicks);

        double forwardOffset = 0d;
        double rightOffset = 0d;
        double downOffset = 0d;

        Vec3 look = player.getLookAngle().normalize();
        Vec3 worldUp = new Vec3(0d, 1d, 0d);
        Vec3 right = look.cross(worldUp);

        if (right.lengthSqr() < 1e-7f) {
            right = new Vec3(1d, 0d, 0d);
        } else {
            right = right.normalize();
        }

        Vec3 spawnPos = player.getEyePosition()
                .add(look.scale(forwardOffset))
                .add(right.scale(rightOffset))
                .add(0d, -downOffset, 0d);

        shootFromRotationNoMomentum(grenade, player, player.getXRot(), player.getYRot(), velocity, 0f);
        grenade.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        serverLevel.addFreshEntity(grenade);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.HANDGRENADE_TOSS.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);
    }

    public static void handleHandgrenadeOvercook(ServerPlayer player) {

        ServerLevel level = player.serverLevel();

        Vec3 center = player.getBoundingBox().getCenter();

        Q2ExplosionHelper.handgrenadeExplosion((ServerLevel) level, null, player, center);
        level.sendParticles(ParticleTypes.FLAME, center.x, center.y, center.z, 20, 0, 0, 0, 0.1);
        level.playSound(null, center.x, center.y, center.z, ModSounds.EXPLOSION.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 2f), 1f);
    }

    public static void handleGrenadeLauncherShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        GrenadelauncherProjectileEntity grenade = new GrenadelauncherProjectileEntity(ModEntities.GRENADELAUNCHER_PROJECTILE.get(), sevel);

        shootFromRotationNoMomentum(grenade, player, player.getXRot(), player.getYRot(), 1f, 0f);
        sevel.addFreshEntity(grenade);

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.GRENADELAUNCHER_SHOOT.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);

        Vec3 shootDir = player.getLookAngle();
        spawnMuzzleFlash(sevel, player, shootDir);
    }

    public static void handleSuperShotgunShoot(ServerPlayer player) {

        ServerLevel sevel = player.serverLevel();
        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();

        final int PELLETS = 20;
        final double RANGE = 64d;
        final double SPREAD_H = 11d;
        final double SPREAD_V = 7d;
        final float DAMAGE_PER_PELLET = Q2WConfigStats.SuperShotgunDamage;

        for (int i = 0; i < PELLETS; i++) {

            Vec3 pelletDir = getSuperShotgunNormalizedSpreadDirection(look, SPREAD_H, SPREAD_V, sevel.random);
            Vec3 endPos = eyePos.add(pelletDir.scale(RANGE));

            BlockHitResult blockHit = sevel.clip(new ClipContext(eyePos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(sevel, player, eyePos, endPos, new AABB(eyePos, endPos).inflate(1.0), e -> e instanceof LivingEntity && e != player);

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
        sevel.playSound(null, posX, posY, posZ, ModSounds.SUPER_SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);

        ///Entity
        Vec3 shootDir = player.getLookAngle();
        spawnMuzzleFlash(sevel, player, shootDir);
    }

    public static void handleShotgunShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level level = player.level();


        ///Hitscan
        final int PELLETS = 12;
        final double RANGE = 64d;
        final double SPREAD_DEGREES = 10d;
        final float DAMAGE_PER_PELLET = Q2WConfigStats.ShotgunDamage;

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
                    new AABB(eyePos, endPos).inflate(1),
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
        level.playSound(null, posX, posY, posZ, ModSounds.SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);

        ///Entity
        Vec3 shootDir = player.getLookAngle();
        spawnMuzzleFlash(sevel, player, shootDir);
    }

    public static void handleHyperblasterShoot(ServerPlayer player){

        ServerLevel sevel = player.serverLevel();
        Level lvl = player.level();

        ///Entity
        double forwardOffset = 0.5f;
        double upOffset = 0.2f;

        Vec3 look = player.getLookAngle();
        float rightOffset = 0.25f;
        Vec3 right = look.cross(new Vec3(0, 1.5, 0)).normalize();

        double spawnX = player.getX() + right.x * rightOffset + look.x * forwardOffset;
        double spawnY = player.getEyeY() - upOffset + right.y * rightOffset + look.y * forwardOffset;
        double spawnZ = player.getZ() + right.z * rightOffset + look.z * forwardOffset;

        LaserProjectileEntity laser = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE.get(), sevel, Q2WConfigStats.HyperBlasterDamage, false);

        shootFromRotationNoMomentum(laser, player, player.getXRot(), player.getYRot(), 1f, 0f);
        laser.setPos(spawnX, spawnY, spawnZ);
        sevel.addFreshEntity(laser);

        Vec3 shootDir = player.getLookAngle();
        spawnMuzzleFlash(sevel, player, shootDir);

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.HYPERBLASTER_SHOOT.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);
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

        LaserProjectileEntity laser = new LaserProjectileEntity(ModEntities.LASER_PROJECTILE.get(), sevel, Q2WConfigStats.BlasterDamage, true);

        shootFromRotationNoMomentum(laser, player, player.getXRot(), player.getYRot(), 1f, 0f);
        laser.setPos(spawnX, spawnY, spawnZ);
        sevel.addFreshEntity(laser);

        Vec3 shootDir = player.getLookAngle();
        spawnMuzzleFlash(sevel, player, shootDir);

        ///Sound
        double posX = player.getX();
        double posY = player.getY();
        double posZ = player.getZ();
        lvl.playSound(null, posX, posY, posZ, ModSounds.BLASTER_SHOOT.get(), SoundSource.PLAYERS, weaponSoundVolume(player, 1f), 1f);
    }

    public static void playAmmoEmptySound(ServerPlayer player){

        Level level = player.level();
        level.playSound(null, player.blockPosition(), ModSounds.AMMOEMPTY.get(), SoundSource.NEUTRAL, 1f, 1f);
    }
}