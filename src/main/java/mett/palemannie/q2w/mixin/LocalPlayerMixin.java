package mett.palemannie.q2w.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mett.palemannie.q2w.item.custom.AbstractWeapon;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/*
 *   All credit goes to byteManiaks MCQuake3 - appropriated to Forge
 *   https://github.com/bytemaniak/mcquake3
 */


@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @WrapOperation(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
    // Don't slow the player down if they are firing a Quake weapon.
    private boolean cancelWeaponSlowdown(LocalPlayer instance, Operation<Boolean> original) {
        if (instance.getUseItem().getItem() instanceof AbstractWeapon) return false;
        else return original.call(instance);
    }
}
