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
public class SummaryReport 
{
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private final Date lastDate;
    private final Date currentDate;
    private final DatabaseHelper dbHelper = new DatabaseHelper();
    private int rowCount = 0;
    private int providerCount = 0;
    private int totalConsultations = 0;
    private int totalFee = 0;
    
    //  name of excel file
    private final String filename = Initializer.getHomeDirectory() + "\\Summary Report.xls";
    
    public SummaryReport(Date lDate, Date cDate) throws FileNotFoundException, IOException
    {
        lastDate = lDate;
        currentDate = cDate;
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet(sdf.format(new Date()));
        
         //  creating title row
        HSSFRow titleRow = sheet.createRow((short)rowCount);
        titleRow.createCell((short) 0).setCellValue("Provider Name");
        titleRow.createCell((short) 1).setCellValue("Consultations");
        titleRow.createCell((short) 2).setCellValue("Fee");
        
        ArrayList<Integer> providers = dbHelper.getActiveProviders(lastDate, currentDate);
        Iterator<Integer> providerIterator = providers.iterator();
        while(providerIterator.hasNext())
        {
            int fee = 0;
            int consultations = 0;
            int id = providerIterator.next();
            ArrayList<Integer> services = dbHelper.getActiveProviderServices(id, lastDate, currentDate);
            Iterator<Integer> serviceIterator = services.iterator();
            while(serviceIterator.hasNext())
            {
                consultations++;
                fee += dbHelper.getServicePrice(serviceIterator.next());
            }
            
            HSSFRow row = sheet.createRow((short)++rowCount);
            row.createCell((short) 0).setCellValue(dbHelper.getProviderName(id));
            row.createCell((short) 1).setCellValue("" + consultations);
            row.createCell((short) 2).setCellValue("" + fee);
            
            totalConsultations += consultations;
            totalFee += fee;
            providerCount++;
        }
        
        HSSFRow totalRow = sheet.createRow(++rowCount);
        totalRow.createCell((short) 0).setCellValue("Total = " + providerCount);
        totalRow.createCell((short) 1).setCellValue("" + totalConsultations);
        totalRow.createCell((short) 2).setCellValue("" + totalFee);
        
        CellStyle style = hwb.createCellStyle();
        Font f = hwb.createFont();
        f.setBold(true);
        style.setFont(f);
        
        for(int i = 0; i < 3; i++)
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