package geometria;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main extends JFrame {

    private final DrawingPanel drawingPanel = new DrawingPanel();

    public Main() {
        this.setTitle("Układ współrzędnych");

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.setVisible(true);
        this.setSize(screenWidth, screenHeight - 60);
        this.setLocation((screenWidth - this.getWidth()) / 2, (screenHeight - this.getHeight()) / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().add(drawingPanel);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.consoleResult();
    }

    public void consoleResult() {

        Point[] arr = new Point[5];
        for (int i = 0; i < arr.length; i++)
            arr[i] = Point.randomPoint();

        //-------------------zad1-------------------

        System.out.println("---Zad1---");
        Line line1 = new Line(-200, 100, 200, 400);
        line1.rotate(-Math.random() * 180);
        whichSide(line1, arr[0]);

        //-------------------zad2-------------------

        System.out.println("---Zad2---");
        System.out.println("Punkt " + arr[4] + " należy do trójkąta:" + insideTriangle(arr[1], arr[2], arr[3], arr[4]));

        //-------------------zad3-------------------

        int numberOfPoints = (int) (Math.random() * 5 + 5);
        ArrayList<Point> list = new ArrayList<>();

        System.out.println("---Zad3---");

        for (int i = 0; i < numberOfPoints; i++) {
            Point point = Point.randomPoint();
            list.add(point);
            System.out.println(i + "." + point);
        }

        ArrayList<Point> convexHullArray = convexHull(list);

        System.out.println("Otoczka:");
        for (Point point : convexHullArray)
            System.out.println(point);

        Point mainPoint = new Point(0, 0);
        System.out.println("Punkt " + mainPoint + " wewnątrz otoczki:" + insidePolygon(convexHullArray, mainPoint));

        //-------------------zad4-------------------

        System.out.println("---Zad4---");
        Point[] crossPoints = lineAndCircle(20);

        if (crossPoints != null) {
            System.out.println("Punkty przecięcia:");
            for (Point point : crossPoints) {
                System.out.println(point);
            }
        }

        //-------------------rysowanie-------------------
        drawingPanel.toDraw(line1, arr);
    }

    public void whichSide(Line line, Point point) {
        double[] factors = line.findEquation();
        if (factors[0] * point.getX() + factors[1] > point.getY())
            System.out.println("Punkt " + point + " lezy pod prosta");
        else if (factors[0] * point.getX() + factors[1] < point.getY())
            System.out.println("Punkt " + point + " lezy nad prosta");
        else
            System.out.println("Punkt " + point + " lezy na prosta");
    }

    public ArrayList<Point> convexHull(ArrayList<Point> list) {
        sortList(list);
        ArrayList<Point> resultList = new ArrayList<>();
        double[] factors;
        boolean lower = false;
        boolean higher = false;

        resultList.add(list.get(0));

        Line line = new Line(list.get(0), list.get(1));

        for (int k = 0; k < list.size(); k++) {
            for (int i = 1; i < list.size(); i++) {

                line.setTail(list.get(i));
                if (resultList.contains(line.getTail()) || line.getTail().equals(line.getHead())) {
                    continue;
                }

                factors = line.findEquation();

                for (Point point : list) {
                    if (point == line.getHead() || point == line.getTail())
                        continue;

                    if (factors[0] * point.getX() + factors[1] > point.getY())
                        lower = true;
                    else
                        higher = true;
                }

                if (lower != higher) {
                    resultList.add(line.getTail());
                    line.setHead(line.getTail());
                    break;
                }

                lower = false;
                higher = false;
            }
        }
        return resultList;
    }

    public boolean insidePolygon(ArrayList<Point> convexHullArray, Point mainPoint) {
        Line[] lines = new Line[convexHullArray.size()];
        Line mainLine = new Line(mainPoint, new Point(1000, mainPoint.getY()));
        int counter = 0;

        for (int i = 0; i < lines.length - 1; i++)
            lines[i] = new Line(convexHullArray.get(i), convexHullArray.get(i + 1));
        lines[lines.length - 1] = new Line(convexHullArray.get(convexHullArray.size() - 1), convexHullArray.get(0));

        for (Line line : lines) {
            if (belongsToLineSegment(crossPoint(mainLine, line), line) && belongsToLineSegment(crossPoint(mainLine, line), mainLine))
                counter++;
        }

        return counter == 1;
    }

    public Point crossPoint(Line line1, Line line2) {
        double[] factors1 = line1.findEquation();
        double[] factors2 = line2.findEquation();

        if (factors1[0] == factors2[0] && factors1[1] == factors2[1]) {
            System.err.println("Nieskonczenie wiele punktow przeciecia");
            return null;
        } else if (factors1[0] == factors2[0]) {
            System.err.println("brak punktow przeciecia");
            return null;
        }

        double x = (factors2[1] - factors1[1]) / (factors1[0] - factors2[0]);
        double y = factors1[0] * x + factors1[0];

        return new Point(x, y);
    }

    void sortList(ArrayList<Point> list) {
        list.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return -1 * Double.compare(o1.getX(), o2.getX());
            }
        });
    }

    public boolean insideTriangle(Point point1, Point point2, Point point3, Point point4) {
        double bigArea = triangleArea(point1, point2, point3) * 1000000;
        bigArea = Math.round(bigArea);

        double area1 = triangleArea(point1, point2, point4);
        double area2 = triangleArea(point1, point3, point4);
        double area3 = triangleArea(point2, point3, point4);
        double area4 = (area1 + area2 + area3) * 1000000;
        area4 = Math.round(area4);

        return area4 == bigArea;
    }

    double length(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    double triangleArea(Point point1, Point point2, Point point3) {
        double length1 = length(point1, point2);
        double length2 = length(point1, point3);
        double length3 = length(point2, point3);
        double p = (length1 + length2 + length3) / 2;
        return Math.sqrt(p * (p - length1) * (p - length2) * (p - length3));
    }

    public Point[] lineAndCircle(double radius) {
        Line randomLine = new Line(Point.randomPoint(), Point.randomPoint());
        System.out.println("Punkty prostej:" + randomLine.getHead() + " i " + randomLine.getTail());
        double[] fac = randomLine.findEquation();
        double[] arguments;
        try {
            arguments = quadraticEquation(fac[0] * fac[0] + 1, 2 * fac[0] * fac[1], fac[1] * fac[1] - radius * radius);
        } catch (UnsupportedOperationException e) {
            System.out.println("Brak punktów przecięcia");
            return null;
        }

        if (arguments.length == 1)
            return new Point[]{new Point(arguments[0], fac[0] * arguments[0] + fac[1])};
        else
            return new Point[]{new Point(arguments[0], fac[0] * arguments[0] + fac[1]),
                    new Point(arguments[1], fac[0] * arguments[1] + fac[1])};
    }

    boolean belongsToStraight(Point point, Line line) {
        double[] factors = line.findEquation();
        return factors[0] * point.getX() + factors[1] == point.getY();
    }

    boolean belongsToLineSegment(Point point, Line line) {
        return belongsToStraight(point, line) &&
                Math.min(line.getHead().getX(), line.getTail().getX()) <= point.getX() &&
                Math.max(line.getHead().getX(), line.getTail().getX()) >= point.getX() &&
                Math.min(line.getHead().getY(), line.getTail().getY()) <= point.getY() &&
                Math.max(line.getHead().getY(), line.getTail().getY()) >= point.getY();
    }

    double[] quadraticEquation(double a, double b, double c) {

        double d = (b * b) - (4 * a * c);

        if (d > 0.0) {
            double x1 = (-b + Math.sqrt(d)) / (2 * a);
            double x2 = (-b - Math.sqrt(d)) / (2 * a);
            return new double[]{x1, x2};
        } else if (d == 0.0) {
            double x = -b / (2 * a);
            return new double[]{x};
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
