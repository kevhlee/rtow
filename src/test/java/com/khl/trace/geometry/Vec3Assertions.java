package com.khl.trace.geometry;

import org.junit.jupiter.api.Assertions;

/**
 * Assertions for {@code Vec3}.
 *
 * @author Kevin Lee
 */
public class Vec3Assertions {

    public static void assertEqualVec(Vec3 expected, Vec3 actual, double epsilon) {
        Assertions.assertEquals(expected.getX(), actual.getX(), epsilon);
        Assertions.assertEquals(expected.getY(), actual.getY(), epsilon);
        Assertions.assertEquals(expected.getZ(), actual.getZ(), epsilon);
    }

}
