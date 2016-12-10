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

package smartMirror.dataModels.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author CodeHigh @copyright on 06/12/2016.
 *         Class responsible for establishing connection with the database
 */
public class MysqlCon
{

    private Connection con;

    /**
     * Method that establish the connection
     */
    public void dbConnect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7143433", "sql7143433", "CSqnX957Xb");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that closes the connection
     */
    public void closeConnection()
    {
        try
        {
            this.con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method that provides the connection
     *
     * @return the connection
     */
    public Connection getCon()
    {
        return con;
    }
}