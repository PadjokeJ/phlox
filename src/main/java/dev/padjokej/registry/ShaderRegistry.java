package dev.padjokej.registry;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import dev.padjokej.Phlox;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.resources.Identifier;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A registry which holds shaders that will be applied to the game on condition
 *
 * @author padjokej (<a href="https://github.com/PadjokeJ">Github</a>)
 */
@Environment(EnvType.CLIENT)
public final class ShaderRegistry {
    // This is a set to prevent a shader from being initialized twice
    private static final Set<Shader> SHADERS = new HashSet<>();

    /**
     * Register a shader into the registry
     *
     * @param condition The condition for the shader to be rendered
     * @param shaderId  The id of the shader within the mod's resources
     * @return The {@link Shader} object
     */
    public static Shader register(Supplier<Boolean> condition, Identifier shaderId) {
        Shader shader = new Shader(condition, shaderId);

        SHADERS.add(shader);

        return shader;
    }

    /**
     * Register a shader that will always be applied
     *
     * @param shaderId The id of the shader within the mod's resources
     * @return The {@link Shader} object
     */
    public static Shader registerAlwaysOn(Identifier shaderId) {
        Shader shader = new Shader(() -> true, shaderId);

        SHADERS.add(shader);

        return shader;
    }

    /**
     * Loop over each shader that is registered
     *
     * @param consumer The action to apply to each shader within the registry
     */
    public static void forEach(Consumer<Shader> consumer) {
        SHADERS.forEach(consumer);
    }

    /**
     * An immutable shader object
     *
     * @param condition The condition for the shader to be rendered
     * @param shaderId  The id of the shader within the mod's resources
     */
    public record Shader(Supplier<Boolean> condition, Identifier shaderId) {
        public void process(RenderTarget renderTarget, GraphicsResourceAllocator resourceAllocator, ShaderManager shaderManager) {
            if (!condition.get()) return;

            PostChain postChain = shaderManager.getPostChain(shaderId, LevelTargetBundle.MAIN_TARGETS);
            postChain.process(renderTarget, resourceAllocator);
        }
    }

    public static void initialize() {
        Phlox.LOGGER.info("Starting shader registry");
    }
}
