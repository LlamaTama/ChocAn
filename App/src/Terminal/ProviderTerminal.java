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

import javax.swing.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Tanesh Manjrekar
 */
public class ProviderTerminal extends JFrame implements ActionListener
{
    private JPanel operationChoicePanel, 
            memberOperationPanel;
    private JButton memberOperationButton,
            generateProviderDirectoryButton,
            backToOperationChoiceButton,
            selectDateButton,
            submitDetailsButton;
    private JTextField memberNumberTextField,
            serviceCodeTextField,
            commentsTextField;
    private JLabel memberNumberLabel,
            dateOfServiceLabel,
            serviceCodeLabel,
            commentsLabel;
    
    private ProviderTerminal()
    {
        //Calling super class constructor and setting layout constraints
        super();
        /*
        fillx - allows component to fill the space provided horizontally
        align center center - aligns components horizontally and vertically
        */
        setLayout(new MigLayout("fillx, align center center"));
        
        //Creating and adding components to frame
        initializeOperationChoiceComponents();
        addOperationChoiceComponents();
        
        //Frame constraints
        setSize(400, 180);
        setTitle("Provider");
        setResizable(true);
        setLocationRelativeTo(null); //centers frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void initializeOperationChoiceComponents()
    {
        
    }
    
    private void initializeMemberOperationComponents()
    {
        
    }
    
    private void addOperationChoiceComponents()
    {
        
    }
    
    private void addMemberOperationComponents()
    {
        
    }
    
    public static void createProviderTerminal()
    {
        ProviderTerminal pt = new ProviderTerminal();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        
    }
}
