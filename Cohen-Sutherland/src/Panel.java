import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Panel extends JPanel implements MouseListener {

    Line2D.Double linea;
    int xMin, xMax, yMin, yMax;

    public Panel() {
        linea = new Line2D.Double();
        this.addMouseListener(this);
    }

    public void paintArea(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int squareSize = 200;
        int x = (panelWidth - squareSize) / 2;
        int y = (panelHeight - squareSize) / 2;
        xMin = x;
        xMax = x + squareSize;
        yMin = y;
        yMax = y + squareSize;
        g2d.drawRect(x, y, squareSize, squareSize);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        this.paintArea(g2d);

        if (linea != null) {
            clippingAlgorithm(g2d, linea);
        }
    }

    public void clippingAlgorithm(Graphics2D g2d, Line2D line) {
        double x1 = line.getX1();
        double y1 = line.getY1();
        double x2 = line.getX2();
        double y2 = line.getY2();

        int point1 = bits(x1, y1);
        int point2 = bits(x2, y2);

        boolean accept = false;

        while (true) {
            if ((point1 | point2) == 0) {
                accept = true;
                break;
            } else if ((point1 & point2) != 0) {
                break;
            } else {
                double x, y;
                int pointOut = (point1 != 0) ? point1 : point2;

                if ((pointOut & 8) != 0) {
                    x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
                    y = yMax;
                } else if ((pointOut & 4) != 0) {
                    x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
                    y = yMin;
                } else if ((pointOut & 2) != 0) {
                    y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
                    x = xMax;
                } else {
                    y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
                    x = xMin;
                }

                if (pointOut == point1) {
                    x1 = x;
                    y1 = y;
                    point1 = bits(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    point2 = bits(x2, y2);
                }
            }
        }

        if (accept) {
            g2d.setColor(Color.GREEN);
            g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }

    public int bits(double x, double y) {
        int code = 0;
        if (x < xMin) {
            code |= 1;
        } else if (x > xMax) {
            code |= 2;
        }
        if (y < yMin) {
            code |= 4;
        } else if (y > yMax) {
            code |= 8;
        }
        return code;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        linea.x1 = e.getX();
        linea.y1 = e.getY();
        System.out.println(linea.x1);
        System.out.println(linea.y1);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        linea.x2 = e.getX();
        linea.y2 = e.getY();
        System.out.println(linea.x2);
        System.out.println(linea.y2);
        repaint();
    }
}
