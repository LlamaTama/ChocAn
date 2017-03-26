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
import java.awt.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Tanesh Manjrekar
 */
public class ProviderTerminal extends JFrame implements ActionListener
{
    private CardLayout mainPanelLayout;
    private JPanel mainPanel,
            operationChoicePanel, 
            memberOperationPanel;
    private JButton memberOperationButton,
            generateProviderDirectoryButton,
            backToOperationChoiceButton,
            selectDateButton,
            submitDetailsButton;
    private JTextField memberIDTextField,
            dateOfServiceTextField,
            serviceCodeTextField;
    private JTextArea commentsTextArea;
    private JLabel memberIDLabel,
            dateOfServiceLabel,
            serviceCodeLabel,
            serviceNameLabel,
            commentsLabel;
    
    private ProviderTerminal()
    {
        //Calling super class constructor and setting layout constraints
        super();
        /*
        fillx - allows component to fill the space provided horizontally
        align center center - aligns components horizontally and vertically
        */
        setLayout(new MigLayout("fill, align center center"));
        
        initializeComponents();
        addComponents();
        
        //Frame constraints
        setSize(400, 180);
        setTitle("Provider");
        setResizable(true);
        setLocationRelativeTo(null); //centers frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void initializeComponents()
    {   
        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);
        /*
        fillx - allows component to fill the space provided horizontally
        align center center - aligns components horizontally and vertically
        wrap 1 - goes to next line after every 1 component
        gapy 15 - 15 unit vertical gap between components
        */
        operationChoicePanel = new JPanel(new MigLayout("fillx, align center center, wrap 1, gapy 15"));
        initializeOperationChoiceComponents();
        memberOperationPanel = new JPanel(new MigLayout("fillx"));
        initializeMemberOperationComponents();
    }
    
    private void initializeOperationChoiceComponents()
    {
        memberOperationButton = new JButton("Member Billing");
        memberOperationButton.setFont(Initializer.getDefaultFont());
        memberOperationButton.addActionListener(this);
        generateProviderDirectoryButton = new JButton("Generate Provider Directory");
        generateProviderDirectoryButton.setFont(Initializer.getDefaultFont());
        generateProviderDirectoryButton.addActionListener(this);
    }
    
    private void initializeMemberOperationComponents()
    {
        memberIDLabel = new JLabel("Member ID");
        dateOfServiceLabel = new JLabel("Date of Service");
        serviceCodeLabel = new JLabel("Service Code");
        serviceNameLabel = new JLabel();
        commentsLabel = new JLabel("Comments");
        
        memberIDTextField = new JTextField();
        dateOfServiceTextField = new JTextField();
        serviceCodeTextField = new JTextField();
        commentsTextArea = new JTextArea();
        
        backToOperationChoiceButton = new JButton("Back");
        backToOperationChoiceButton.setFont(Initializer.getDefaultFont());
        backToOperationChoiceButton.addActionListener(this);
        selectDateButton = new JButton("Select date");
        selectDateButton.setFont(Initializer.getDefaultFont());
        selectDateButton.addActionListener(this);
        submitDetailsButton = new JButton("Submit");
        submitDetailsButton.setFont(Initializer.getDefaultFont());
        submitDetailsButton.addActionListener(this);
    }
    
    private void addComponents()
    {
        addOperationChoiceComponents();
        mainPanel.add(operationChoicePanel, "Choice");
        addMemberOperationComponents();
        mainPanel.add(memberOperationPanel, "Billing");
        mainPanelLayout.show(mainPanel, "Choice");
        
        add(mainPanel, "grow");
    }
    
    private void addOperationChoiceComponents()
    {
        operationChoicePanel.add(memberOperationButton, "grow");
        operationChoicePanel.add(generateProviderDirectoryButton, "grow");
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
        if(ae.getSource().equals(memberOperationButton))
        {
            mainPanelLayout.show(mainPanel,"Billing");
            pack();
            setSize(getPreferredSize());
        }
        else if(ae.getSource().equals(generateProviderDirectoryButton))
        {
            mainPanelLayout.show(mainPanel, "Choice");
            setSize(400, 180);
        }
        else if(ae.getSource().equals(backToOperationChoiceButton))
        {
            
        }
        else if(ae.getSource().equals(selectDateButton))
        {
            
        }
        else if(ae.getSource().equals(submitDetailsButton))
        {
            
        }
    }
}
