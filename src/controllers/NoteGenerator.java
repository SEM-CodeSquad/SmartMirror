package controllers;

import dataHandlers.TransitionAnimation;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.File;

class NoteGenerator
{
    private String msg;
    private String userStyle;
    private Image image;

    NoteGenerator(String msg, String userStyle)
    {
        this.msg = msg;
        this.userStyle = userStyle;
        setImage();
    }

    private void setImage() {
        File f = new File("resources/" + this.userStyle + ".png");
        this.image = new Image(f.toURI().toString());
    }

    void generateGraphicalNote(StackPane stackPane, TextArea textArea)
    {
        Platform.runLater(() -> generate(stackPane, textArea));
    }

    private void generate(StackPane stackPane, TextArea textArea)
    {
        try
        {
            stackPane.setOpacity(0);
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(this.image, BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

            stackPane.setBackground(new Background(backgroundImage));
            DropShadow dropShadow = new DropShadow(30, 0.5, 0, Color.BLACK);
            stackPane.setEffect(dropShadow);

            setPostMessage(textArea);
            TransitionAnimation animation = new TransitionAnimation();
            animation.fadeIn(stackPane, 2).play();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setPostMessage(TextArea textArea)
    {
        String newLine = System.getProperty("line.separator");
        if (this.msg.length() > 40) textArea.setText(newLine + this.msg);
        else textArea.setText(newLine + newLine + this.msg);
    }

    public void deleteGraphicalNote(StackPane stackPane, TextArea textArea)
    {
        TransitionAnimation animation = new TransitionAnimation();
        FadeTransition fadeTransition = animation.fadeOut(stackPane, 2);
        fadeTransition.setOnFinished(event -> {
            stackPane.setStyle("-fx-background-color: transparent;");
            textArea.setText("");
        });
        fadeTransition.play();

    }
}
