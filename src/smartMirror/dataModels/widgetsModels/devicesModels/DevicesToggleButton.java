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

package smartMirror.dataModels.widgetsModels.devicesModels;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         Class model for the toggle button that show if a device is on or off
 */
public class DevicesToggleButton extends Label
{
    private SimpleBooleanProperty switched = new SimpleBooleanProperty(true);

    /**
     * Constructor build a toggle button adds the boolean property listener to it
     */
    public DevicesToggleButton()
    {
        prefHeight(21);
        prefWidth(67);
        Button button = new Button();
        button.setPrefWidth(31);
        button.setPrefHeight(28);
        button.mnemonicParsingProperty().set(false);
        button.setMaxHeight(Double.NEGATIVE_INFINITY);
        button.setMinHeight(Double.NEGATIVE_INFINITY);
        button.setStyle("-fx-background-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\n" +
                "linear-gradient(#020b02, #3a3a3a),\n" +
                "linear-gradient(#b9b9b9 0%, #c2c2c2 20%, #afafaf 80%, #c8c8c8 100%),\n" +
                "linear-gradient(#f5f5f5 0%, #dbdbdb 50%, #cacaca 51%, #d7d7d7 100%); " +
                "-fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);" +
                "-fx-background-radius: 30;");
        button.setOnAction(t -> switched.set(!switched.get()));

        setGraphic(button);

        switched.addListener((ov, t, t1) ->
        {
            if (t1)
            {
                setText(" ON");
                setStyle("-fx-background-color: linear-gradient(#198c19, #007300),\n" +
                        "radial-gradient(center 50% -40%, radius 200%, #329932 45%, #008000 50%);" +
                        "-fx-text-fill:#ffffff;" +
                        "-fx-font: 10px \"Microsoft YaHei\";" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10 25 25 10;" +
                        "-fx-border-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
                        "linear-gradient(#020b02, #3a3a3a)," +
                        "linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%)," +
                        "linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%)," +
                        "linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%); " +
                        "-fx-border-radius: 10 25 25 10;");
                setContentDisplay(ContentDisplay.RIGHT);
            }
            else
            {
                setText("OFF ");
                setStyle("-fx-background-color: linear-gradient(#ff3232, #ff0000),\n" +
                        "radial-gradient(center 50% -40%, radius 200%, #ff1919 45%, #cc0000 50%);" +
                        "-fx-text-fill:#ffffff;" +
                        "-fx-font: 10px \"Microsoft YaHei\";" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 25 10 10 25;" +
                        "-fx-border-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%)," +
                        "linear-gradient(#020b02, #3a3a3a)," +
                        "linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%)," +
                        "linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%)," +
                        "linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%); " +
                        "-fx-border-radius: 25 10 10 25;");
                setContentDisplay(ContentDisplay.LEFT);
            }
        });

        switched.set(false);
    }

    /**
     * Getter method responsible for providing the switch property where it can be set to true to show status on or false
     * to show status off
     *
     * @return switch property
     */
    public SimpleBooleanProperty switchProperty()
    {
        return switched;
    }
}