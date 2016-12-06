package smartMirror.controllers.widgetsControllers.postItsController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import smartMirror.dataHandlers.widgetsDataHandlers.postIts.PostItManager;
import smartMirror.dataModels.applicationModels.ChainedMap;
import smartMirror.dataHandlers.commons.Timestamp;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItAction;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItNote;

import java.net.URL;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * @author Pucci on 22/11/2016.
 *         Class responsible for updating the PostItTableView
 */
public class PostItsController extends Observable implements Observer, Initializable
{
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

    @FXML
    private StackPane postPanes;

    private ChainedMap<String, PostItNote> postItNotes;
    private boolean[] booleanArray;
    private PostItManager postItManager;

    private String tableColor;

    /**
     * Constructor responsible for the initialization of the needed data structures and the PostItManager
     *
     * @see PostItManager
     */
    PostItsController()
    {
        this.postItNotes = new ChainedMap<>(12);
        this.booleanArray = new boolean[12];
        this.postItManager = new PostItManager();
    }

    /**
     * Getter method that provides the pane holding all the post-its
     *
     * @return post-it pane
     */
    StackPane getPostPane()
    {
        return postPanes;
    }

    /**
     * Getter method that provides the name of this table color
     *
     * @return the table color name
     */
    public String getTableColor()
    {
        return tableColor;
    }

    /**
     * Setter method that sets the name of the table color
     *
     * @param tableColor color name to be set
     */
    void setTableColor(String tableColor)
    {
        this.tableColor = tableColor;
    }

    /**
     * Method responsible for grouping the components to facilitate manipulation
     */
    private void setUp()
    {
        this.postItManager.setCanvases(canvas1, canvas2, canvas3, canvas4, canvas5, canvas6, canvas7,
                canvas8, canvas9, canvas10, canvas11, canvas12);
        this.postItManager.setStackPanes(postPaneS1, postPaneS2, postPaneS3, postPaneS4, postPaneS5,
                postPaneS6, postPaneS7, postPaneS8, postPaneS9, postPaneS10, postPaneS11, postPaneS12);
        this.postItManager.setTextAreas(postTextS1, postTextS2, postTextS3, postTextS4, postTextS5, postTextS6,
                postTextS7, postTextS8, postTextS9, postTextS10, postTextS11, postTextS12);
    }

    /**
     * Method that checks which index is free to post a post-it
     *
     * @return -1 if no index is free or the number of the free index
     */
    private int freePostIndex()
    {
        int index = -1;
        for (int i = 0; i < booleanArray.length; i++)
        {

            if (!booleanArray[i])
            {
                return i;
            }
        }
        return index;
    }

    /**
     * Method that creates a graphical post-it
     *
     * @param postItNote the post-it to be generated
     */
    private synchronized void createPostIt(PostItNote postItNote)
    {
        if (postItNote.getSenderId().equals(this.tableColor))
        {
            int index = freePostIndex();
            if (index != -1)
            {
                System.out.println(this.tableColor + " : " + index);
                postItNote.setPostItIndex(index);
                postItNotes.add(postItNote.getPostItId(), postItNote);
                booleanArray[index] = true;
                this.postItManager.setImage(index, postItNote.getSenderId());
                this.postItManager.generateGraphicalNote(index, postItNote.getBodyText());
//                setChanged();
//                notifyObservers("success");
            }
            else
            {
                System.out.println(this.tableColor + " : full " + index);

//                setChanged();
//                notifyObservers(this);
            }
        }
    }

    /**
     * Method responsible for managing post-its ( deleting or modifying the post-it text)
     *
     * @param postItAction the action to be taken
     */
    private synchronized void managePostIt(PostItAction postItAction)
    {
        if (postItNotes.getId().contains(postItAction.getPostItId()))
        {
            int index = postItNotes.getMap().get(postItAction.getPostItId()).getPostItIndex();
            if (postItAction.isActionDelete())
            {
                this.postItManager.deleteGraphicalNote(index);
                this.postItNotes.remove(postItAction.getPostItId());
                booleanArray[index] = false;
//                setChanged();
//                notifyObservers("success");
            }
            else if (postItAction.isActionModify())
            {
                this.postItManager.setPostMessage(index, postItAction.getModification());
                this.postItNotes.get(postItAction.getPostItId()).setBodyText(postItAction.getModification());
//                setChanged();
//                notifyObservers("success");
            }
        }
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
        if (arg instanceof PostItNote)
        {
            PostItNote postItNote = (PostItNote) arg;
            Thread thread = new Thread(() -> createPostIt(postItNote));
            thread.start();
        }
        else if (arg instanceof PostItAction)
        {
            PostItAction postItAction = (PostItAction) arg;
            Thread thread = new Thread(() -> managePostIt(postItAction));
            thread.start();
        }
        else if (arg instanceof Timestamp)
        {
            Timestamp timestamp = (Timestamp) arg;
            Thread thread = new Thread(() ->
            {
                LinkedList<String> ids = (LinkedList<String>) postItNotes.getId();
                LinkedList<String> idsToBeDeleted = new LinkedList<>();
                for (String id : ids)
                {
                    PostItNote postItNote = postItNotes.get(id);
                    System.out.println("deleting : " + postItNote.getPostItIndex() + " :" + tableColor);
                    if (postItNote.getTimestamp() < timestamp.getTimestamp())
                    {
                        idsToBeDeleted.add(id);
                    }
                }
                for (String id : idsToBeDeleted)
                {
                    PostItNote postItNote = postItNotes.get(id);
                    PostItAction postItAction = new PostItAction(postItNote.getPostItId(), "Delete", "none");
                    managePostIt(postItAction);
                    System.out.println("deleted");
                }
            });
            thread.start();
        }
    }

    /**
     * Method that initialize the controller with all its FXML components from the view
     *
     * @param location  location
     * @param resources resource
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // initialize your logic here: all @FXML variables will have been injected
        setUp();
    }
}
