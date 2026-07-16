package mett.palemannie.q2w.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mett.palemannie.q2w.item.custom.AbstractWeapon;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/*
*   All credit goes to byteManiaks MCQuake3 - appropriated to Forge
*   https://github.com/bytemaniak/mcquake3
 */

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;startAttack()Z"))
    // Replaces the attack action with the use action when firing Quake weapons
    private boolean doQuakeWeaponAttack(Minecraft instance, Operation<Boolean> original) {
        if (instance.player.getMainHandItem().getItem() instanceof AbstractWeapon) {
            instance.startUseItem();
            return false;
        }

        return original.call(instance);
    }

    @WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;isDown()Z"))
    // Keeps the Quake weapons firing. Replace right click check with left click check when Quake weapons are fired to ensure that.
    private boolean isQuakeWeaponFired(KeyMapping key, Operation<Boolean> original) {
        Minecraft instance = Minecraft.getInstance();
        if (key.equals(instance.options.keyUse) &&
                instance.player.getUseItem().getItem() instanceof AbstractWeapon &&
                instance.options.keyAttack.isDown())
            return true;

        return original.call(key);
    }
}
