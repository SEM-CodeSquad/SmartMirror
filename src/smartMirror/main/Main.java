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

package smartMirror.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 */
public class Main extends Application
{

    /**
     * Main method
     *
     * @param args launching args
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * JavaFX start method
     *
     * @param primaryStage stage holding the interface
     * @throws Exception exception in case of erros
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/smartMirror/Views/mainViews/MainInterface.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 480, 640);
        primaryStage.setTitle("SmartMirror");
        primaryStage.setScene(scene);
        primaryStage.show();
//        primaryStage.setFullScreen(true);
        primaryStage.setMaximized(true);
        primaryStage.setAlwaysOnTop(true);


        primaryStage.setOnCloseRequest(event -> System.exit(0));
        scene.setOnKeyPressed(event ->
        {
            switch (event.getCode())
            {
                case X:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        });
    }
}