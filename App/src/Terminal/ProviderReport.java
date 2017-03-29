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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author vishc
 */
public class ProviderReport 
{
    private final int id;
    private final Date lastDate;
    private final Date currentDate;
    
    //  name of excel file
    String filename = "Reports\\ProviderReport.xls";
    
    public ProviderReport(int id, Date lastDate, Date currentDate) throws FileNotFoundException, IOException
    {
        this.id = id;
        this.lastDate = lastDate;
        this.currentDate = currentDate;
        
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Weekly Report");
        
        //  creating cells
        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell((short) 0).setCellValue("ID");
        rowhead.createCell((short) 1).setCellValue("Name");
        rowhead.createCell((short) 2).setCellValue("Address");
        rowhead.createCell((short) 3).setCellValue("State");
        rowhead.createCell((short) 4).setCellValue("City");
        rowhead.createCell((short) 5).setCellValue("Zip");
        
        //  database connection
        DatabaseHelper dbHelper = new DatabaseHelper();
        
        ArrayList<String[]> providerDetails = dbHelper.getUserDetails(id, "provider");
        Iterator<String []> providerIterator = providerDetails.iterator();
        int i = 0;
        while(providerIterator.hasNext())
        {
            String[] details = providerIterator.next();
            HSSFRow row =   sheet.createRow((short)i);
            row.createCell((short) 0).setCellValue(details[0]);
            row.createCell((short) 1).setCellValue(details[1]);
            i++;
        }
        
        sheet.createRow(i);
        sheet.createRow(i++);
        
        ArrayList<ArrayList> appointmentDetails = dbHelper.getAppointmentDetails(id, lastDate, currentDate);
        
        //  writing data to xls file
        FileOutputStream fileOut = new FileOutputStream(filename);
        hwb.write(fileOut);
        fileOut.close();
        System.out.println("Your excel file has been generated!"); 
    } 
}
