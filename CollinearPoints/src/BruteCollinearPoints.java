import java.util.Arrays;

public class BruteCollinearPoints {

  private final Point[] points;
  private LineSegment[] lineSegments;
  private int numberOfSegments;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {

    if (!isInputValid(points)) {
      throw new IllegalArgumentException("Invalid input");
    }

    this.points = Arrays.copyOf(points, points.length);

    this.lineSegments = new LineSegment[points.length];

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

    Arrays.sort(this.points);

    for (int p = 0; p < points.length - 3; p++) {
      for (int q = p + 1; q < points.length - 2; q++) {
        for (int r = q + 1; r < points.length - 1; r++) {
          for (int s = r + 1; s < points.length; s++) {

            if (this.points[p].slopeTo(this.points[q]) == this.points[q].slopeTo(this.points[r])
                && this.points[q].slopeTo(this.points[r]) == this.points[r].slopeTo(this.points[s])) {
              this.lineSegments[numberOfSegments++] = new LineSegment(points[p], points[s]);
            }
          }
        }
      }
    }

  }

  // the number of line segments
  public int numberOfSegments() {
    return this.numberOfSegments;
  }

  // the line segments
  public LineSegment[] segments() {
    return Arrays.copyOf(this.lineSegments, numberOfSegments);
  }
}