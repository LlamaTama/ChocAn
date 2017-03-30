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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.stream.*;


/**
 *
 * @author vishc
 */
public class SummaryReport 
{
    
    //  name of excel file
    String filename = "Reports\\SummaryReport.xls";
    
    public SummaryReport() throws FileNotFoundException, IOException
    {
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("new sheet");
        
         //  creating cells
        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell((short) 0).setCellValue("Provider Name");
        rowhead.createCell((short) 1).setCellValue("No. Consultation");
        rowhead.createCell((short) 2).setCellValue("Overall Fee Total");
        
        //  databse connection
        DatabaseHelper dbHelper = new DatabaseHelper();
        dbHelper.open();
        Connection con = dbHelper.conn;
         try 
        {
            Statement st = dbHelper.stmt;
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            ResultSet rs = st.executeQuery("select distinct \"Provider ID\" from app.appointment ");
           
            String name[] = new String [10];
            int fees[] = new int[10];
            int id [] = new int[10];
            int id1 [] = new int[10];
            int noc=0;
            int sc[] = new int[10];
           int feestotal;
            int i=1;
            
           HSSFRow row = sheet.createRow((short)i);
            while(rs.next())
            {
                
                name[i] = rs.getString("Name");
                id[i] = rs.getInt("Provider ID");
            
               ResultSet rs3 = st3.executeQuery("select distinct \"Service Code\" from appointment");
                rs3.next();
                sc[i] = rs3.getInt("Service Code");
               ResultSet rs2 = st2.executeQuery("select distinct fees from provider_directory where \"Service Code\"="+ sc[i]);
               ResultSet rs1 = st1.executeQuery("select count(*) from appointment where \"Provider ID\"="+id[i]);
               rs1.next();
               noc=rs1.getInt("1");
                
               rs2.next();
               fees[i] = rs2.getInt("Fees");
                
               // feestotal = fees[i]
              
              
               
               
               
                row.createCell((short) 0).setCellValue(name[i]);
                row.createCell((short) 1).setCellValue(noc);
                i++;
            }
            
            
             //row.createCell((short) 2).setCellValue(feestotal);
             
             
             
            //  writing data to xls file
            FileOutputStream fileOut = new FileOutputStream(filename);
            hwb.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");
        
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MemberReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
            
    
}
