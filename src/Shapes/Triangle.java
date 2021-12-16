package Shapes;

/**
 * 2D Triangle in screen coordinates
 * Vertices must come in clockwise order
 * Created 2021-03-31
 *
 * @author Magnus Silverdal
 */
public class Triangle {
    private Point[] vertices = new Point[3];
    private Line[] edges = new Line[3];

    public Triangle(Point p0, Point p1, Point p2) {
        vertices[0] = p0;
        vertices[1] = p1;
        vertices[2] = p2;
        sortVertices();
        edges[0] = new Line(vertices[0],vertices[1]);
        edges[1] = new Line(vertices[1],vertices[2]);
        edges[2] = new Line(vertices[2],vertices[0]);
    }

    public Line[] getEdges() {
        return edges;
    }
    public Point[] getVertices() {
        return vertices;
    }

    // Find the ordering of vertices for Bresenhamstyle fill...
    public void sortVertices() {
        int leftmost = 0;
        if (vertices[0].getX() > vertices[1].getX()) {
            leftmost = 1;
        }
        if (vertices[leftmost].getX() > vertices[2].getX()) {
            leftmost = 2;
        }
        if (leftmost == 1) {
            Point tmp = vertices[0];
            vertices[0] = vertices[1];
            vertices[1] = vertices[2];
            vertices[2] = tmp;
        }
        if (leftmost == 2) {
            Point tmp = vertices[0];
            vertices[0] = vertices[2];
            vertices[2] = vertices[1];
            vertices[1] = tmp;
        }
    }

