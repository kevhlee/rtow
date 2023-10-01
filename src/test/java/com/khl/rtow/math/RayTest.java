package com.khl.rtow.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RayTest {

    private static final double EPSILON = 1e-6;

    private Ray ray;

    @BeforeEach
    public void setUpClass() {
        ray = new Ray(new Vec(1, 0, -1), new Vec(0.5, -1, 2));
    }

    @Test
    public void testGetOrigin() {
        var actual = ray.getOrigin();
        var expected = new Vec(1, 0, -1);
        VecAssertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    public void testGetDirection() {
        var actual = ray.getDirection();
        var expected = new Vec(0.5, -1, 2);
        VecAssertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    public void testAt() {
        VecAssertions.assertEqualVec(ray.getOrigin(), ray.at(0), EPSILON);
        VecAssertions.assertEqualVec(new Vec(2, -2, 3), ray.at(2), EPSILON);
        VecAssertions.assertEqualVec(new Vec(0.25, 1.5, -4), ray.at(-1.5), EPSILON);
        VecAssertions.assertEqualVec(new Vec(1.325, -0.65, 0.3), ray.at(0.65), EPSILON);
    }

}