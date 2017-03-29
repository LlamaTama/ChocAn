/*
 * Copyright (C) 2017 Tanesh Manjrekar
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

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author Tanesh Manjrekar
 */
public class Initializer 
{
    private static Font defaultFont = new Font("DejaVu Sans", Font.PLAIN, 22);
    
    public static void main(String args[])
    {
        displaySplashScreen();
        LoginTerminal.createLoginTerminal();
    }
    
    private static void displaySplashScreen()
    {
        
    }
    
    public static Font getDefaultFont()
    {
        return defaultFont;
    }
    
    public static String getLastRunDate()
    {
        String lastRun = new Date().toString();
        
        Properties p = new Properties();
        
        try
        {
            FileInputStream fis = new FileInputStream("config.properties");
            
            p.load(fis);
            
            lastRun = p.getProperty("lastRun");
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
        
        return lastRun;
    }
}
