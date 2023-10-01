package com.khl.rtow.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VecTest {

    private static final double EPSILON = 1e-6;

    private final Vec v = new Vec(1, 2, 3);
    private final Vec w = new Vec(-1, 0, 1);

    @Test
    public void testGetX() {
        Assertions.assertEquals(v.getX(), 1, EPSILON);
    }

    @Test
    public void testGetY() {
        Assertions.assertEquals(v.getY(), 2, EPSILON);
    }

    @Test
    public void testGetZ() {
        Assertions.assertEquals(v.getZ(), 3, EPSILON);
    }

    @Test
    public void testSetX() {
        v.setX(0);
        Assertions.assertEquals(v.getX(), 0, EPSILON);
    }

    @Test
    public void testSetY() {
        v.setY(0);
        Assertions.assertEquals(v.getY(), 0, EPSILON);
    }

    @Test
    public void testSetZ() {
        v.setZ(0);
        Assertions.assertEquals(v.getZ(), 0, EPSILON);
    }

    @Test
    public void testNeg() {
        VecAssertions.assertEqualVec(new Vec(1, 0, -1), w.neg(), EPSILON);
    }

    @Test
    public void testUnit() {
        VecAssertions.assertEqualVec(v.mul(1.0 / Math.sqrt(14)), v.unit(), EPSILON);
    }

    @Test
    public void testAdd() {
        VecAssertions.assertEqualVec(new Vec(0, 2, 4), v.add(w), EPSILON);
    }

    @Test
    public void testSub() {
        VecAssertions.assertEqualVec(new Vec(2, 2, 2), v.sub(w), EPSILON);
    }

    @Test
    public void testMul() {
        VecAssertions.assertEqualVec(new Vec(-1, 0, 3), v.mul(w), EPSILON);
    }

    @Test
    public void testMulScalar() {
        VecAssertions.assertEqualVec(new Vec(3, 6, 9), v.mul(3.0), EPSILON);
    }

    @Test
    public void testDot() {
        Assertions.assertEquals(2.0, v.dot(w), EPSILON);
    }

    @Test
    public void testCross() {
        VecAssertions.assertEqualVec(new Vec(2, -4, 2), v.cross(w), EPSILON);
    }

    @Test
    public void testLength() {
        Assertions.assertEquals(Math.sqrt(14.0), v.length(), EPSILON);
    }

    @Test
    public void testLengthSq() {
        Assertions.assertEquals(14.0, v.lengthSq(), EPSILON);
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("Vec3[ 1.000 2.000 3.000 ]", v.toString());
    }

}