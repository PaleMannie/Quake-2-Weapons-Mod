package mett.palemannie.q2w.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mett.palemannie.q2w.item.custom.AbstractWeapon;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/*
 *   All credit goes to byteManiaks MCQuake3 - appropriated to Forge
 *   https://github.com/bytemaniak/mcquake3
 */


@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"))
    // When shooting a gun, don't play the bow animation as it looks out of place
    private UseAnim cancelFirstPersonBowAnimation(ItemStack instance, Operation<UseAnim> original) {
        if (instance.getItem() instanceof AbstractWeapon) return UseAnim.NONE;
        return original.call(instance);
    }
}
