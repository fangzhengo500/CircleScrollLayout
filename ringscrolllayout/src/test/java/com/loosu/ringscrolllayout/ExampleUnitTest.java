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

        double degrees1 = Math.toDegrees(angle1);
        double degrees2 = Math.toDegrees(angle2);
        double degrees3 = Math.toDegrees(angle3);
    }
}