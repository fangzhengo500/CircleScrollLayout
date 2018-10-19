package com.loosu.ringscrolllayout;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void calculateAngleTest() {

        double angle1 = Math.atan2(0, 1);
        double angle2 = Math.atan2(1, 0);
        double angle3 = Math.atan2(-1, 0);
        double angle4 = Math.atan2(0, -1);

        double angle5 = Math.atan2(1, 1);
        double angle6 = Math.atan2(1, -1);
        double angle7 = Math.atan2(-1, -1);
        double angle8 = Math.atan2(-1, 1);

        double degrees1 = Math.toDegrees(angle1);
        double degrees2 = Math.toDegrees(angle2);
        double degrees3 = Math.toDegrees(angle3);
        double degrees4 = Math.toDegrees(angle4);

        double degrees5 = Math.toDegrees(angle5);
        double degrees6 = Math.toDegrees(angle6);
        double degrees7 = Math.toDegrees(angle7);
        double degrees8 = Math.toDegrees(angle8);
    }
}
