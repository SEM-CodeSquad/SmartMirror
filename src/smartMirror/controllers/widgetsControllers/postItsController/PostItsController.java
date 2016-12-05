package smartMirror.controllers.widgetsControllers.postItsController;

import smartMirror.dataHandlers.widgetsDataHandlers.postIts.PostItManager;
import smartMirror.dataModels.applicationModels.ChainedMap;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItAction;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItNote;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * @author Pucci on 22/11/2016.
 */
public class PostItsController extends Observable implements Observer, Initializable {
    @FXML
    public GridPane postItPaneStandard;

    @FXML
    public StackPane postPaneS1;
    @FXML
    public Canvas canvas1;
    @FXML
    public TextArea postTextS1;

    @FXML
    public StackPane postPaneS2;
    @FXML
    public Canvas canvas2;
    @FXML
    public TextArea postTextS2;

    @FXML
    public StackPane postPaneS3;
    @FXML
    public Canvas canvas3;
    @FXML
    public TextArea postTextS3;

    @FXML
    public StackPane postPaneS4;
    @FXML
    public Canvas canvas4;
    @FXML
    public TextArea postTextS4;

    @FXML
    public StackPane postPaneS5;
    @FXML
    public Canvas canvas5;
    @FXML
    public TextArea postTextS5;

    @FXML
    public StackPane postPaneS6;
    @FXML
    public Canvas canvas6;
    @FXML
    public TextArea postTextS6;

    @FXML
    public StackPane postPaneS7;
    @FXML
    public Canvas canvas7;
    @FXML
    public TextArea postTextS7;

    @FXML
    public StackPane postPaneS8;
    @FXML
    public Canvas canvas8;
    @FXML
    public TextArea postTextS8;

    @FXML
    public StackPane postPaneS9;
    @FXML
    public Canvas canvas9;
    @FXML
    public TextArea postTextS9;

    @FXML
    public StackPane postPaneS10;
    @FXML
    public Canvas canvas10;
    @FXML
    public TextArea postTextS10;

    @FXML
    public StackPane postPaneS11;
    @FXML
    public Canvas canvas11;
    @FXML
    public TextArea postTextS11;

    @FXML
    public StackPane postPaneS12;
    @FXML
    public Canvas canvas12;
    @FXML
    public TextArea postTextS12;
    private ChainedMap<String, PostItNote> postItNotes;
    private boolean[] booleanArray;
    private PostItManager postItManager;
    //    protected String[] postItIdList = new String[1000]; //Set to 1000 slots for now
//    protected int idListIndex = 0;
    @FXML
    private StackPane postPanes;
    private String tableColor;

    public PostItsController() {
        this.postItNotes = new ChainedMap<>(12);
        this.booleanArray = new boolean[12];
        this.postItManager = new PostItManager();
    }

    public StackPane getPostPane() {
        return postPanes;
    }

    public String getTableColor() {
        return tableColor;
    }

    public void setTableColor(String tableColor) {
        this.tableColor = tableColor;
    }

    private void setUp() {
        this.postItManager.setCanvases(canvas1, canvas2, canvas3, canvas4, canvas5, canvas6, canvas7,
                canvas8, canvas9, canvas10, canvas11, canvas12);
        this.postItManager.setStackPanes(postPaneS1, postPaneS2, postPaneS3, postPaneS4, postPaneS5,
                postPaneS6, postPaneS7, postPaneS8, postPaneS9, postPaneS10, postPaneS11, postPaneS12);
        this.postItManager.setTextAreas(postTextS1, postTextS2, postTextS3, postTextS4, postTextS5, postTextS6,
                postTextS7, postTextS8, postTextS9, postTextS10, postTextS11, postTextS12);
    }

    private int freePostIndex() {
        int index = -1;
        for (int i = 0; i < booleanArray.length; i++) {

            if (!booleanArray[i]) {
                return i;
            }
        }
        return index;
    }

    private synchronized void createPostIt(PostItNote postItNote) {
        if (postItNote.getSenderId().equals(this.tableColor)) {
            int index = freePostIndex();
            if (index != -1) {
                postItNote.setPostItIndex(index);
                postItNotes.add(postItNote.getPostItId(), postItNote);
                booleanArray[index] = true;
//                postItIdList[idListIndex] = postItNote.getPostItId();
//                idListIndex++;
                this.postItManager.setImage(index, postItNote.getSenderId());
                this.postItManager.generateGraphicalNote(index, postItNote.getBodyText());
                setChanged();
                notifyObservers("success");
            } else {
                setChanged();
                notifyObservers(this);
            }
        }
    }

    private synchronized void managePostIt(PostItAction postItAction) {
        if (postItNotes.getId().contains(postItAction.getPostItId())) {
            int index = postItNotes.getMap().get(postItAction.getPostItId()).getPostItIndex();
            if (postItAction.isActionDelete()) {
                this.postItManager.deleteGraphicalNote(index);
                booleanArray[index] = false;
                setChanged();
                notifyObservers("success");
            } else if (postItAction.isActionModify()) {
                this.postItManager.setPostMessage(index, postItAction.getModification());
                setChanged();
                notifyObservers("success");
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof PostItNote) {
            PostItNote postItNote = (PostItNote) arg;
            Thread thread = new Thread(() -> createPostIt(postItNote));
            thread.start();
        } else if (arg instanceof PostItAction) {
            PostItAction postItAction = (PostItAction) arg;
            Thread thread = new Thread(() -> managePostIt(postItAction));
            thread.start();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize your logic here: all @FXML variables will have been injected
        setUp();
    }
}
