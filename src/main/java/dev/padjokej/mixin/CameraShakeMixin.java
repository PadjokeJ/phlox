package dev.padjokej.mixin;

import dev.padjokej.utilities.CameraShake;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.GameRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class CameraShakeMixin {
    @Shadow
    @Final
    private GameRenderState gameRenderState;

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;isGameLoadFinished()Z",
                    ordinal = 0
            )
    )
    private void shakeCamera(CallbackInfo ci) {
        float[] shake = CameraShake.getShake();

        if (shake != null) {
            CameraRenderState cameraRenderState = gameRenderState.levelRenderState.cameraRenderState;

            gameRenderState.levelRenderState.cameraRenderState.pos = cameraRenderState.pos.add(shake[0], shake[1], shake[2]);
        }
    }
}
