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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author vishc
 */
public class ProviderReport 
{
    private final int id;
    private final Date lastDate;
    private final Date currentDate;
    private int rowCount = 0;
    private int consultations = 0;
    private int totalFee = 0;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    
    //  name of excel file
    private final String filename;
    
    public ProviderReport(int provID, Date lDate, Date cDate) throws FileNotFoundException, IOException
    {
        id = provID;
        lastDate = lDate;
        currentDate = cDate;
        
        //  database connection
        DatabaseHelper dbHelper = new DatabaseHelper();
        
        filename = Initializer.getHomeDirectory() + "\\" + dbHelper.getProviderName(id) + " " + sdf.format(currentDate) + ".xls";
        
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Weekly Report");
        
        ArrayList<String[]> providerDetails = dbHelper.getUserDetails(id, "provider");
        Iterator<String []> providerIterator = providerDetails.iterator();
        while(providerIterator.hasNext())
        {
            String[] details = providerIterator.next();
            HSSFRow row = sheet.createRow((short)rowCount);
            row.createCell((short) 0).setCellValue(details[0]);
            row.createCell((short) 1).setCellValue(details[1]);
            rowCount++;
        }
        
        sheet.createRow(rowCount);
        sheet.createRow(rowCount++);
        
        HSSFRow titleRow = sheet.createRow(rowCount++);
        titleRow.createCell((short) 0).setCellValue("Date of Service");
        titleRow.createCell((short) 1).setCellValue("Received");
        titleRow.createCell((short) 2).setCellValue("Member Name");
        titleRow.createCell((short) 3).setCellValue("Member ID");
        titleRow.createCell((short) 4).setCellValue("Service Code");
        titleRow.createCell((short) 5).setCellValue("Service Fee");
        
        ArrayList<ArrayList> appointmentDetails = dbHelper.getProviderAppointmentDetails(id, lastDate, currentDate);
        Iterator<ArrayList> appointmentIterator = appointmentDetails.iterator();
        while(appointmentIterator.hasNext())
        {
            ArrayList<String[]> appointment = appointmentIterator.next();
            String[] dateOfService = appointment.get(0);
            String[] received = appointment.get(1);
            String[] memberID = appointment.get(2);
            String[] memberName = {"Member Name", dbHelper.getMemberName(Integer.parseInt(memberID[1]))};
            String[] serviceCode = appointment.get(3);
            String[] serviceFee = {"Service Fee", "" + dbHelper.getServicePrice(Integer.parseInt(serviceCode[1]))};
            
            HSSFRow row = sheet.createRow((short)rowCount);
            row.createCell((short) 0).setCellValue(dateOfService[1]);
            row.createCell((short) 1).setCellValue(received[1]);
            row.createCell((short) 2).setCellValue(memberName[1]);
            row.createCell((short) 3).setCellValue(memberID[1]);
            row.createCell((short) 4).setCellValue(serviceCode[1]);
            row.createCell((short) 5).setCellValue(serviceFee[1]);
            rowCount++;
            consultations++;
            totalFee += Integer.parseInt(serviceFee[1]);
        }
        
        HSSFRow totalRow = sheet.createRow(rowCount);
        totalRow.createCell((short) 0).setCellValue("Total");
        totalRow.createCell(1);
        totalRow.createCell((short) 2).setCellValue("" + consultations);
        totalRow.createCell(3);
        totalRow.createCell(4);
        totalRow.createCell((short) 5).setCellValue("" + totalFee);
        
        CellStyle style = hwb.createCellStyle();
        Font f = hwb.createFont();
        f.setBold(true);
        style.setFont(f);
        
        for(int i = 0; i < 6; i++)
        {
            sheet.autoSizeColumn(i);
            titleRow.getCell(i).setCellStyle(style);
            totalRow.getCell(i).setCellStyle(style);
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
