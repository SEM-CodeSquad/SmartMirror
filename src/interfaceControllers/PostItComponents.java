package interfaceControllers;

import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class PostItComponents
{
    private Map<Integer, StackPane> stackPaneMap;
    private Map<Integer, TextArea> textAreaMap;
    private final int size = 11;
    private int numOfPanes;
    private int numOfTextAreas;
    private NoteGenerator noteGenerator;

    public PostItComponents()
    {
        this.stackPaneMap = new HashMap<>(this.size);
        this.textAreaMap = new HashMap<>(this.size);
        this.numOfPanes = 0;
        this.numOfTextAreas = 0;
    }

    public void addPane(int key, StackPane stackPane)
    {
      if (this.numOfPanes > this.size) throw new IllegalArgumentException("StackPane Map Is Full");
      this.stackPaneMap.put(key, stackPane);
      this.numOfPanes++;
    }

    public void addTextArea(int key, TextArea textArea)
    {
        if (this.numOfTextAreas > this.size) throw new IllegalArgumentException("TextArea Map Is Full");
        this.textAreaMap.put(key, textArea);
        this.numOfTextAreas++;
    }

    private StackPane getStackPane(int key)
    {
        if (!this.stackPaneMap.containsKey(key)) throw new IllegalArgumentException("StackPane Map Does Not Contain This key");

        return this.stackPaneMap.get(key);
    }

    private TextArea getTextArea(int key)
    {
        if (!this.textAreaMap.containsKey(key)) throw new IllegalArgumentException("TextArea Map Does Not Contain This key");

        return this.textAreaMap.get(key);
    }

    public void createPostIt(String msg, String userStyle, int index)
    {
       noteGenerator = new NoteGenerator(msg, userStyle);
       noteGenerator.generateGraphicalNote(getStackPane(index), getTextArea(index));
    }

    public void deletePostIt(int index)
    {
        noteGenerator = new NoteGenerator("", "");
        noteGenerator.deleteGraphicalNote(getStackPane(index), getTextArea(index));
    }
}