    /*
    public void drawTriangle(Triangle t, int color) {
        Line[] triangel = t.getEdges();
        for (Line l : triangel) {
            draw(l,color);
        }
    }

    // Find the top
    // fill between the lines leading up to it
    // find the slope of the third line
    // use it to decide

    public void fillTriangle(Triangle t, int color) {
        System.out.println("--" + color);
        Line[] edges = t.getEdges();
        Point[] vertices = t.getVertices();

        int uppermost = 0;
        if (vertices[0].getY() > vertices[1].getY()) {
            uppermost = 1;
        }
        if (vertices[uppermost].getY() > vertices[2].getY()) {
            uppermost = 2;
        }

        int[] pixelsLine1;
        int[] pixelsLine2;
        int[] pixelsLine3;
        boolean[] reverse = {false,false,false};

        if (uppermost == 0) {
            pixelsLine1 = getPointsFromLine(edges[0].getStart(),edges[0].getEnd());
            pixelsLine2 = getPointsFromLine(edges[1].getStart(),edges[1].getEnd());
            pixelsLine3 = getPointsFromLine(edges[2].getStart(),edges[2].getEnd());
            reverse[2] = true;
        } else if (uppermost == 1) {
            pixelsLine1 = getPointsFromLine(edges[0].getStart(),edges[0].getEnd());
            reverse[0] = true;
            pixelsLine2 = getPointsFromLine(edges[1].getStart(),edges[1].getEnd());
            pixelsLine3 = getPointsFromLine(edges[2].getStart(),edges[2].getEnd());
            if (edges[2].getEnd().getY() < edges[2].getStart().getY()) {
                reverse[2] = true;
            }
        }
        else {
            pixelsLine1 = getPointsFromLine(edges[1].getEnd(),edges[1].getStart());
            pixelsLine2 = getPointsFromLine(edges[2].getStart(),edges[2].getEnd());
            pixelsLine3 = getPointsFromLine(edges[0].getEnd(),edges[0].getStart());
        }

        // Debug...
        /*for (int i = 0 ; i < pixelsLine1.length ; i+=2) {
            System.out.println("("+pixelsLine1[i]+","+pixelsLine1[i+1]+")");
        }
        System.out.println();
        for (int i = 0 ; i < pixelsLine2.length ; i+=2) {
            System.out.println("("+pixelsLine2[i]+","+pixelsLine2[i+1]+")");
        }
        System.out.println();
        for (int i = 0 ; i < pixelsLine3.length ; i+=2) {
            System.out.println("("+pixelsLine3[i]+","+pixelsLine3[i+1]+")");
        }
        System.out.println();*/
    // end Debug..
/*



    int index1 = 0;
    int bound1 =  pixelsLine1.length-1;
    int index2 = 0;
    int bound2 =  pixelsLine2.length-1;
    // reversing the index with slope...
        if (reverse[0]) {
        index1 = pixelsLine1.length - 1;
        bound1 = 0;
    }
        if (reverse[1]) {
        index2 = pixelsLine2.length-1;
        bound2 = 0;
    }

        while (index1 <= bound1 && index2 <= bound2) {
        System.out.println("u ("+pixelsLine1[index1]+","+pixelsLine1[index1+1]+") - ("+pixelsLine2[index2]+","+pixelsLine2[index2+1]+")");
        for (int x = pixelsLine1[index1] ; x <= pixelsLine2[index2] ; x++) {
            draw(x,pixelsLine1[index1+1],color);
        }
        while(index1 < pixelsLine1.length-3 && pixelsLine1[index1+1] == pixelsLine1[index1+3]) {
            index1+=2;
        }
        while(index2 < pixelsLine2.length-3 && pixelsLine2[index2+1] == pixelsLine2[index2+3]) {
            index2+=2;
        }
        index1+=reverse[0]?-2:2;
        index2+=reverse[1]?-2:2;
    }

    int index3 = 0;
    //while (index3 < pixelsLine3.length - 3 && pixelsLine3[index3 + 1] == pixelsLine1[index1 - 1]) {
    //    index3 += 2;
    //}
        if (edges[2].getStart().getY() > edges[2].getEnd().getY()) {
        System.out.println(index3 + " " + index2);

        while (index3 < pixelsLine3.length - 1 && index2 < pixelsLine2.length - 1 && pixelsLine3[index3 + 1] < pixelsLine2[index2 + 1]) {
            index3 += 2;
        }
        while (index3 <= pixelsLine3.length - 1 && index2 <= pixelsLine2.length - 1) {

            System.out.println("l\\ ("+pixelsLine3[index3]+","+pixelsLine3[index3+1]+") - ("+pixelsLine2[index2]+","+pixelsLine2[index2+1]+")");
            for (int x = pixelsLine3[index3]; x <= pixelsLine2[index2]; x++) {
                draw(x, pixelsLine3[index3 + 1], color);
            }
            while (index3 <= pixelsLine3.length - 3 && pixelsLine3[index3 + 1] == pixelsLine3[index3 + 3]) {
                index3 += 2;
            }
            while (index2 < pixelsLine2.length - 3 && pixelsLine2[index2 + 1] == pixelsLine2[index2 + 3]) {
                index2 += 2;
            }
            index3 += 2;
            index2 += 2;
        }
                /*System.out.println("l\\ ("+pixelsLine3[index3]+","+pixelsLine3[index3+1]+") - ("+pixelsLine2[index2]+
                        ","+pixelsLine2[index2+1]+")");
                for (int x = pixelsLine3[index3]; x <= pixelsLine2[index2]; x++) {
                    drawPixel(x, pixelsLine3[index3 + 1], color);
                }*/
  /*      System.out.println("Halt");
    }else {
        System.out.println(index1 + " " + index3 );
        while (index3 < pixelsLine3.length - 1 && index1 < pixelsLine1.length - 1 && pixelsLine3[index3 + 1] < pixelsLine1[index1 + 1]) {
            index3 += 2;
        }
        while (index3 <= pixelsLine3.length-1 && index1 <= pixelsLine1.length-1) {


            System.out.println("l/ ("+pixelsLine1[index1]+","+pixelsLine1[index1+1]+") - ("+pixelsLine3[index3]+","+pixelsLine3[index3+1]+")");
            for (int x = pixelsLine1[index1]; x <= pixelsLine3[index3]; x++) {
                draw(x, pixelsLine3[index3 + 1], color);
            }
            while (index1 < pixelsLine1.length - 3 && pixelsLine1[index1 + 1] == pixelsLine1[index1 + 3]) {
                index1 += 2;
            }
            while (index3 < pixelsLine3.length - 3 && pixelsLine3[index3 + 1] == pixelsLine3[index3 + 3]) {
                index3 += 2;
            }
            index3 += 2;
            index1 += 2;
        }
    }*/

        /*for (Line l : triangle) {
            int[] pixels = getPointsFromLine(l.getStart(),l.getEnd());
            System.out.println();
            for (int i = 0 ; i < pixels.length ; i++) {
                System.out.println("("+pixels[i]+","+pixels[i+1]+")");
                drawPixel(pixels[i++],pixels[i],color);
            }
        }*/

//}


}