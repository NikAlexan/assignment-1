package com.nikitavassilenko.assignment1.closest;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public record Point(double x, double y) {
    }

    public record Result(Point p1, Point p2, double dist) {
    }

    public static Result findClosest(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }

        Point[] sortedByX = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));

        Point[] aux = new Point[points.length];
        return closestRecursive(sortedByX, aux, 0, points.length - 1);
    }

    private static Result closestRecursive(Point[] ptsByX, Point[] aux, int left, int right) {
        if (right - left == 1) {
            return new Result(ptsByX[left], ptsByX[right], dist(ptsByX[left], ptsByX[right]));
        }
        if (left == right) {
            return new Result(ptsByX[left], ptsByX[left], Double.POSITIVE_INFINITY);
        }

        int mid = (left + right) >>> 1;
        Result leftRes = closestRecursive(ptsByX, aux, left, mid);
        Result rightRes = closestRecursive(ptsByX, aux, mid + 1, right);

        Result best = leftRes.dist < rightRes.dist ? leftRes : rightRes;
        double d = best.dist;
        double midX = ptsByX[mid].x;

        mergeByY(ptsByX, aux, left, mid, right);

        int stripSize = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(ptsByX[i].x - midX) < d) {
                aux[stripSize++] = ptsByX[i];
            }
        }

        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (aux[j].y - aux[i].y) < d; j++) {
                double dist = dist(aux[i], aux[j]);
                if (dist < d) {
                    d = dist;
                    best = new Result(aux[i], aux[j], dist);
                }
            }
        }

        return best;
    }

    private static void mergeByY(Point[] pts, Point[] aux, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (pts[i].y <= pts[j].y) {
                aux[k++] = pts[i++];
            } else {
                aux[k++] = pts[j++];
            }
        }
        while (i <= mid) aux[k++] = pts[i++];
        while (j <= right) aux[k++] = pts[j++];
        System.arraycopy(aux, left, pts, left, right - left + 1);
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }
}
