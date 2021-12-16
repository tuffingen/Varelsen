package SimulationModel;

import Shapes.Shape;

import java.util.ArrayList;

/**
 * This is a class
 * Created 2021-10-18
 *
 * @author Magnus Silverdal
 */
public class SimulationModel {
    private Cell c;
    public SimulationModel()
    {
        c = new Cell(2,2);


    }
    public void update() {
        c.update();
    }

    public ArrayList<Shape> getShapes() {
        ArrayList<Shape> shapes = new ArrayList<>();
        shapes.add(c.getShape());
        return shapes;
    }

//    public ArrayList<Sprite> getSprites() {
//        ArrayList<Sprite> sprites = new ArrayList<>();
//        sprites.add(c.getSprite());
//        return sprites;
//    }
}