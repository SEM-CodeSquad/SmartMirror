/*
 * Copyright 2016 CodeHigh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (C) 2016 CodeHigh.
 *     Permission is granted to copy, distribute and/or modify this document
 *     under the terms of the GNU Free Documentation License, Version 1.3
 *     or any later version published by the Free Software Foundation;
 *     with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 *     A copy of the license is included in the section entitled "GNU
 *     Free Documentation License".
 */

package smartMirror.controllers.interfaceControllers.widgetsControllers.postItsController;

import com.vdurmont.emoji.EmojiParser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import smartMirror.controllers.dataHandlers.widgetsDataHandlers.postIts.PostItManager;
import smartMirror.dataModels.applicationModels.ChainedMap;
import smartMirror.dataModels.modelCommons.Timestamp;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItAction;
import smartMirror.dataModels.widgetsModels.postItsModels.PostItNote;

import java.net.URL;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * @author CodeHigh on 22/11/2016.
 *         Class responsible for updating the PostItTableView
 */
class PostItsController extends Observable implements Observer, Initializable
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
    private String getTableColor()
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
                //The length of the text here is set to 100 because of the way the emojis are sent. As in
                // :relaxed: means a smile face.
                if (postItNote.getBodyText().length() <= 100)
                {
                    postItNote.setPostItIndex(index);
                    postItNotes.add(postItNote.getPostItId(), postItNote);
                    booleanArray[index] = true;
                    this.postItManager.setImage(index, postItNote.getSenderId());
                    String text = EmojiParser.parseToUnicode(postItNote.getBodyText());
                    this.postItManager.generateGraphicalNote(index, text);
                    setChanged();
                    notifyObservers("Post-it successfully added to the table " + getTableColor());
                }
                else
                {
                    setChanged();
                    notifyObservers("A post-it should not contain more than 80 characters (think that emojis " +
                            "count as around 6 characters)");
                }
            }
            else
            {
                setChanged();
                notifyObservers("Post-it could not be added the table " + getTableColor() + " is full delete " +
                        "a post-it in order to add a new one");
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
                setChanged();
                notifyObservers("Post-it successfully deleted from table " + getTableColor());
            }
            else if (postItAction.isActionModify())
            {
                if (postItAction.getModification().length() <= 90)
                {
                    String text = EmojiParser.parseToUnicode(postItAction.getModification());
                    this.postItManager.setPostMessage(index, text);
                    this.postItNotes.get(postItAction.getPostItId()).setBodyText(text);
                    setChanged();
                    notifyObservers("Post-it successfully updated int the table " + getTableColor());
                }
                else
                {
                    setChanged();
                    notifyObservers("A post-it should not contain more than 90 characters");
                }

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
