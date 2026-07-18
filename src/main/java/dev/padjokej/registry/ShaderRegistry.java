package dev.padjokej.registry;

import dev.padjokej.Phlox;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ShaderRegistry {
    private static final List<Shader> SHADERS = new ArrayList<>();

    public static Shader register(Supplier<Boolean> condition, Identifier shaderId) {
        Shader shader = new Shader(condition, shaderId);

        SHADERS.add(shader);

        return shader;
    }

    public static void forEach(Consumer<Shader> function) {
        SHADERS.forEach(function);
    }

    public record Shader(Supplier<Boolean> condition, Identifier shaderId) {}

    public static void initialize() {
        Phlox.LOGGER.info("Starting shader registry");
    }
}
