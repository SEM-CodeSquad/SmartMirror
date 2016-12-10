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

package smartMirror.controllers.dataHandlers.widgetsDataHandlers.postIts;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         Class responsible for managing the graphical post-its
 */
@SuppressWarnings("unchecked")
public class PostItManager
{
    private GraphicsContext gc;
    private List<Pixel>[] pixelsList = new List[12];
    private Image[] images = new Image[12];

    private Canvas[] canvases;
    private TextArea[] textAreas;

    /**
     * Method responsible responsible for assigning all the canvas to be used
     *
     * @param args all canvas to be used
     */
    public void setCanvases(Canvas... args)
    {
        this.canvases = args;
    }

    /**
     * Method responsible responsible for assigning all the textAreas to be used
     *
     * @param args all textAreas to be used
     */
    public void setTextAreas(TextArea... args)
    {
        this.textAreas = args;
    }

    /**
     * Method responsible for setting the post-it image for the choose color
     *
     * @param i         index of post-it
     * @param userStyle color of the post-it
     */
    public void setImage(int i, String userStyle)
    {
        InputStream is = getClass().getClassLoader().getResourceAsStream(userStyle + ".png");
        Image image = new Image(is);
        this.images[i] = image;
    }

    /**
     * Method responsible for generating a graphical post-it
     *
     * @param i   index of post-it
     * @param msg text to be set in the post-it
     */
    public void generateGraphicalNote(int i, String msg)
    {
        Platform.runLater(() ->
        {
            try
            {

                gc = canvases[i].getGraphicsContext2D();


                gc.drawImage(images[i], 0, 0);


                readPixels(this.images[i], i);
                setPostMessage(i, msg);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method that sets the text in the post-it
     *
     * @param i   index of pos-it
     * @param msg text to be set
     */
    public void setPostMessage(int i, String msg)
    {
        String newLine = System.getProperty("line.separator");
        if (msg.length() > 40) textAreas[i].setText(newLine + msg);
        else textAreas[i].setText(newLine + newLine + msg);
    }

    /**
     * Method that deletes the graphical post-it
     *
     * @param i index of post-it
     */
    public void deleteGraphicalNote(int i)
    {
        AnimationTimer animationTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                update(i);
            }
        };
        animationTimer.start();
        textAreas[i].setText("");
    }

    /**
     * Method that reads and store each pixel for the post-it image
     *
     * @param image post-it image
     * @param i     index of post-it
     * @see Pixel
     */
    private void readPixels(Image image, int i)
    {
        PixelReader pixelReader = image.getPixelReader();
        List<Pixel> pixelArrayList = new ArrayList<>();
        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                Color color = pixelReader.getColor(x, y);

                if (!color.equals(Color.TRANSPARENT))
                {
                    Pixel p = new Pixel(x, y, color);
                    pixelArrayList.add(p);
                    pixelsList[i] = pixelArrayList;
                }
            }
        }
    }

    /**
     * Method called by the delete graphical post-it function it is responsible for apply the nice delete effect for the
     * post-it using the Pixel class to give the impression that the post-it is disintegrating
     *
     * @param i post-it index
     * @see Pixel
     */
    private void update(int i)
    {
        gc = canvases[i].getGraphicsContext2D();

        gc.clearRect(0, 0, images[i].getWidth(), images[i].getHeight());

        pixelsList[i].removeIf(Pixel::isDead);

        pixelsList[i].parallelStream()
                .filter(p -> !p.isActive())
                .forEach(p -> p.activate(new Point2D(-Math.random() * 19, -Math.random() * 15)));

        pixelsList[i].forEach(p ->
        {
            p.update();
            p.draw(gc);
        });
    }
}
