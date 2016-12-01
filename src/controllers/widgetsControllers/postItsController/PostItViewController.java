package controllers.widgetsControllers.postItsController;

import dataHandlers.animations.TransitionAnimation;
import dataModels.applicationModels.Preferences;
import dataModels.widgetsModels.postItsModels.PostItAction;
import dataModels.widgetsModels.postItsModels.PostItNote;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Pucci on 22/11/2016.
 */
public class PostItViewController extends Observable implements Observer {
    public StackPane postPanes;

    private StackPane[] panes;

    private TransitionAnimation animation;

    /**
     *
     */
    public PostItViewController() {
        Platform.runLater(this::build);
    }

    private void build() {
        PostItsController standardController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane standard = standardController.getPostPane();
        setOpacity(standard, false);
        standardController.setTableColor("standard");
        this.addObserver(standardController);
        standardController.addObserver(this);

        PostItsController blueController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane blue = blueController.getPostPane();
        setOpacity(blue, false);
        blueController.setTableColor("blue");
        this.addObserver(blueController);
        blueController.addObserver(this);

        PostItsController greenController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane green = greenController.getPostPane();
        setOpacity(green, false);
        greenController.setTableColor("green");
        this.addObserver(greenController);
        greenController.addObserver(this);

        PostItsController purpleController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane purple = purpleController.getPostPane();
        setOpacity(purple, false);
        purpleController.setTableColor("purple");
        this.addObserver(purpleController);
        purpleController.addObserver(this);

        PostItsController orangeController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane orange = orangeController.getPostPane();
        setOpacity(orange, false);
        orangeController.setTableColor("orange");
        this.addObserver(orangeController);
        orangeController.addObserver(this);

        PostItsController yellowController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane yellow = yellowController.getPostPane();
        setOpacity(yellow, false);
        yellowController.setTableColor("yellow");
        this.addObserver(yellowController);
        yellowController.addObserver(this);

        PostItsController pinkController = loadView("/interfaceViews/widgetsViews/postItWidget/PostItTable.fxml")
                .getController();
        StackPane pink = pinkController.getPostPane();
        setOpacity(pink, false);
        pinkController.setTableColor("pink");
        this.addObserver(pinkController);
        pinkController.addObserver(this);

        this.animation = new TransitionAnimation();
        this.animation.transitionAnimation(15, 5, standard, blue, green, purple, orange,
                pink, yellow);
        this.animation.playSeqAnimation();

        this.panes = new StackPane[]{standard, blue, green, purple, orange,
                pink, yellow};
    }


    private synchronized void showSpecificTable(String colourIndex) {
        int index = Integer.parseInt(colourIndex);

        if (!(index > panes.length)) {
            this.animation.pauseSeqAnimation();
            this.animation.getSequentialTransition().getCurrentTime();

            for (int i = 0; i < panes.length; i++) {
                if (i != index) {
                    panes[i].setOpacity(0);
                } else {
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), panes[index]);

                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.play();
                }
            }
            Platform.runLater(() -> {

            });
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    Platform.runLater(() -> {
                        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), panes[index]);

                        fadeOut.setFromValue(1);
                        fadeOut.setToValue(0);

                        fadeOut.setOnFinished(event -> animation.playSeqAnimation());
                        fadeOut.play();
                    });
                }
            }, 30000);
        }
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
        } else if (arg instanceof Preferences && ((Preferences) arg).getName().equals("widget6")) {
            Thread thread = new Thread(() -> setVisible(((Preferences) arg).getValue().equals("true")));
            thread.start();
        } else if (arg instanceof Preferences && ((Preferences) arg).getName().equals("showOnly")) {
            Thread thread = new Thread(() -> showSpecificTable(((Preferences) arg).getValue()));
            thread.start();
        }
    }
}
