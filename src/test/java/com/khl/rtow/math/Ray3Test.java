package com.khl.rtow.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Unit testing for {@code Ray3}.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Ray3Test {

    private static final double EPSILON = 1e-6;

    private Ray3 ray;

    @BeforeEach
    public void setUpClass() {
        ray = new Ray3(new Vec3(1, 0, -1), new Vec3(0.5, -1, 2));
    }

    @Test
    @Order(1)
    public void testGetOrigin() {
        Vec3 actual = ray.getOrigin();
        Vec3 expected = new Vec3(1, 0, -1);
        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    @Order(1)
    public void testGetDirection() {
        Vec3 actual = ray.getDirection();
        Vec3 expected = new Vec3(0.5, -1, 2);
        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    @Order(2)
    public void testAt() {
        Vec3 actual = ray.at(0);
        Vec3 expected = ray.getOrigin();
        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);

        actual = ray.at(2);
        expected = new Vec3(2, -2, 3);
        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);

        actual = ray.at(-1.5);
        expected = new Vec3(0.25, 1.5, -4);
        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);

        actual = ray.at(0.65);
        expected = new Vec3(1.325, -0.65, 0.3);
        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);
    }

}