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

package smartMirror.dataModels.widgetsModels.postItsModels;

/**
 * @author CodeHigh @copyright on 07/12/2016.
 *         Class model for post-its
 */
public class PostItNote
{
    private String postItId;
    private String bodyText;
    private String senderId;
    private long timestamp;

    private int postItIndex;

    /**
     * Constructor sets the post-it id, text, color, timestamp
     *
     * @param postItId  id of the post-it
     * @param bodyText  post-it text
     * @param senderId  color of the post-it
     * @param timestamp post-it timestamp
     */
    public PostItNote(String postItId, String bodyText, String senderId, long timestamp)
    {
        this.postItId = postItId;
        this.bodyText = bodyText;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    /**
     * Getter method that provides the post-it id
     *
     * @return post-it id
     */
    public String getPostItId()
    {
        return postItId;
    }

    /**
     * Getter method that provides the post-it text
     *
     * @return post-it text
     */
    public String getBodyText()
    {
        return bodyText;
    }

    /**
     * Setter method that sets the post-it text
     *
     * @param bodyText post-it text
     */
    public void setBodyText(String bodyText)
    {
        this.bodyText = bodyText;
    }

    /**
     * Getter method that provides the post-it color
     *
     * @return post-it color
     */
    public String getSenderId()
    {
        return senderId;
    }

    /**
     * Getter method that provides the post-it timestamp
     *
     * @return post-it timestamp
     */
    public long getTimestamp()
    {
        return timestamp;
    }

    /**
     * Setter method that sets the post-it timestamp
     *
     * @param timestamp post-it timestamp
     */
    public void setTimestamp(int timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * Getter method that provides the post-it index
     *
     * @return post-it index
     */
    public int getPostItIndex()
    {
        return postItIndex;
    }


    /**
     * Setter method that sets the post-it index
     *
     * @param index post-it index
     */
    public void setPostItIndex(int index)
    {
        if (index > 12 || index < 0) throw new IllegalArgumentException("Just Integers Between 0 and 12 are Allowed");
        else this.postItIndex = index;
    }
}
