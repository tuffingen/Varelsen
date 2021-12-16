package ScreenRenderer;

import Shapes.*;

/**
 * This is a class
 * Created 2021-10-18
 *
 * @author Magnus Silverdal
 */
public class Screen {
    private int[] pixels;
    private int width;
    private int height;

    public Screen(int[] pixels, int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public void draw(int x, int y , int color) {
        pixels[y*width+x] = color;
    }

    public void draw(Point p, int color) {
        draw(p.getX(), p.getY(), color);
    }

    public void draw(Line l, int color) {
        draw(l.getStart(),l.getEnd(),color);
    }

    public void draw(Point start, Point end, int color) {
        int[] pixels = getPointsFromLine(start,end);
        for (int i = 0 ; i < pixels.length ; i++) {
            draw(pixels[i++],pixels[i],color);
        }
    }

    public void draw(Rectangle r, int color) {
        Line l1 =  new Line(r.getStart(),new Point(r.getEnd().getX(), r.getStart().getY()));
        Line l2 =  new Line(new Point(r.getEnd().getX(), r.getStart().getY()),r.getEnd());
        Line l3 =  new Line(r.getEnd(),new Point(r.getStart().getX(), r.getEnd().getY()));
        Line l4 =  new Line(new Point(r.getStart().getX(), r.getEnd().getY()),r.getStart());
        draw(l1, color);
        draw(l2, color);
        draw(l3, color);
        draw(l4, color);
    }

    public void fill(Rectangle r, int color) {
        for (int i = r.getStart().getY() ; i < r.getEnd().getY() ; i++) {
            for (int j = r.getStart().getX() ; j < r.getEnd().getX() ; j++) {
                draw(j,i,color);
            }
        }
    }

    public void draw(Sprite s) {
        for (int ys = 0 ; ys < s.getHeight() ; ys++) {
            for (int xs = 0 ; xs < s.getWidth() ; xs++) {
                pixels[s.getX()+xs+width*(s.getY()+ys)] = s.getPixels()[xs + ys*s.getWidth()];
            }
        }
    }

    public void fill(Circle c, int color) {
        Point p1 = new Point(c.getCenter().getX()-c.getRadius(), c.getCenter().getY()-c.getRadius());
        Point p2 = new Point(c.getCenter().getX()+c.getRadius(), c.getCenter().getY()+c.getRadius());
        for (int i = p1.getY() ; i < p2.getY() ; i++) {
            for (int j = p1.getX() ; j < p2.getX() ; j++) {
                if ((j-c.getCenter().getX())*(j-c.getCenter().getX()) + (i-c.getCenter().getY())*(i-c.getCenter().getY()) < c.getRadius()*c.getRadius())
                    draw(j,i,color);
            }
        }
    }

    private int[] getPointsFromLine(Point start, Point end) {
        int deltax = end.getX()-start.getX();
        int deltay = end.getY()-start.getY();
        if (deltax < 0)
            deltax = -deltax;
        if (deltay < 0)
            deltay = -deltay;
        int[] point = new int[(deltax>deltay?deltax+1:deltay+1)*2];
        // Bresenhams algorithm for fast lines
        int x, y;
        int dx, dy;
        int incx, incy;
        int balance;
        int count = 0;
        if (end.getX() >= start.getX()) {
            dx = end.getX() - start.getX();
            incx = 1;
        } else {
            dx = start.getX() - end.getX();
            incx = -1;
        }

        if (end.getY() >= start.getY()) {
            dy = end.getY() - start.getY();
            incy = 1;
        } else {
            dy = start.getY() - end.getY();
            incy = -1;
        }

        x = start.getX();
        y = start.getY();

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != end.getX()) {
                point[count++] = x;
                point[count++] = y;
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            point[count++] = x;
            point[count] = y;
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != end.getY()) {
                point[count++] = x;
                point[count++] = y;
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            point[count++] = x;
            point[count] = y;
        }
        return point;
    }

    public void clear() {
        for (int i = 0 ; i < pixels.length; i++) {
            pixels[i] = 0x0;
        }
    }
}
