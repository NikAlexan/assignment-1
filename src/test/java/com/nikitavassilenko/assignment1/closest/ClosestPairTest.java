package com.nikitavassilenko.assignment1.closest;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClosestPairTest {
    @Test
    void testTwoPoints() {
        ClosestPair.Point p1 = new ClosestPair.Point(0, 0);
        ClosestPair.Point p2 = new ClosestPair.Point(3, 4); // distance 5
        ClosestPair.Result r = ClosestPair.findClosest(new ClosestPair.Point[]{p1, p2});
        assertEquals(5.0, r.dist(), 1e-9);
    }

    @Test
    void testThreePoints() {
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(1, 1),
                new ClosestPair.Point(5, 5)
        };
        ClosestPair.Result r = ClosestPair.findClosest(pts);

        assertEquals(Math.sqrt(2), r.dist(), 1e-9);
    }

    @Test
    void testRandomSmallMatchesBruteForce() {
        Random rnd = new Random(42);
        ClosestPair.Point[] pts = new ClosestPair.Point[50];
        for (int i = 0; i < pts.length; i++) {
            pts[i] = new ClosestPair.Point(rnd.nextDouble(100), rnd.nextDouble(100));
        }
        ClosestPair.Result fast = ClosestPair.findClosest(pts);
        ClosestPair.Result slow = bruteForce(pts);

        assertEquals(slow.dist(), fast.dist(), 1e-9);
    }

    private static ClosestPair.Result bruteForce(ClosestPair.Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        ClosestPair.Point p1 = null, p2 = null;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double d = Math.hypot(pts[i].x() - pts[j].x(), pts[i].y() - pts[j].y());
                if (d < best) {
                    best = d;
                    p1 = pts[i];
                    p2 = pts[j];
                }
            }
        }
        return new ClosestPair.Result(p1, p2, best);
    }

    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> ClosestPair.findClosest(null));
        assertThrows(IllegalArgumentException.class, () -> ClosestPair.findClosest(new ClosestPair.Point[]{new ClosestPair.Point(0, 0)}));
    }
}
