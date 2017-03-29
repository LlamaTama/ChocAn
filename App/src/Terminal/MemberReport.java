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
import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author vishc
 */
public class MemberReport 
{
    private final int id;
    
    //  name of excel file
    String filename = "Reports\\MemberReport.xls";
    
    public MemberReport(int id) throws FileNotFoundException, IOException
    {
        this.id = id;
        
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
        
        ArrayList<String[]> memberDetails = dbHelper.getUserDetails(id, "member");
        Iterator<String []> memberIterator = memberDetails.iterator();
        int i = 0;
        while(memberIterator.hasNext())
        {
            String[] details = memberIterator.next();
            HSSFRow row =   sheet.createRow((short)i);
            row.createCell((short) 0).setCellValue(details[0]);
            row.createCell((short) 1).setCellValue(details[1]);
            i++;
        }
        
        //  writing data to xls file
        FileOutputStream fileOut = new FileOutputStream(filename);
        hwb.write(fileOut);
        fileOut.close();
        System.out.println("Your excel file has been generated!");
        
    } 
}