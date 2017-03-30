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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vishc
 */
public class Scheduler 
{
    public static void generateReports() throws ParseException, IOException
    {
        DatabaseHelper dbHelper = new DatabaseHelper();
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
        
        try 
        {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(Initializer.getLastRunDate());
        } 
        catch (ParseException ex) 
        {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<Integer> members = dbHelper.getAllMembers();
        Iterator<Integer> memberIterator = members.iterator();
        while (memberIterator.hasNext())
        {
            Integer current = memberIterator.next();
            MemberReport mr = new MemberReport(current, new SimpleDateFormat("yyyy-MM-dd").parse(Initializer.getLastRunDate()), new Date());
        }
        
        ArrayList<Integer> providers = dbHelper.getAllProviders();
        Iterator<Integer> providerIterator = providers.iterator();
        while (providerIterator.hasNext())
        {
            Integer current = providerIterator.next();
            ProviderReport pr = new ProviderReport(current, new SimpleDateFormat("yyyy-MM-dd").parse(Initializer.getLastRunDate()), new Date());
        }
        
      
        
        
    }
}
