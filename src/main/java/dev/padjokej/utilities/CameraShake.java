package dev.padjokej.utilities;

import dev.padjokej.Phlox;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class CameraShake {
    private static final LinkedList<ShakeModule> CAMERA_SHAKE_LIST = new LinkedList<>();

    public static void shake(float intensity, float duration) {
        CAMERA_SHAKE_LIST.add(new ShakeModule(System.currentTimeMillis() + (long) (duration * 1000f), intensity));
    }

    public static Vec3 getShake() {
        final long currentTimeMillis = System.currentTimeMillis();
        Vec3 shake = Vec3.ZERO;

        RandomGenerator rng = RandomGeneratorFactory.getDefault().create();

        Iterator<ShakeModule> iterator = CAMERA_SHAKE_LIST.iterator();
        while (iterator.hasNext()) {
            ShakeModule currentShake = iterator.next();
            if (currentShake.isExpired(currentTimeMillis)) {
                iterator.remove();
                continue;
            }

            shake = shake.add(currentShake.getShake(rng, currentTimeMillis));
        }

        return shake;
    }

    public static void initialize() {
        Phlox.LOGGER.info("Starting phlox camera shake utility");
    }

    private record ShakeModule(long endTime, float intensity) {
        public Vec3 getShake(RandomGenerator rng, long systemTimeMillis) {
            float currentIntensity = intensity * (endTime - systemTimeMillis) / 1000f;

            return new Vec3(
                    (rng.nextFloat() - 0.5f) * currentIntensity,
                    (rng.nextFloat() - 0.5f) * currentIntensity,
                    (rng.nextFloat() - 0.5f) * currentIntensity);
        }

        public boolean isExpired(long systemTimeMillis) {
            return endTime <= systemTimeMillis;
        }
    }
}
