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
import javax.swing.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;
/**
 *
 * @author vishc
 */
public class ManagerTerminal extends JFrame implements ActionListener
{
    private ManagerTerminal()
    {
        //Calling super class constructor and setting layout constraints
        super();
        setLayout(new MigLayout("fillx, align center center"));
        
        //Creating and adding components to frame
        initializeComponents();
        addComponents();
        
        //Frame constraints
        setSize(400, 180);
        setTitle("Manager");
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void initializeComponents()
    {
        
    }
    
    private void addComponents()
    {
        
    }
    
    public static void createManagerTerminal()
    {
        ManagerTerminal mt = new ManagerTerminal();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        
    }
}
