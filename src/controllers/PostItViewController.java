package controllers;

import dataHandlers.TransitionAnimation;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Pucci on 22/11/2016.
 */
public class PostItViewController implements Observer {
    public StackPane postPanes;

    private StackPane standard;
    private PostItsController standardController;

    private StackPane blue;
    private PostItsController bluedController;

    private StackPane green;
    private PostItsController greenController;

    private StackPane yellow;
    private PostItsController yellowController;

    private StackPane pink;
    private PostItsController pinkController;

    private StackPane purple;
    private PostItsController purpleController;

    private StackPane orange;
    private PostItsController orangeController;

    private TransitionAnimation animation;

    public PostItViewController() {
        Platform.runLater(this::build);
    }

    private void build() {
        standardController = loadView("/interfaceView/PostItTable.fxml")
                .getController();
        this.standard = standardController.getPostPane();
        setOpacity(this.standard, false);

        bluedController = loadView("/interfaceView/PostItTable.fxml")
                .getController();
        this.blue = bluedController.getPostPane();
        setOpacity(this.blue, false);

        greenController = loadView("/interfaceView/PostItTable.fxml")
                .getController();
        this.green = greenController.getPostPane();
        setOpacity(this.green, false);

        purpleController = loadView("/interfaceView/PostItTable.fxml")
                .getController();
        this.purple = purpleController.getPostPane();
        setOpacity(this.purple, false);

        orangeController = loadView("/interfaceView/PostItTable.fxml")
                .getController();
        this.orange = orangeController.getPostPane();
        setOpacity(this.orange, false);

        yellowController = loadView("/interfaceView/PostItTable.fxml")
                .getController();
        this.yellow = yellowController.getPostPane();
        setOpacity(this.yellow, false);

        pinkController = loadView("/interfaceView/PostItTable.fxml")
                .getController();
        this.pink = pinkController.getPostPane();
        setOpacity(this.pink, false);

        this.animation = new TransitionAnimation();
        this.animation.transitionAnimation(15, 5, this.standard, this.blue, this.green, this.purple, this.orange,
                this.pink, this.yellow);
        this.animation.playSeqAnimation();


    }

    private FXMLLoader loadView(String resource) {
        FXMLLoader myLoader = null;
        try {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            myLoader.setController(new PostItsController());
            Parent loadScreen = myLoader.load();
            this.postPanes.getChildren().add(loadScreen);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return myLoader;
    }

    private void setOpacity(StackPane stackPane, boolean b) {
        if (b) {
            stackPane.setOpacity(1.0);
        } else {
            stackPane.setOpacity(0.0);
        }
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
