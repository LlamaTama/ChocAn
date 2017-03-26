/*
 * Copyright (C) 2017 vishc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Terminal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vishc
 */
public class DatabaseHelper 
{
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_URL = "jdbc:derby:Database\\ChocAnDB";
    
    //  Database credentials
    private static final String USER = "";
    private static String PASS = "";
    
    private Connection conn;
    private Statement stmt;
     
    public DatabaseHelper()
    {
        try 
        {
            //  Register JDBC driver
            Class.forName(JDBC_DRIVER);
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    public void open() 
    {
        try 
        {
            //  Connecting to database
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() 
    {
        try 
        {
            stmt.close();
            conn.close();
        } catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public boolean checkUser(String sql) 
     {
        try 
        {
            ResultSet rs = stmt.executeQuery(sql);
          
            //  Getting data from result set
            while(rs.next())
            {
                //System.out.println(rs.getInt(1));
                //System.out.println(rs.getString(2));
                return true;
            }
          
            rs.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
     }
    
}
