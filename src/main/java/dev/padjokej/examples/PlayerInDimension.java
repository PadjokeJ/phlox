package dev.padjokej.examples;

import dev.padjokej.Phlox;
import dev.padjokej.registry.ShaderRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class PlayerInDimension implements RegisterShaderExample {
    @Override
    public void example() {
        Supplier<Boolean> condition = () -> Minecraft.getInstance().player != null
                && Minecraft.getInstance().player.level().dimension() == Level.NETHER;

        ShaderRegistry.register(condition,
                Identifier.fromNamespaceAndPath(Phlox.MOD_ID, "example"));
    }
}
