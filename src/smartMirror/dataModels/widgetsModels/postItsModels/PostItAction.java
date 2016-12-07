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
 *         Class model for the post-it action
 */
public class PostItAction
{
    private String postItId;
    private String action;
    private String modification;

    /**
     * Constructor sets the post-it id, the action to be taken, and the modification to be done.
     *
     * @param postItId     id of the post-it
     * @param action       action to be taken Delete or Modify
     * @param modification in case of Modify it should contain the text to update the pos-it
     */
    public PostItAction(String postItId, String action, String modification)
    {
        this.postItId = postItId;
        this.action = action;
        this.modification = modification;
    }

    /**
     * Method responsible for identifying is the action is Delete
     *
     * @return true if the action is Delete
     */
    public boolean isActionDelete()
    {
        return this.action.equals("Delete");
    }

    /**
     * Method responsible for identifying is the action is Modify
     *
     * @return true if the action is Modify
     */
    public boolean isActionModify()
    {
        return this.action.equals("Modify");
    }

    /**
     * Getter method provides the post-it id
     *
     * @return id of the post-it
     */
    public String getPostItId()
    {
        return postItId;
    }

    /**
     * Getter method provides the text modification
     *
     * @return text modification
     */
    public String getModification()
    {
        return modification;
    }
}
