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
    String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    String JDBC_CLASSPATH = "App\\lib\\CopyLibs\\derby.jar";
    String DB_URL = "App\\Database\\ChocAnDB";
    
    private String USER = "";
    private String PASS = "";
    
    private Connection conn = null;
    private Statement stmt = null;
     
    public DatabaseHelper()
    {
        try 
        {
            Class.forName("JDBC_DRIVER");
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
            conn = DriverManager.getConnection(DB_URL, USER,PASS);
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
    
     public void test() 
     {
        try 
        {
            String sql;
            sql = "SELECT * FROM Provider";
            ResultSet rs = stmt.executeQuery(sql); // DML
          
            while(rs.next())
            {
              
                System.out.print(rs.getString(1));
                System.out.print(rs.getString(2));
            }
          
            rs.close();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
