package controllers;

import dataModels.PostItAction;
import dataModels.PostItNote;
import dataModels.PostItChainedMap;

import java.util.*;

public class PostItGuiManager extends Observable implements Observer
{
    private PostItNote[] postItNotesGui;
    private PostItChainedMap<String, PostItNote> postItNotesStored;
    private InterfaceController gui;
    private PostItNote postItNote;
    private PostItAction postItAction;

    public PostItGuiManager(InterfaceController gui)
    {
        postItNotesGui = new PostItNote[12];
        postItNotesStored = new PostItChainedMap<>();
        this.gui = gui;
    }

    private void deletePostIt(String postItId)
    {
        boolean found = false;
        int i = 0;
        while (i < postItNotesGui.length && !found)
        {
            if (postItNotesGui[i] != null)
            {
                if (postItNotesGui[i].getPostItId().equals(postItId))
                {
                    this.gui.getPostItComponents().deletePostIt(i);
                    postItNotesGui[i] = null;
                    found = true;
                    if (postItNotesStored.size() > 0)
                    {
                        String key = postItNotesStored.getPostItsId().remove();
                        this.postItNote = postItNotesStored.getPostIts().remove(key);
                        gui.getPostItComponents().createPostIt(this.postItNote.getBodyText(), this.postItNote.getSenderId(), i);
                        postItNotesGui[i] = this.postItNote;
                    }
                }
            }

            i++;
        }

        if (!found && this.postItNotesStored.getPostItsId().contains(postItId))
        {
            this.postItNotesStored.removePostIt(postItId);
        }
    }

    private void modifyPostIt(String postItId)
    {
        boolean found = false;
        int i = 0;
        while (i < postItNotesGui.length && !found)
        {
            if (postItNotesGui[i] != null)
            {
                if (postItNotesGui[i].getPostItId().equals(postItId))
                {
                    this.postItNote = postItNotesGui[i];
                    this.postItNote.setBodyText(postItAction.getModification());
                    this.gui.getPostItComponents().deletePostIt(i);
                    postItNotesGui[i] = this.postItNote;
                    this.gui.getPostItComponents().createPostIt(this.postItNote.getBodyText(), this.postItNote.getSenderId(), i);
                    found = true;
                }
            }

            i++;
        }

        if (!found && this.postItNotesStored.getPostItsId().contains(postItId))
        {
            this.postItNote = postItNotesStored.getPostIt(postItId);
            this.postItNote.setBodyText(postItAction.getModification());
            this.postItNotesStored.addPostIt(postItId,postItNote);
        }
    }

    private int freePostIndex()
    {
        int index = -1;
        for (int i = 0;i < postItNotesGui.length; i++)
        {
            System.out.println(postItNotesGui[i]);
            if (postItNotesGui[i] == null)
            {
                return i;
            }
        }
        return index;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (arg.equals("post-it"))
        {
            Thread thread = new Thread(()->{
                //this.postItNote = gui.getParser().getPostItNote();
                int freeIndex = freePostIndex();
                if (freeIndex != -1)
                {
                    gui.getPostItComponents().createPostIt(this.postItNote.getBodyText(), this.postItNote.getSenderId(), freeIndex);
                    this.postItNotesGui[freeIndex] = this.postItNote;
                }
                else
                {
                    postItNotesStored.addPostIt(postItNote.getPostItId(), postItNote);
                }

                System.out.println("Posted");
            });
            thread.start();

        }
        else if (arg.equals("post-it action"))
        {
           Thread thread = new Thread(()->{
               // this.postItAction = this.gui.getParser().getPostItAction();
               if (postItAction.isActionDelete())
               {
                   deletePostIt(postItAction.getPostItId());
               }
               else if (postItAction.isActionModify())
               {
                   modifyPostIt(postItAction.getPostItId());
               }
           });
            thread.start();
        }
    }
}
