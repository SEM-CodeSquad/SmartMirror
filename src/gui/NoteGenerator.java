package gui;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import java.io.File;

class NoteGenerator
{
    private String msg;
    private String userStyle;

    NoteGenerator(String msg, String userStyle)
    {
        this.msg = msg;
        this.userStyle = userStyle;
    }

    void generateGraphicalNote(StackPane stackPane, TextArea textArea)
    {
        Platform.runLater(()-> generate(stackPane, textArea));
    }

    private void generate(StackPane stackPane, TextArea textArea)
    {
        try
        {
            File f = new File("resources/" + this.userStyle + ".png");
            stackPane.setStyle("-fx-background-image: url('" + f.toURI().toString() + "'); " +
                    "-fx-background-repeat: stretch;" +
                    "-fx-background-size: 320 200;" +
                    "    -fx-background-position: center center;" +
                    "    -fx-effect: dropshadow(three-pass-box, black, 30, 0.5, 0, 0);");
            setPostMessage(textArea);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setPostMessage(TextArea textArea)
    {
        //String newLine = System.getProperty("line.separator");
        textArea.setText(this.msg);
        System.out.println("Done");
    }

    public void deleteGraphicalNote(StackPane stackPane, TextArea textArea)
    {
        stackPane.setStyle("-fx-background-color: transparent;");
        textArea.setText("");
    }
}
