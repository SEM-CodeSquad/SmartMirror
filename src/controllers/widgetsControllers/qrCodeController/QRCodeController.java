package controllers.widgetsControllers.qrCodeController;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;
import java.util.Observer;

public class QRCodeController implements Observer {
    public AnchorPane qrCodePane;
    public ImageView qrCodeImageView;

    private void setVisible(Boolean b) {
        this.qrCodePane.setVisible(b);
    }

    private void setQrCodeImage(Image image) {
        this.qrCodeImageView.setImage(image);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Image) {
            Image image = (Image) arg;
            setQrCodeImage(image);
        }
    }
}
