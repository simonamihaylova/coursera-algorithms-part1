import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

  private final Point[] points;
  private LineSegment[] lineSegments;
  private int numberOfSegments;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {

    if (!isInputValid(points)) {
      throw new IllegalArgumentException("Invalid input");
    }

    this.points = Arrays.copyOf(points, points.length);

    lineSegments = new LineSegment[points.length * points.length];

    calculateLineSegments();

  }

  private boolean isInputValid(Point[] p) {
    if (p == null) {
      return false;
    }

    for (Point point : p) {
      if (point == null) {
        return false;
      }
    }

    // checks if there is a repeated point
    for (int i = 0; i < p.length - 1; i++) {
      for (int j = i + 1; j < p.length; j++) {
        if (p[i].compareTo(p[j]) == 0) {
          return false;
        }
      }
    }

    return true;
  }

  private void calculateLineSegments() {

    Point[] pointsBySlope = Arrays.copyOf(this.points, this.points.length);

    for (int i = 0; i < points.length; i++) {
      Arrays.sort(pointsBySlope, points[i].slopeOrder());

      List<Point> currentLineSegments = new ArrayList<>();
      currentLineSegments.add(points[i]);
      double previousSlope = Double.NEGATIVE_INFINITY;

      for (int j = 1; j < pointsBySlope.length; j++) {

        double currentSlope = points[i].slopeTo(pointsBySlope[j]);

        if (currentSlope == previousSlope) {
          currentLineSegments.add(pointsBySlope[j - 1]);
        } else {

          if (currentLineSegments.size() >= 3) {
            currentLineSegments.add(pointsBySlope[j - 1]);
            addLineSegment(currentLineSegments, points[i]);
          }

          currentLineSegments.subList(1, currentLineSegments.size()).clear();
        }

        previousSlope = currentSlope;
      }

      if (currentLineSegments.size() >= 3) {
        currentLineSegments.add(pointsBySlope[pointsBySlope.length - 1]);
        addLineSegment(currentLineSegments, points[i]);
      }
    }

  }

  private void addLineSegment(List<Point> currentLineSegments, Point startingPoint) {
    Point minPointBySlope = currentLineSegments.stream().min(Point::compareTo).get();

    Point maxPointBySlope = currentLineSegments.stream().max(Point::compareTo).get();

    if (startingPoint.compareTo(minPointBySlope) == 0) {
      this.lineSegments[numberOfSegments++] = new LineSegment(minPointBySlope, maxPointBySlope);
    }
  }

  // the number of line segments
  public int numberOfSegments() {
    return numberOfSegments;
  }

  // the line segments
  public LineSegment[] segments() {
    return Arrays.copyOf(lineSegments, numberOfSegments, LineSegment[].class);
  }
}
