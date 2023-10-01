package com.khl.rtow.math;

import org.junit.jupiter.api.Assertions;

/**
 * Assertions for {@code Vec3}.
 *
 * @author Kevin Lee
 */
public class VecAssertions {

    public static void assertEqualVec(Vec expected, Vec actual, double epsilon) {
        Assertions.assertEquals(expected.getX(), actual.getX(), epsilon);
        Assertions.assertEquals(expected.getY(), actual.getY(), epsilon);
        Assertions.assertEquals(expected.getZ(), actual.getZ(), epsilon);
    }

}