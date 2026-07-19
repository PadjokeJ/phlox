package dev.padjokej.registry;

import dev.padjokej.Phlox;
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
public class ShaderRegistry {
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
    }

    public static void initialize() {
        Phlox.LOGGER.info("Starting shader registry");
    }
}
