package geometria;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    static Point randomPoint() {
        double randomX = (Math.random() * 200 - 100); // [-100, 100]
        double randomY = (Math.random() * 200 - 100);

        return new Point(randomX, randomY);
    }

    public void translate(Vector vector) {
        this.x = x + vector.getX();
        this.y = y + vector.getY();
    }

    void reflectX() {
        this.setY(-this.getY());
    }

    void reflectY() {
        this.setX(-this.getX());
    }

    @Override
    public String toString() {
        return "(" + (int) this.getX() + "," + (int) this.getY() + ")";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
