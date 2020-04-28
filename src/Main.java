import DataClasses.Images;
import DataClasses.Variables;
import GUI.DimensionChooser;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Images.loadImages();
        Dimension d = DimensionChooser.getDimension();
        Variables.setDimension(d);
        Window window = new Window();
    }
}
