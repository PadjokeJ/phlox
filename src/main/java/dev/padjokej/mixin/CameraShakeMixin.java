package dev.padjokej.mixin;

import dev.padjokej.utilities.CameraShake;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.GameRenderState;
import net.minecraft.world.phys.Vec3;
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

    @Inject(at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/client/Minecraft;isGameLoadFinished()Z",
                ordinal = 0),
            method = "render")
    private void shakeCamera(CallbackInfo ci) {
        Vec3 shake = CameraShake.getShake();

        gameRenderState.levelRenderState.cameraRenderState.pos = gameRenderState
                .levelRenderState
                .cameraRenderState
                .pos
                .add(shake);
    }
}
