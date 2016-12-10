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

package smartMirror.controllers.interfaceControllers.widgetsControllers.qrCodeController;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;
import java.util.Observer;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for updating the QRCodeView
 */
public class QRCodeController implements Observer
{
    public AnchorPane qrCodePane;
    public ImageView qrCodeImageView;

    /**
     * Method responsible for setting the QR code image
     *
     * @param image QR code image to be set
     */
    private void setQrCodeImage(Image image)
    {
        this.qrCodeImageView.setImage(image);
    }

    /**
     * Update method where the observable classes sends notifications messages
     *
     * @param o   observable object
     * @param arg object arg
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof Image)
        {
            Image image = (Image) arg;
            setQrCodeImage(image);
        }
    }
}
