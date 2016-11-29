package controllers.widgetsControllers.postItsController;

import dataHandlers.animations.TransitionAnimation;
import dataModels.applicationModels.Preferences;
import dataModels.widgetsModels.postItsModels.PostItAction;
import dataModels.widgetsModels.postItsModels.PostItNote;
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
public class PostItViewController extends Observable implements Observer {
    public StackPane postPanes;

    private StackPane standard;
    private PostItsController standardController;

    private StackPane blue;
    private PostItsController blueController;

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
        standardController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        this.standard = standardController.getPostPane();
        setOpacity(this.standard, false);
        standardController.setTableColor("standard");
        this.addObserver(standardController);
        standardController.addObserver(this);

        blueController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        this.blue = blueController.getPostPane();
        setOpacity(this.blue, false);
        blueController.setTableColor("blue");
        this.addObserver(blueController);
        blueController.addObserver(this);

        greenController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        this.green = greenController.getPostPane();
        setOpacity(this.green, false);
        greenController.setTableColor("green");
        this.addObserver(greenController);
        greenController.addObserver(this);

        purpleController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        this.purple = purpleController.getPostPane();
        setOpacity(this.purple, false);
        purpleController.setTableColor("purple");
        this.addObserver(purpleController);
        purpleController.addObserver(this);

        orangeController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        this.orange = orangeController.getPostPane();
        setOpacity(this.orange, false);
        orangeController.setTableColor("orange");
        this.addObserver(orangeController);
        orangeController.addObserver(this);

        yellowController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        this.yellow = yellowController.getPostPane();
        setOpacity(this.yellow, false);
        yellowController.setTableColor("yellow");
        this.addObserver(yellowController);
        yellowController.addObserver(this);

        pinkController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        this.pink = pinkController.getPostPane();
        setOpacity(this.pink, false);
        pinkController.setTableColor("pink");
        this.addObserver(pinkController);
        pinkController.addObserver(this);

        this.animation = new TransitionAnimation();
        this.animation.transitionAnimation(15, 5, this.standard, this.blue, this.green, this.purple, this.orange,
                this.pink, this.yellow);
        this.animation.playSeqAnimation();


    }

    private synchronized void setVisible(boolean b) {
        Platform.runLater(() -> this.postPanes.setVisible(b));
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

    private synchronized void notifyObs(Object arg) {
        setChanged();
        notifyObservers(arg);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof PostItNote) {
            Thread thread = new Thread(() -> {
                PostItNote postItNote = (PostItNote) arg;
                notifyObs(postItNote);
            });
            thread.start();
        } else if (arg instanceof PostItAction) {
            Thread thread = new Thread(() -> {
                PostItAction postItAction = (PostItAction) arg;
                notifyObs(postItAction);
            });
            thread.start();
        } else if (arg instanceof PostItsController) {
            Thread thread = new Thread(() -> {
                PostItsController postItsController = (PostItsController) arg;
                notifyObs(postItsController);
            });
            thread.start();
        } else if (arg.equals("success")) {
            Thread thread = new Thread(() -> notifyObs("success"));
            thread.start();
        } else if (arg instanceof Preferences && ((Preferences) arg).getName().equals("post-it")) {
            Thread thread = new Thread(() -> setVisible(((Preferences) arg).getValue().equals("true")));
            thread.start();
        }
    }
}
