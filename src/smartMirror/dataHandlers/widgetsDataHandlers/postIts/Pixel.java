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

package smartMirror.dataHandlers.widgetsDataHandlers.postIts;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author CodeHigh on 23/11/2016.
 *         Class that contains a pixel for the post-it image
 */
class Pixel
{
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();

    private Point2D velocity = Point2D.ZERO;

    private Color color;

    private double alpha = 1.0;
    private boolean active = false;

    /**
     * Constructor takes position and color of the pixel
     *
     * @param x     position x-axis
     * @param y     position y-axis
     * @param color color of the pixel
     */
    Pixel(int x, int y, Color color)
    {
        this.x.set(x);
        this.y.set(y);
        this.color = color;
    }

    /**
     * Getter method that returns the position in the x-axis
     *
     * @return position x-axis
     */
    private double getX()
    {
        return x.get();
    }

    /**
     * Getter method that returns the position in the y-axis
     *
     * @return position y-axis
     */
    private double getY()
    {
        return y.get();
    }

    /**
     * Method that identifies if a pixel is dead. If dead it can be removed to increase performance
     *
     * @return true if the pixel is dead or false if not
     */
    boolean isDead()
    {
        return alpha == 0;
    }

    /**
     * Method that identifies if a pixel is active
     *
     * @return true if active or false if inactive
     */
    boolean isActive()
    {
        return active;
    }

    /**
     * Method that activate the pixel
     *
     * @param velocity pixel velocity
     */
    void activate(Point2D velocity)
    {
        active = true;
        this.velocity = velocity;
    }

    /**
     * Method that update the vectors for the pixel
     */
    public void update()
    {
        if (!active)
            return;

        alpha -= 0.017 * 0.75;

        if (alpha < 0)
            alpha = 0;

        this.x.set(getX() + velocity.getX());
        this.y.set(getY() + velocity.getY());
    }

    /**
     * Method that draws the pixel
     *
     * @param g GraphicsContext used to draw
     */
    void draw(GraphicsContext g)
    {
        g.setFill(color);

        g.setGlobalAlpha(alpha);
        g.fillOval(getX(), getY(), 1, 1);
    }


}
