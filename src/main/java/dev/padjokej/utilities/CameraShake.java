package dev.padjokej.utilities;

import dev.padjokej.Phlox;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class CameraShake {
    private static final ConcurrentLinkedDeque<ShakeModule> CAMERA_SHAKE_LIST = new ConcurrentLinkedDeque<>();

    public static void shake(float intensity, float duration) {
        CAMERA_SHAKE_LIST.add(new ShakeModule((long) (duration * 1000f), intensity));
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

    private record ShakeModule(long duration, long endTime, float intensity) {
        public ShakeModule(long duration, float intensity) {
            this(duration, System.currentTimeMillis() + duration, intensity);
        }
        public Vec3 getShake(RandomGenerator rng, long systemTimeMillis) {
            float currentIntensity = intensity * (endTime - systemTimeMillis) / ((float) duration);

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
