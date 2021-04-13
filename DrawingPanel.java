package geometria;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {

    private Vector vector;
    private Line line1;
    private Point[] arr;

    public void toDraw(Line l1, Point[] array) {
        int screenWidth = this.getSize().width;
        int screenHeight = this.getSize().height;
        vector = new Vector(screenWidth / 2.0, screenHeight / 2.0 - 30);

        this.line1 = l1;
        line1.getHead().reflectX();
        line1.getTail().reflectX();
        line1.translate(vector);

        this.arr = array;

        for (Point point : this.arr) {
            point.reflectX();
            point.translate(vector);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        //OX OY
        g.drawLine((int) vector.getX(), 0, (int) vector.getX(), 3000);
        g.drawLine(0, (int) vector.getY(), 3000, (int) vector.getY());

        /*
         *   zadanie 1 - GUI
         */

        g.drawLine((int) line1.getHead().getX(), (int) line1.getHead().getY(), (int) line1.getTail().getX(), (int) line1.getTail().getY());
        g.drawString("Line1", (int) line1.getHead().getX(), (int) line1.getHead().getY());

        g.drawRect((int) arr[0].getX(), (int) arr[0].getY(), 2, 2);
        g.drawString("Point", (int) arr[0].getX() - 5, (int) arr[0].getY() - 5);

        /*
         *   zadanie 2 - GUI
         */

        g.drawLine((int) arr[1].getX(), (int) arr[1].getY(), (int) arr[2].getX(), (int) arr[2].getY());
        g.drawLine((int) arr[1].getX(), (int) arr[1].getY(), (int) arr[3].getX(), (int) arr[3].getY());
        g.drawLine((int) arr[2].getX(), (int) arr[2].getY(), (int) arr[3].getX(), (int) arr[3].getY());

        g.drawString("Point1", (int) arr[1].getX(), (int) arr[1].getY());
        g.drawString("Point2", (int) arr[2].getX(), (int) arr[2].getY());
        g.drawString("Point3", (int) arr[3].getX(), (int) arr[3].getY());

        g.drawRect((int) arr[4].getX(), (int) arr[4].getY(), 2, 2);
        g.drawString("Point4", (int) arr[4].getX() - 5, (int) arr[4].getY() - 5);
    }
}