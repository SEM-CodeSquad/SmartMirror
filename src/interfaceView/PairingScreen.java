package interfaceView;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Observable;
import java.util.Observer;

public class PairingScreen extends Observable implements Observer {
    private Label time;
    private Label date;
    private Label dayName;
    private ImageView qrCodeImageView;
    private GridPane dateTimeContainer;
    private GridPane qrCodeContainer;

    public PairingScreen(GridPane dateTimeContainer, GridPane qrCodeContainer) {
        this.dateTimeContainer = dateTimeContainer;
        this.qrCodeContainer = qrCodeContainer;
        Platform.runLater(this::setUp);
    }

    public void setUp() {
        time = new Label();
        time.setFont(Font.font("Agency FB", FontWeight.BOLD, 45.0));
        dateTimeContainer.add(time, 0, 0);
        GridPane.setMargin(time, new Insets(0, 0, 0, 5));


        date = new Label();
        date.setFont(Font.font("Sitka Banner", FontWeight.BOLD, 35.0));
        dateTimeContainer.add(date, 0, 1);
        GridPane.setMargin(date, new Insets(0, 0, 0, 5));
        GridPane.setValignment(date, VPos.TOP);


        dayName = new Label();
        dayName.setFont(Font.font("Sitka Banner", FontWeight.BOLD, 35.0));
        dateTimeContainer.add(dayName, 0, 1);
        GridPane.setMargin(dayName, new Insets(0, 0, 0, 168));
        GridPane.setValignment(dayName, VPos.TOP);


        qrCodeImageView = new ImageView();
        qrCodeImageView.setFitHeight(150.0);
        qrCodeImageView.setFitWidth(216.0);
        qrCodeImageView.setPreserveRatio(true);
        qrCodeImageView.setPickOnBounds(true);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setSpread(0.89);
        dropShadow.setWidth(24.11);
        dropShadow.setRadius(10.7775);
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setChoke(0.11);
        innerShadow.setHeight(52.1);
        innerShadow.setWidth(55.21);
        innerShadow.setRadius(26.3275);
        dropShadow.setInput(innerShadow);
        qrCodeImageView.setEffect(dropShadow);
        qrCodeContainer.add(qrCodeImageView, 0, 1);
        GridPane.setMargin(qrCodeImageView, new Insets(10, 0, 0, 0));
        GridPane.setHalignment(qrCodeImageView, HPos.CENTER);
        GridPane.setValignment(qrCodeImageView, VPos.TOP);

        setChanged();
        notifyObservers("PairingScreen Done!");
    }

    public Label getTime() {
        return time;
    }

    public Label getDate() {
        return date;
    }

    public Label getDayName() {
        return dayName;
    }

    private void setQrCodeImage(Image image) {
        Platform.runLater(() -> qrCodeImageView.setImage(image));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Image) {
            Image image = (Image) arg;
            setQrCodeImage(image);
        }


    }
}
