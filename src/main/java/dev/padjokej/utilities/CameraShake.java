package dev.padjokej.utilities;

import net.minecraft.world.phys.Vec3;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class CameraShake {
    public static long timeOfShake = -1;
    public static int durationMillis = 0;
    public static float intensity = 0;

    public static void shake(float intensity, float duration) {
        durationMillis = (int) (duration * 1000f);
        timeOfShake = System.currentTimeMillis();
        CameraShake.intensity = intensity;
    }

    public static int getRemainingMillis() {
        return (int) Math.max(timeOfShake + durationMillis - System.currentTimeMillis(), 0);
    }

    public static Vec3 getShake() {
        if (timeOfShake == -1 || getRemainingMillis() == 0) {
            return Vec3.ZERO;
        }

        float currentIntensity = lerpIntensity();

        RandomGenerator rng = RandomGeneratorFactory.getDefault().create();
        return new Vec3(
                (rng.nextFloat() - 0.5f) * currentIntensity,
                (rng.nextFloat() - 0.5f) * currentIntensity,
                (rng.nextFloat() - 0.5f) * currentIntensity);
    }

    public static float lerpIntensity() {
        float t = getRemainingMillis() / ((float) durationMillis);
        t = 1 - t;

        return t * intensity;
    }
}
