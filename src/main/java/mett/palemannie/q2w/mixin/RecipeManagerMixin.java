package mett.palemannie.q2w.mixin;

import mett.palemannie.q2w.util.LegacyIdMigration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {
    // Recipe-book loading stores the returned holder's current ID, including highlighted recipes.
    @Inject(method = "byKey", at = @At("RETURN"), cancellable = true)
    private void q2w$resolveLegacyRecipe(ResourceLocation id, CallbackInfoReturnable<Optional<RecipeHolder<?>>> cir) {
        if (cir.getReturnValue().isPresent()) return;
        ResourceLocation current = LegacyIdMigration.prefixedId(id);
        if (!current.equals(id)) {
            cir.setReturnValue(((RecipeManager) (Object) this).byKey(current));
        }
    }
}
