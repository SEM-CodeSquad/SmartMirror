package controllers;

import dataModels.ResizableCanvas;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.*;

/**
 * @author Pucci on 22/11/2016.
 */
public class PostItsController extends Observable implements Observer, Initializable {
    @FXML
    public GridPane postItPaneStandard;
    @FXML
    public StackPane postPaneS1;
    @FXML
    public TextArea postTextS1;
    @FXML
    public StackPane postPaneS2;
    @FXML
    public TextArea postTextS2;
    @FXML
    public StackPane postPaneS3;
    @FXML
    public TextArea postTextS3;
    @FXML
    public StackPane postPaneS4;
    @FXML
    public TextArea postTextS4;
    @FXML
    public StackPane postPaneS5;
    @FXML
    public TextArea postTextS5;
    @FXML
    public StackPane postPaneS6;
    @FXML
    public TextArea postTextS6;
    @FXML
    public StackPane postPaneS7;
    @FXML
    public TextArea postTextS7;
    @FXML
    public StackPane postPaneS8;
    @FXML
    public TextArea postTextS8;
    @FXML
    public StackPane postPaneS9;
    @FXML
    public TextArea postTextS9;
    @FXML
    public StackPane postPaneS10;
    @FXML
    public TextArea postTextS10;
    @FXML
    public StackPane postPaneS11;
    @FXML
    public TextArea postTextS11;
    @FXML
    public StackPane postPaneS12;
    @FXML
    public TextArea postTextS12;
    @FXML
    private StackPane postPanes;

    public StackPane getPostPane() {
        return postPanes;
    }


    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize your logic here: all @FXML variables will have been injected

    }
}
