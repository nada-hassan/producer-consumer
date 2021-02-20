package mainPack;

import java.awt.*;
import java.util.Random;

public class Product implements IProduct{
    private Color color;
    private int id;

    public Product(Color color, int id){
        this.color = color;
        this.id = id;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public String toString(){
        return "product: " + color;
    }
}
