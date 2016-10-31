package dataHandlers;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageColorChanger
{
    private Image image;

    public ImageColorChanger(Image image)
    {
        this.image = image;
    }

    public WritableImage invertColor()
    {
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

                Color color1 = Color.web("0xffffffff");
                Color color2 = Color.web("0x000000ff");


                if (color.equals(color1))
                {
                    writer.setColor(x, y, color2);
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
