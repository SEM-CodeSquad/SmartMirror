package smartMirror.dataModels.widgetsModels.qrCodeModels;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import net.glxn.qrgen.image.ImageType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Observable;

public class QRCode extends Observable
{
    private String clientId;

    public QRCode(String clientId)
    {
        this.clientId = clientId;
    }

    public void getQRCode()
    {
        ByteArrayOutputStream out = net.glxn.qrgen.QRCode.from(clientId).to(ImageType.PNG).withSize(200, 180).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        Image image = new Image(in);

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
                Color color3 = Color.web("#1d1d1d");


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
        setChanged();
        notifyObservers(image);
    }
}
