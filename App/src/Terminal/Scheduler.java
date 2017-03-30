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

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author vishc
 */
public class Scheduler 
{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private static final DatabaseHelper dbHelper = new DatabaseHelper();
    
    private static void generateReports() throws ParseException, IOException
    {
        Date startDate = sdf.parse(Initializer.getLastRunDate());
        Date endDate = new Date();
        
        ArrayList<Integer> members = dbHelper.getAllMembers();
        Iterator<Integer> memberIterator = members.iterator();
        while (memberIterator.hasNext())
        {
            Integer current = memberIterator.next();
            MemberReport mr = new MemberReport(current, startDate,endDate);
        }
        
        ArrayList<Integer> providers = dbHelper.getAllProviders();
        Iterator<Integer> providerIterator = providers.iterator();
        while (providerIterator.hasNext())
        {
            Integer current = providerIterator.next();
            ProviderReport pr = new ProviderReport(current, startDate, endDate);
        }
        
        SummaryReport sr = new SummaryReport(startDate, endDate);
        
        Properties p = new Properties();
        
        try
        {
            FileOutputStream fos = new FileOutputStream("src\\Resources\\config.properties");
            
            p.setProperty("lastRun", sdf.format(new Date()));
            
            p.store(fos, null);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }
    
    public static void schedule()
    {
        try
        {
            while(true)
            {
                Calendar c = new GregorianCalendar(Locale.CANADA);
                int currentHour = c.get(Calendar.HOUR_OF_DAY);
                int currentDay = c.get(Calendar.DAY_OF_WEEK);
                
                if(currentHour==0 && currentDay==5)
                {
                    generateReports();
                }
                
                Thread.sleep(60000);
            }
        }
        catch(InterruptedException | ParseException | IOException ie)
        {
            System.out.println(ie);
        }
    }
}
