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

package smartMirror.dataModels.applicationModels;

/**
 * @author CodeHigh on 29/11/2016.
 *         Class containg the model for the preferences element
 */
public class Preferences
{
    private String name;
    private String value;

    /**
     * Constructor builds a element with the name of the preferences and its value
     *
     * @param name  preferences name
     * @param value value of the preferences
     */
    public Preferences(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * Getter that provides the name of the preference
     *
     * @return preference name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter that provides the value of the preference
     *
     * @return preference value
     */
    public String getValue()
    {
        return value;
    }
}
