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

package smartMirror.dataModels.widgetsModels.qrCodeModels;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Observable;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         Class model that generates the QR code image
 */
public class QRCode extends Observable
{
    private String clientId;

    /**
     * Constructor sets the client id that is used to generate the QR code
     *
     * @param clientId UUID of the client
     */
    public QRCode(String clientId)
    {
        this.clientId = clientId;
    }

    /**
     * Method that creates the QR code image and notify its observers
     */
    public void getQRCode()
    {
        ByteArrayOutputStream out = net.glxn.qrgen.QRCode.from(clientId).to(ImageType.PNG).withSize(200, 180).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        Image image = new Image(in);

        PixelReader reader = image.getPixelReader();

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage newImage = new WritableImage(width, height);
        PixelWriter writer = newImage.getPixelWriter();

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                // reading a pixel from src image,
                // then writing a pixel to dest image
                Color color = reader.getColor(x, y);

                Color color1 = Color.web("0xffffffff");
                Color color3 = Color.web("#1d1d1d");


                if (color.equals(color1))
                {
                    writer.setColor(x, y, color3);
                }
                else
                {
                    writer.setColor(x, y, color1);
                }
            }
        }
        setChanged();
        notifyObservers(image);
    }
}
