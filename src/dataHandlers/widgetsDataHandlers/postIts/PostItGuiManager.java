package dataHandlers.widgetsDataHandlers.postIts;


import dataModels.widgetsModels.postItsModels.PostItAction;
import dataModels.widgetsModels.postItsModels.PostItNote;
import dataModels.applicationModels.ChainedMap;

import java.util.*;

public class PostItGuiManager extends Observable implements Observer
{
    private PostItNote[] postItNotesGui;
    private ChainedMap<String, PostItNote> postItNotesStored;

    private PostItNote postItNote;
    private PostItAction postItAction;

    public PostItGuiManager()
    {
        postItNotesGui = new PostItNote[12];
        postItNotesStored = new ChainedMap<>();

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

                    postItNotesGui[i] = null;
                    found = true;
                    if (postItNotesStored.size() > 0)
                    {
                        String key = postItNotesStored.getId().remove();
                        this.postItNote = postItNotesStored.getMap().remove(key);

                        postItNotesGui[i] = this.postItNote;
                    }
                }
            }

            i++;
        }

        if (!found && this.postItNotesStored.getId().contains(postItId))
        {
            this.postItNotesStored.remove(postItId);
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

                    postItNotesGui[i] = this.postItNote;
                    found = true;
                }
            }

            i++;
        }

        if (!found && this.postItNotesStored.getId().contains(postItId))
        {
            this.postItNote = postItNotesStored.get(postItId);
            this.postItNote.setBodyText(postItAction.getModification());
            this.postItNotesStored.add(postItId, postItNote);
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
                    this.postItNotesGui[freeIndex] = this.postItNote;
                }
                else
                {
                    postItNotesStored.add(postItNote.getPostItId(), postItNote);
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
