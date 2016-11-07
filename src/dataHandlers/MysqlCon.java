package dataHandlers;

import java.sql.*;

class MysqlCon{

    private Connection con;

    public void dbConnect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7143433","sql7143433","CSqnX957Xb");
        }
        catch(Exception e)
        { e.printStackTrace();
        }
    }

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

    public Connection getCon()
    {
        return con;
    }
}