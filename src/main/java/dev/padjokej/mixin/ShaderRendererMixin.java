package dev.padjokej.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import dev.padjokej.registry.ShaderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Allows for shader rendering, by looking into the registry for shaders.
 *
 * @author padjokej (<a href="https://github.com/PadjokeJ">Github</a>)
 */
@Mixin(GameRenderer.class)
public class ShaderRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private CrossFrameResourcePool resourcePool;

    @Shadow
    @Final
    private RenderTarget mainRenderTarget;

    @Inject(
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V",
                    ordinal = 0),
            method = "render")
    private void renderShader(CallbackInfo ci) {
        ShaderRegistry.forEach(shader -> {
            if (shader.condition().get()) {
                PostChain pc = minecraft.getShaderManager()
                        .getPostChain(shader.shaderId(), LevelTargetBundle.MAIN_TARGETS);
                if (pc != null)
                    pc.process(this.mainRenderTarget, resourcePool);
            }
        });
    }
}