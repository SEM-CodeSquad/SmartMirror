package smartMirror.controllers.widgetsControllers.qrCodeController;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Pucci @copyright on 06/12/2016.
 *         Class responsible for updating the QRCodeView
 */
public class QRCodeController implements Observer
{
    public AnchorPane qrCodePane;
    public ImageView qrCodeImageView;

    /**
     * Method responsible for setting the QR code image
     *
     * @param image QR code image to be set
     */
    private void setQrCodeImage(Image image)
    {
        this.qrCodeImageView.setImage(image);
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof Image)
        {
            Image image = (Image) arg;
            setQrCodeImage(image);
        }
    }
}
