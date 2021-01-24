package trace.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Unit testing for {@code Vec3}.
 *
 * @author Kevin Lee
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Vec3Test {

    private static final double EPSILON = 1e-6;

    private Vec3 v;
    private Vec3 w;

    @BeforeEach
    public void setUp() {
        v = new Vec3(1, 2, 3);
        w = new Vec3(-1, 0, 1);
    }

    @Test
    @Order(1)
    public void testGetX() {
        Assertions.assertEquals(v.getX(), 1, EPSILON);
    }

    @Test
    @Order(1)
    public void testGetY() {
        Assertions.assertEquals(v.getY(), 2, EPSILON);
    }

    @Test
    @Order(1)
    public void testGetZ() {
        Assertions.assertEquals(v.getZ(), 3, EPSILON);
    }

    @Test
    @Order(2)
    public void testSetX() {
        v.setX(0);
        Assertions.assertEquals(v.getX(), 0, EPSILON);
    }

    @Test
    @Order(2)
    public void testSetY() {
        v.setY(0);
        Assertions.assertEquals(v.getY(), 0, EPSILON);
    }

    @Test
    @Order(2)
    public void testSetZ() {
        v.setZ(0);
        Assertions.assertEquals(v.getZ(), 0, EPSILON);
    }

    @Test
    public void testNeg() {
        Vec3 actual = w.neg();
        Vec3 expected = new Vec3(1, 0, -1);

        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    public void testUnit() {
        Vec3 actual = v.unit();
        Vec3 expected = v.mul(1.0 / Math.sqrt(14));

        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    public void testAdd() {
        Vec3 actual = v.add(w);
        Vec3 expected = new Vec3(0, 2, 4);

        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    public void testSub() {
        Vec3 actual = v.sub(w);
        Vec3 expected = new Vec3(2, 2, 2);

        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    public void testMul() {
        Vec3 actual = v.sub(w);
        Vec3 expected = new Vec3(2, 2, 2);

        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    public void testAddInPlace() {
        v.addInPlace(w);
        Vec3 expected = new Vec3(0, 2, 4);

        Vec3Assertions.assertEqualVec(v, expected, EPSILON);
    }

    @Test
    public void testSubInPlace() {
        v.subInPlace(w);
        Vec3 expected = new Vec3(2, 2, 2);

        Vec3Assertions.assertEqualVec(v, expected, EPSILON);
    }

    @Test
    public void testMulInPlace() {
        v.mulInPlace(w);
        Vec3 expected = new Vec3(-1, 0, 3);

        Vec3Assertions.assertEqualVec(v, expected, EPSILON);
    }

    @Test
    public void testDot() {
        double actual = v.dot(w);
        double expected = 2;

        Assertions.assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testCross() {
        Vec3 actual = v.cross(w);
        Vec3 expected = new Vec3(2, -4, 2);

        Vec3Assertions.assertEqualVec(expected, actual, EPSILON);
    }

    @Test
    public void testLength() {
        double actual = v.length();
        double expected = Math.sqrt(14);

        Assertions.assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testLengthSq() {
        double actual = v.lengthSq();
        double expected = 14;

        Assertions.assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testToString() {
        String actual = v.toString();
        String expected = "Vec3[ 1.000 2.000 3.000 ]";

        Assertions.assertEquals(expected, actual);
    }

}