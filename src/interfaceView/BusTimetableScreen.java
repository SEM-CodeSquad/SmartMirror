package interfaceView;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by Axel on 11/18/2016.
 */
public class BusTimetableScreen {

    Label busName;
    Label busDirection;
    Label busMinutes;
    Label busName1;
    Label busDirection1;
    Label busMinutes1;
    Label busName2;
    Label busDirection2;
    Label busMinutes2;
    Label busName3;
    Label busDirection3;
    Label busMinutes3;
    Label busName4;
    Label busDirection4;
    Label busMinutes4;
    Label busName5;
    Label busDirection5;
    Label busMinutes5;
    Label busName6;
    Label busDirection6;
    Label busMinutes6;
    private GridPane busTablePane;

    public BusTimetableScreen(GridPane gridPane) {

        gridPane.getChildren().add(this.busTablePane);
        Platform.runLater(this::setUp);
    }

    public void setUp() {

        busTablePane.setPadding(new Insets(5, 5, 5, 5));
        busTablePane.setVgap(10);
        busTablePane.setHgap(10);


        Label busName = new Label();
        busName.setAlignment(Pos.CENTER);
        busName.setPrefSize(40, 40);
        busName.setTextFill(Color.WHITE);
        busName.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 30.0));
        busName.setStyle("-fx-background-color:" + "#008228" + "; -fx-background-radius: 5;");
        busTablePane.add(busName, 0, 0);

        Label busDirection = new Label("busDirection from busStop");
        busDirection.setTextFill(Color.WHITE);
        busDirection.setFont(Font.font("Microsoft Yahei", 10.0));
        GridPane.setMargin(busDirection, new Insets(-22, 0, 0, 5));
        busTablePane.add(busDirection, 1, 0);

        Label busMinutes = new Label("busMinutes");
        busMinutes.setTextFill(Color.WHITE);
        busMinutes.setFont(Font.font("Microsoft Yahei", 15.0));
        GridPane.setMargin(busMinutes, new Insets(5, 0, 0, 5));
        busTablePane.add(busMinutes, 1, 0);

        Label busName1 = new Label();
        busName1.setAlignment(Pos.CENTER);
        busName1.setPrefSize(40, 40);
        busName1.setTextFill(Color.WHITE);
        busName1.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 30.0));
        busName1.setStyle("-fx-background-color:" + "#008228" + "; -fx-background-radius: 5;");
        busTablePane.add(busName1, 0, 1);

        Label busDirection1 = new Label("busDirection from busStop");
        busDirection1.setTextFill(Color.WHITE);
        busDirection1.setFont(Font.font("Microsoft Yahei", 10.0));
        GridPane.setMargin(busDirection1, new Insets(-22, 0, 0, 5));
        busTablePane.add(busDirection1, 1, 1);

        Label busMinutes1 = new Label("busMinutes");
        busMinutes1.setTextFill(Color.WHITE);
        busMinutes1.setFont(Font.font("Microsoft Yahei", 15.0));
        GridPane.setMargin(busMinutes1, new Insets(5, 0, 0, 5));
        busTablePane.add(busMinutes1, 1, 1);

        Label busName2 = new Label();
        busName2.setAlignment(Pos.CENTER);
        busName2.setPrefSize(40, 40);
        busName2.setTextFill(Color.WHITE);
        busName2.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 30.0));
        busName2.setStyle("-fx-background-color:" + "#008228" + "; -fx-background-radius: 5;");
        busTablePane.add(busName2, 0, 2);

        Label busDirection2 = new Label("busDirection from busStop");
        busDirection2.setTextFill(Color.WHITE);
        busDirection2.setFont(Font.font("Microsoft Yahei", 10.0));
        GridPane.setMargin(busDirection2, new Insets(-22, 0, 0, 5));
        busTablePane.add(busDirection2, 1, 2);

        Label busMinutes2 = new Label("busMinutes");
        busMinutes2.setTextFill(Color.WHITE);
        busMinutes2.setFont(Font.font("Microsoft Yahei", 15.0));
        GridPane.setMargin(busMinutes2, new Insets(5, 0, 0, 5));
        busTablePane.add(busMinutes2, 1, 2);

        Label busName3 = new Label();
        busName3.setAlignment(Pos.CENTER);
        busName3.setPrefSize(40, 40);
        busName3.setTextFill(Color.WHITE);
        busName3.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 30.0));
        busName3.setStyle("-fx-background-color:" + "#008228" + "; -fx-background-radius: 5;");
        busTablePane.add(busName3, 0, 3);

        Label busDirection3 = new Label("busDirection from busStop");
        busDirection3.setTextFill(Color.WHITE);
        busDirection3.setFont(Font.font("Microsoft Yahei", 10.0));
        GridPane.setMargin(busDirection3, new Insets(-22, 0, 0, 5));
        busTablePane.add(busDirection3, 1, 3);

        Label busMinutes3 = new Label("busMinutes");
        busMinutes3.setTextFill(Color.WHITE);
        busMinutes3.setFont(Font.font("Microsoft Yahei", 15.0));
        GridPane.setMargin(busMinutes3, new Insets(5, 0, 0, 5));
        busTablePane.add(busMinutes3, 1, 3);


        Label busName4 = new Label();
        busName4.setAlignment(Pos.CENTER);
        busName4.setPrefSize(40, 40);
        busName4.setTextFill(Color.WHITE);
        busName4.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 30.0));
        busName4.setStyle("-fx-background-color:" + "#008228" + "; -fx-background-radius: 5;");
        busTablePane.add(busName4, 0, 4);

        Label busDirection4 = new Label("busDirection from busStop");
        busDirection4.setTextFill(Color.WHITE);
        busDirection4.setFont(Font.font("Microsoft Yahei", 10.0));
        GridPane.setMargin(busDirection4, new Insets(-22, 0, 0, 5));
        busTablePane.add(busDirection4, 1, 4);

        Label busMinutes4 = new Label("busMinutes");
        busMinutes4.setTextFill(Color.WHITE);
        busMinutes4.setFont(Font.font("Microsoft Yahei", 15.0));
        GridPane.setMargin(busMinutes4, new Insets(5, 0, 0, 5));
        busTablePane.add(busMinutes4, 1, 4);

        Label busName5 = new Label();
        busName5.setAlignment(Pos.CENTER);
        busName5.setPrefSize(40, 40);
        busName5.setTextFill(Color.WHITE);
        busName5.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 30.0));
        busName5.setStyle("-fx-background-color:" + "#008228" + "; -fx-background-radius: 5;");
        busTablePane.add(busName5, 0, 5);

        Label busDirection5 = new Label("busDirection from busStop");
        busDirection5.setTextFill(Color.WHITE);
        busDirection5.setFont(Font.font("Microsoft Yahei", 10.0));
        GridPane.setMargin(busDirection5, new Insets(-22, 0, 0, 5));
        busTablePane.add(busDirection5, 1, 5);

        Label busMinutes5 = new Label("busMinutes");
        busMinutes5.setTextFill(Color.WHITE);
        busMinutes5.setFont(Font.font("Microsoft Yahei", 15.0));
        GridPane.setMargin(busMinutes5, new Insets(5, 0, 0, 5));
        busTablePane.add(busMinutes5, 1, 5);

        Label busName6 = new Label();
        busName6.setAlignment(Pos.CENTER);
        busName6.setPrefSize(40, 40);
        busName6.setTextFill(Color.WHITE);
        busName6.setFont(Font.font("Microsoft Yahei", FontWeight.BOLD, 30.0));
        busName6.setStyle("-fx-background-color:" + "#008228" + "; -fx-background-radius: 5;");
        busTablePane.add(busName6, 0, 6);

        Label busDirection6 = new Label("busDirection from busStop");
        busDirection6.setTextFill(Color.WHITE);
        busDirection6.setFont(Font.font("Microsoft Yahei", 10.0));
        GridPane.setMargin(busDirection6, new Insets(-22, 0, 0, 5));
        busTablePane.add(busDirection6, 1, 6);

        Label busMinutes6 = new Label("busMinutes");
        busMinutes6.setTextFill(Color.WHITE);
        busMinutes6.setFont(Font.font("Microsoft Yahei", 15.0));
        GridPane.setMargin(busMinutes6, new Insets(5, 0, 0, 5));
        busTablePane.add(busMinutes6, 1, 6);
    }

    public void setLabel(String busName, int busDeparture, String busFrom, String busColor, String busDirection) {
        this.busName.setText(busName);
        this.busName.setStyle("-fx-background-color:" + busColor + "; -fx-background-radius: 5;");
        this.busDirection.setText(busDirection + " from " + busFrom);
        this.busMinutes.setText(Integer.toString(busDeparture));
    }

    public void setLabel1(String busName, int busDeparture, String busFrom, String busColor, String busDirection) {
        this.busName1.setText(busName);
        this.busName1.setStyle("-fx-background-color:" + busColor + "; -fx-background-radius: 5;");
        this.busDirection1.setText(busDirection + " from " + busFrom);
        this.busMinutes1.setText(Integer.toString(busDeparture));
    }

    public void setLabel2(String busName, int busDeparture, String busFrom, String busColor, String busDirection) {
        this.busName2.setText(busName);
        this.busName2.setStyle("-fx-background-color:" + busColor + "; -fx-background-radius: 5;");
        this.busDirection2.setText(busDirection + " from " + busFrom);
        this.busMinutes2.setText(Integer.toString(busDeparture));
    }

    public void setLabel3(String busName, int busDeparture, String busFrom, String busColor, String busDirection) {
        this.busName3.setText(busName);
        this.busName3.setStyle("-fx-background-color:" + busColor + "; -fx-background-radius: 5;");
        this.busDirection3.setText(busDirection + " from " + busFrom);
        this.busMinutes3.setText(Integer.toString(busDeparture));
    }

    public void setLabel4(String busName, int busDeparture, String busFrom, String busColor, String busDirection) {
        this.busName4.setText(busName);
        this.busName4.setStyle("-fx-background-color:" + busColor + "; -fx-background-radius: 5;");
        this.busDirection4.setText(busDirection + " from " + busFrom);
        this.busMinutes4.setText(Integer.toString(busDeparture));
    }

    public void setLabel5(String busName, int busDeparture, String busFrom, String busColor, String busDirection) {
        this.busName5.setText(busName);
        this.busName5.setStyle("-fx-background-color:" + busColor + "; -fx-background-radius: 5;");
        this.busDirection5.setText(busDirection + " from " + busFrom);
        this.busMinutes5.setText(Integer.toString(busDeparture));
    }

    public void setLabel6(String busName, int busDeparture, String busFrom, String busColor, String busDirection) {
        this.busName6.setText(busName);
        this.busName6.setStyle("-fx-background-color:" + busColor + "; -fx-background-radius: 5;");
        this.busDirection6.setText(busDirection + " from " + busFrom);
        this.busMinutes6.setText(Integer.toString(busDeparture));
    }

}
