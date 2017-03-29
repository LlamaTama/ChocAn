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

/**
 *
 * @author vishc
 */
public class ProviderReport {
    
    //  name of excel file
    String filename = "Reports\\ProviderReport.xls";
    
    public ProviderReport() throws FileNotFoundException, IOException
    {
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("new sheet");
        
        //  creating cells
        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell((int) 0).setCellValue("ID");
        rowhead.createCell((short) 1).setCellValue("Name");
        rowhead.createCell((short) 2).setCellValue("Address");
        rowhead.createCell((short) 3).setCellValue("State");
        rowhead.createCell((short) 4).setCellValue("City");
        rowhead.createCell((short) 5).setCellValue("Zip");
        
        //  databse connection
        DatabaseHelper dbHelper = new DatabaseHelper();
        dbHelper.open();
        
        try 
        {
            Statement st = dbHelper.stmt;
            ResultSet rs = st.executeQuery("select * from Provider");
        
        //  iteration for inserting values in rows
        int i=1;
        while(rs.next()){
        HSSFRow row=   sheet.createRow((short)i);
        row.createCell((short) 0).setCellValue(Integer.toString(rs.getInt("ID")));
        row.createCell((short) 1).setCellValue(rs.getString("Name"));
        row.createCell((short) 2).setCellValue(rs.getString("Address"));
        row.createCell((short) 3).setCellValue(rs.getString("State"));
        row.createCell((short) 4).setCellValue(rs.getString("City"));
        row.createCell((short) 5).setCellValue(Integer.toString(rs.getInt("Zip")));
        i++;
        }
        //  writing data to xls file
        FileOutputStream fileOut = new FileOutputStream(filename);
        hwb.write(fileOut);
        fileOut.close();
        System.out.println("Your excel file has been generated!");
        
        } catch (SQLException ex) 
        {
            Logger.getLogger(MemberReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
