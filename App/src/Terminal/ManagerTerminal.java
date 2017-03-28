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
    private JButton viewMemberReportButton,
            viewProviderReportButton,
            viewSummaryReportButton;
    
    private ManagerTerminal()
    {
        //Calling super class constructor and setting layout constraints
        super();
        /*
        fillx - allows component to fill the space provided horizontally
        align center center - aligns components horizontally and vertically
        wrap 1 - goes to next line after every 1 component
        gapy 15 - 15 unit vertical gap between components
        */
        setLayout(new MigLayout("fillx, align center center, wrap 1, gapy 15"));
        
        //Creating and adding components to frame
        initializeComponents();
        addComponents();
        
        //Frame constraints
        setSize(400, 250);
        setTitle("Manager");
        setResizable(true);
        setLocationRelativeTo(null); //centers frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void initializeComponents()
    {
        viewMemberReportButton = new JButton("View Member Report");
        viewMemberReportButton.addActionListener(this);
        viewMemberReportButton.setFont(Initializer.getDefaultFont());
        viewProviderReportButton = new JButton("View Provider Report");
        viewProviderReportButton.addActionListener(this);
        viewProviderReportButton.setFont(Initializer.getDefaultFont());
        viewSummaryReportButton = new JButton("View Summary Report");
        viewSummaryReportButton.addActionListener(this);
        viewSummaryReportButton.setFont(Initializer.getDefaultFont());
    }
    
    private void addComponents()
    {
        /*
        grow - expand to fill available space
        */
        add(viewMemberReportButton, "grow");
        add(viewProviderReportButton, "grow");
        add(viewSummaryReportButton, "grow");
    }
    
    public static void createManagerTerminal()
    {
        ManagerTerminal mt = new ManagerTerminal();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource().equals(viewMemberReportButton))
        {
            
        }
        else if(ae.getSource().equals(viewProviderReportButton))
        {
            
        }
        else if(ae.getSource().equals(viewSummaryReportButton))
        {
            
        }
    }
}
