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
import java.text.SimpleDateFormat;
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
    private String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private String DB_URL = "jdbc:derby:Database\\ChocAnDB";
    
    //  Database credentials
    private String USER = "";
    private String PASS = "";
    
    public Connection conn;
    public Statement stmt;
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
     
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
    
    //validate user credentials
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
    
    //check if user exists
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
    
    //get service name from service code
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
    
    //check if service code is valid
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
    
    //insert appointment details
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
    
    //get service fee from service code
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
    
    //get details of user with specified id
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
    
    //get appointment details for member within specified date range
    public ArrayList<ArrayList> getMemberAppointmentDetails(int id, Date lastDate, Date currentDate)
    {
        open();
        
        ArrayList<ArrayList> appointments = new ArrayList<>();
        java.sql.Date lDate = new java.sql.Date(lastDate.getTime());
        java.sql.Date cDate = new java.sql.Date(currentDate.getTime());
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select \"Date of Service\", \"Provider ID\", \"Service Code\" from app.appointment where \"Member ID\"=? and \"Date of Service\">=? and \"Date of Service\"<? order by \"Date of Service\"");
            ps.setInt(1, id);
            ps.setDate(2, lDate);
            ps.setDate(3, cDate);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                ArrayList<String[]> appointmentDetails = new ArrayList<>();
                appointmentDetails.add(new String[]{"Date of Service", sdf.format(rs.getDate(1))});
                appointmentDetails.add(new String[]{"Provider ID", "" + rs.getInt(2)});
                appointmentDetails.add(new String[]{"Service Code", "" + rs.getInt(3)});
                
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
    
    //get appointment details for provider within specified date range
    public ArrayList<ArrayList> getProviderAppointmentDetails(int id, Date lastDate, Date currentDate)
    {
        open();
        
        ArrayList<ArrayList> appointments = new ArrayList<>();
        java.sql.Date lDate = new java.sql.Date(lastDate.getTime());
        java.sql.Date cDate = new java.sql.Date(currentDate.getTime());
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select * from app.appointment where \"Provider ID\"=? and \"Date of Service\">=? and \"Date of Service\"<? order by \"Current Date\"");
            ps.setInt(1, id);
            ps.setDate(2, lDate);
            ps.setDate(3, cDate);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                ArrayList<String[]> appointmentDetails = new ArrayList<>();
                
                appointmentDetails.add(new String[]{"Date of Service", sdf.format(rs.getDate(4))});
                appointmentDetails.add(new String[]{"Received", sdf.format(rs.getDate(5)) + " " + rs.getTime(7).toString()});
                appointmentDetails.add(new String[]{"Member ID", "" + rs.getInt(1)});
                appointmentDetails.add(new String[]{"Service Code", "" + rs.getInt(3)});

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
    
    //get member name from id
    public String getMemberName(int id)
    {
        open();
        
        String result = "";
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select name from app.member where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
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
    
    //get provider name from id
    public String getProviderName(int id)
    {
        open();
        
        String result = "";
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select name from app.provider where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
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
    
    //get id of all members
    public ArrayList<Integer> getAllMembers()
    {
        open();
        
        ArrayList<Integer> memberID = new ArrayList<>();
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select id from app.member");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                memberID.add(rs.getInt(1));
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return memberID;
    }
    
    //get id of all providers
    public ArrayList<Integer> getAllProviders()
    {
        open();
        
        ArrayList<Integer> providerID = new ArrayList<>();
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select id from app.provider");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                providerID.add(rs.getInt(1));
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return providerID;
    }
    
    //get list of all services
    public ArrayList<String []> getAllServices()
    {
        open();
        
        ArrayList<String[]> services = new ArrayList<>();
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select * from app.provider_directory");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                String service[] = {rs.getString(1), ""+ rs.getInt(2), ""+ rs.getInt(3)};
                services.add(service);
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return services;
    }
    
    //get list of all providers who provided a service in the specified date range
    public ArrayList<Integer> getActiveProviders(Date lastDate, Date currentDate)
    {
        open();
        
        ArrayList<Integer> providers = new ArrayList<>();
        java.sql.Date lDate = new java.sql.Date(lastDate.getTime());
        java.sql.Date cDate = new java.sql.Date(currentDate.getTime());
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select distinct \"Provider ID\" from app.appointment where \"Date of Service\">=? and \"Date of Service\"<?");
            ps.setDate(1, lDate);
            ps.setDate(2, cDate);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                providers.add(rs.getInt(1));
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return providers;
    }
    
    //get services offered by provider within specified date range
    public ArrayList<Integer> getActiveProviderServices(int id, Date lastDate, Date currentDate)
    {
        open();
        
        ArrayList<Integer> services = new ArrayList<>();
        java.sql.Date lDate = new java.sql.Date(lastDate.getTime());
        java.sql.Date cDate = new java.sql.Date(currentDate.getTime());
        
        try
        {
            PreparedStatement ps = conn.prepareStatement("select \"Service Code\" from app.appointment where \"Provider ID\"=? and \"Date of Service\">=? and \"Date of Service\"<?");
            ps.setInt(1, id);
            ps.setDate(2, lDate);
            ps.setDate(3, cDate);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                services.add(rs.getInt(1));
            }
            
            rs.close();
        }
        catch(SQLException se)
        {
            System.out.println(se);
        }
        
        close();
        
        return services;
    }
}
