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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vishc
 */
public class DatabaseHelper 
{
    // JDBC driver name and database URL
    String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    String DB_URL = "jdbc:derby:Database\\ChocAnDB";
    
    //  Database credentials
    private String USER = "";
    private String PASS = "";
    
    public Connection conn;
    public Statement stmt;
     
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
    
    public boolean checkUser(String type, int id, String pw) 
    {
       open();
       
       boolean result = false;
       
       try 
       {
           PreparedStatement ps = conn.prepareStatement("select * from " + type + " where id=? and pass=?");
           ps.setInt(1, id);
           ps.setString(2, pw);
           
           ResultSet rs = ps.executeQuery();

           //  Getting data from result set
           if(rs.next())
           {
               //System.out.println(rs.getInt(1));
               //System.out.println(rs.getString(2));
               result = true;
           }

           rs.close();
       } 
       catch (SQLException ex) 
       {
           Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
       }

       close();
       return result;
    }
    
    public boolean checkUserID(int id, String type)
    {
        open();
       
       boolean result = false;
       
       try 
       {
           PreparedStatement ps = conn.prepareStatement("select * from app." + type + " where id=?");
           ps.setInt(1, id);
           
           ResultSet rs = ps.executeQuery();

           //  Getting data from result set
           if(rs.next())
           {
               //System.out.println(rs.getInt(1));
               //System.out.println(rs.getString(2));
               result = true;
           }

           rs.close();
       } 
       catch (SQLException ex) 
       {
           Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
       }

       close();
       return result;
    }
    
    public String getServiceName(String serviceCode)
    {
        open();
        
        String result = "Invalid service code!";
        
        try
        {
            ResultSet rs = stmt.executeQuery("select \"Service Name\" from app.provider_directory where \"Service Code\"=" + serviceCode);
            
            if(rs.next())
            {
                result = rs.getString(1);
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return result;
    }
    
    public boolean checkServiceCode(String serviceCode)
    {
        open();
        
        boolean result = false;
        
        try
        {
            ResultSet rs = stmt.executeQuery("select * from app.provider_directory where \"Service Code\"=" + serviceCode);
            
            if(rs.next())
            {
                result = true;
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return result;
    }
    
    public boolean insertAppointment(int memberID, int providerID, int serviceCode, Date serviceDate, Date currentDate, String comments)
    {
        open();
        
        boolean result = false;
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("insert into app.appointment values(?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, memberID);
            ps.setInt(2, providerID);
            ps.setInt(3, serviceCode);
            ps.setDate(4, new java.sql.Date(serviceDate.getTime()));
            ps.setDate(5, new java.sql.Date(currentDate.getTime()));
            ps.setString(6, comments);
            ps.setTime(7, new java.sql.Time(currentDate.getTime()));
            if(ps.executeUpdate()>0)
            {
                result = true;
            }

        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return result;
    }
    
    public int getServicePrice(int serviceCode)
    {
        open();
        
        int servicePrice = 0;
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select FEES from app.provider_directory where \"Service Code\"=?");
            ps.setInt(1, serviceCode);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                servicePrice = rs.getInt(1);
            }
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return servicePrice;
    }
    
    public ArrayList<String[]> getUserDetails(int id, String type)
    {
        open();
        
        ArrayList<String[]> userDetails = new ArrayList<String[]>();
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select * from app." + type + " where id=?");
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                userDetails.add(new String[]{"ID", "" + rs.getInt("ID")});
                userDetails.add(new String[]{"Name", "" + rs.getString("Name")});
                userDetails.add(new String[]{"Address", "" + rs.getString("Address")});
                userDetails.add(new String[]{"State", rs.getString("State")});
                userDetails.add(new String[]{"City", rs.getString("City")});
                userDetails.add(new String[]{"ZIP", rs.getString("ZIP")});
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return userDetails;
    }
    
    public ArrayList<ArrayList> getAppointmentDetails(int id, Date lastDate, Date currentDate)
    {
        open();
        
        ArrayList<ArrayList> appointments = new ArrayList<>();
        java.sql.Date lDate = new java.sql.Date(lastDate.getTime());
        java.sql.Date cDate = new java.sql.Date(currentDate.getTime());
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select * from app.appointment where id=? and \"Date of Service\">=? and \"Date of Service\"<?");
            ps.setInt(1, id);
            ps.setDate(2, lDate);
            ps.setDate(3, cDate);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                ArrayList<String[]> appointmentDetails = new ArrayList<>();
                appointmentDetails.add(new String[]{"Member ID", "" + rs.getInt(1)});
                appointmentDetails.add(new String[]{"Provider ID", "" + rs.getInt(2)});
                appointmentDetails.add(new String[]{"Service Code", "" + rs.getInt(3)});
                appointmentDetails.add(new String[]{"Date of Service", rs.getDate(4).toString()});
                appointmentDetails.add(new String[]{"Current Date", rs.getDate(5).toString()});
                appointmentDetails.add(new String[]{"Current Time", rs.getTime(7).toString()});
                appointmentDetails.add(new String[]{"Comments", rs.getString(6)});
                
                appointments.add(appointmentDetails);
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return appointments;
    }
}
