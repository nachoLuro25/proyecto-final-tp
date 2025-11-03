package entities;

import java.awt.Point;
import java.util.LinkedList;

public class Trail {

    private LinkedList<Point> points;

    public Trail() {
        this.points = new LinkedList<>();
    }

    public void addPoint(Point p) {
        points.add(new Point(p));
    }

    public LinkedList<Point> getPoints() {
        return points;
    }

    public void clear() {
        points.clear();
    }

    public boolean contains(Point point) {
        return points.contains(point);
    }

    public int size() {
        return points.size();
    }

    public Point getLastPoint() {
        return points.isEmpty() ? null : points.getLast();
    }
}
