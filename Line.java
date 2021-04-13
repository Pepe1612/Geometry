package geometria;

public class Line {

    private Point head;
    private Point tail;

    public Line(Point head, Point tail) {
        this.head = head;
        this.tail = tail;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.head = new Point(x1, y1);
        this.tail = new Point(x2, y2);
    }

    void translate(Vector vector) {
        this.head.setX(head.getX() + vector.getX());
        this.head.setY(head.getY() + vector.getY());
        this.tail.setX(tail.getX() + vector.getX());
        this.tail.setY(tail.getY() + vector.getY());
    }

    void rotate(double angle) {
        Vector tmp = new Vector(-this.head.getX(), -this.head.getY());
        this.translate(tmp);

        double radians = Math.toRadians(angle);
        this.tail.setX(Math.round(tail.getX() * Math.cos(radians) - (tail.getY() * Math.sin(radians))));
        this.tail.setY(Math.round(tail.getY() * Math.cos(radians) + (tail.getX() * Math.sin(radians))));

        tmp.setX(-tmp.getX());
        tmp.setY(-tmp.getY());
        this.translate(tmp);
    }

    double[] findEquation() {
        double A, B; //line factors
        A = (this.head.getY() - this.tail.getY()) / (this.head.getX() - this.tail.getX());
        B = this.head.getY() - A * this.head.getX();

        return new double[]{A, B};
    }

    @Override
    public String toString() {
        return "Line{" + "head=" + head + ", tail=" + tail + '}';
    }

    public Point getHead() {
        return head;
    }

    public Point getTail() {
        return tail;
    }

    public void setHead(Point newHead) {
        this.head = newHead;
    }

    public void setTail(Point newTail) {
        this.tail = newTail;
    }
}
