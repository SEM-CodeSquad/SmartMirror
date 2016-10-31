package gui;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.File;

public class Note
{
    String Color;

    public Note(String color)
    {
        Color = color;
    }

    public Image applyUserColor()
    {
        File f = new File("resources/sticky-notes-md.png");
        Image image = new Image(f.toURI().toString());

        PixelReader reader = image.getPixelReader();

        int width = (int)image.getWidth();
        int height = (int)image.getHeight();

        WritableImage newImage = new WritableImage(width, height);
        PixelWriter writer = newImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // reading a pixel from src image,
                // then writing a pixel to dest image
                Color color = reader.getColor(x, y);



                Color color1 = javafx.scene.paint.Color.web("0x49f1f6ff");
                Color color3 = javafx.scene.paint.Color.web("#f44242");


                if (color.equals(color1))
                {
                    writer.setColor(x, y, color3);
                }
                else
                {
                    writer.setColor(x, y, color1);
                }
            }
        }
        return newImage;
    }
}
