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
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.hssf.usermodel.HSSFRow;
import java.io.*;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;


/**
 *
 * @author vishc
 */
public class MemberReport 
{
    private final int id;
    private final Date lastDate;
    private final Date currentDate;
    private int rowCount = 0;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    
    //  name of excel file
    private final String filename;
    
    public MemberReport(int memID, Date lDate, Date cDate) throws FileNotFoundException, IOException
    {
        id = memID;
        lastDate = lDate;
        currentDate = cDate;
        
        //  database connection
        DatabaseHelper dbHelper = new DatabaseHelper();
        
        filename = Initializer.getHomeDirectory() + "\\" + dbHelper.getMemberName(id) + " " + sdf.format(currentDate) + ".xls";
        
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
        
        ArrayList<String[]> memberDetails = dbHelper.getUserDetails(id, "member");
        Iterator<String []> memberIterator = memberDetails.iterator();
        
        while(memberIterator.hasNext())
        {
            String[] details = memberIterator.next();
            HSSFRow row =   sheet.createRow((short)rowCount);
            row.createCell((short) 0).setCellValue(details[0]);
            row.createCell((short) 1).setCellValue(details[1]);
            rowCount++;
        }
        
        sheet.createRow(rowCount);
        sheet.createRow(rowCount++);
        
        HSSFRow titleRow = sheet.createRow(rowCount++);
        titleRow.createCell((short) 0).setCellValue("Date of Service");
        titleRow.createCell((short) 1).setCellValue("Provider Name");
        titleRow.createCell((short) 2).setCellValue("Service Name");
        
        ArrayList<ArrayList> appointmentDetails = dbHelper.getMemberAppointmentDetails(id, lastDate, currentDate);
        Iterator<ArrayList> appointmentIterator = appointmentDetails.iterator();
        while(appointmentIterator.hasNext())
        {
            ArrayList<String[]> appointment = appointmentIterator.next();
            String[] dateOfService = appointment.get(0);
            String[] providerName = {"Provider Name", dbHelper.getProviderName(Integer.parseInt((appointment.get(1))[1]))};
            String[] serviceName = {"Service Name", dbHelper.getServiceName((appointment.get(2))[1])};
            
            HSSFRow row = sheet.createRow((short)rowCount);
            row.createCell((short) 0).setCellValue(dateOfService[1]);
            row.createCell((short) 1).setCellValue(providerName[1]);
            row.createCell((short) 2).setCellValue(serviceName[1]);
            rowCount++;
        }
        
        CellStyle style = hwb.createCellStyle();
        Font f = hwb.createFont();
        f.setBold(true);
        style.setFont(f);
        
        for(int i = 0; i < 3; i++)
        {
            sheet.autoSizeColumn(i);
            titleRow.getCell(i).setCellStyle(style);
        }
        
        //  writing data to xls file
        FileOutputStream fileOut = new FileOutputStream(filename);
        hwb.write(fileOut);
        fileOut.close();
        System.out.println("Your excel file has been generated!");
    } 
    
    public String getFileName()
    {
        return filename;
    }
}