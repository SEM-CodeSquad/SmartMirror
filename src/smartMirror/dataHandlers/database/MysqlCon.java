package smartMirror.dataHandlers.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Pucci @copyright on 06/12/2016.
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