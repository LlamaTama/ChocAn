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

import External.DocumentSizeFilter;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.text.DefaultStyledDocument;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Tanesh Manjrekar
 */
public class ProviderTerminal extends JFrame implements ActionListener, KeyListener
{
    private CardLayout mainPanelLayout;
    private JPanel mainPanel,
            operationChoicePanel, 
            memberOperationPanel;
    private JButton memberOperationButton,
            generateProviderDirectoryButton,
            backToOperationChoiceButton,
            submitDetailsButton;
    private JTextField memberIDTextField,
            serviceCodeTextField,
            serviceNameTextField;
    private JTextArea commentsTextArea;
    private JScrollPane commentsScrollPane;
    private JLabel memberIDLabel,
            dateOfServiceLabel,
            serviceCodeLabel,
            serviceNameLabel,
            commentsLabel;
    JDateChooser dateChooser;
    
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
        pack();
        setSize(getPreferredSize());
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
        operationChoicePanel = new JPanel(new MigLayout("fill, align center center, wrap 1, gapy 10%"));
        initializeOperationChoiceComponents();
        memberOperationPanel = new JPanel(new MigLayout("fill"));
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
        memberIDLabel.setFont(Initializer.getDefaultFont());
        dateOfServiceLabel = new JLabel("Date of Service");
        dateOfServiceLabel.setFont(Initializer.getDefaultFont());
        serviceCodeLabel = new JLabel("Service Code");
        serviceCodeLabel.setFont(Initializer.getDefaultFont());
        serviceNameLabel = new JLabel("Service Name");
        serviceNameLabel.setFont(Initializer.getDefaultFont());
        commentsLabel = new JLabel("Comments");
        commentsLabel.setFont(Initializer.getDefaultFont());
        
        memberIDTextField = new JTextField();
        memberIDTextField.setFont(Initializer.getDefaultFont());
        
        dateChooser = new JDateChooser(new Date());
        dateChooser.setFont(Initializer.getDefaultFont());
        dateChooser.setDateFormatString("dd MMM yyyy");
        ((JTextFieldDateEditor) dateChooser.getComponent(1)).setHorizontalAlignment(JTextField.CENTER);
        
        serviceCodeTextField = new JTextField();
        serviceCodeTextField.setFont(Initializer.getDefaultFont());
        serviceCodeTextField.addKeyListener(this);
        serviceNameTextField = new JTextField();
        serviceCodeTextField.setFont(Initializer.getDefaultFont());
        serviceNameTextField.setEditable(false);
        
        commentsTextArea = new JTextArea();
        commentsTextArea.setFont(Initializer.getDefaultFont());
        commentsTextArea.setLineWrap(true);
        commentsTextArea.setWrapStyleWord(true);
        DefaultStyledDocument sizeDocument = new DefaultStyledDocument();
        sizeDocument.setDocumentFilter(new DocumentSizeFilter(100));
        commentsTextArea.setDocument(sizeDocument);
        commentsScrollPane = new JScrollPane(commentsTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        backToOperationChoiceButton = new JButton("Back");
        backToOperationChoiceButton.setFont(Initializer.getDefaultFont());
        backToOperationChoiceButton.addActionListener(this);
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
        operationChoicePanel.add(memberOperationButton, "h 15%:20%:30%, grow");
        operationChoicePanel.add(generateProviderDirectoryButton, "h 15%:20%:30%, grow");
    }
    
    private void addMemberOperationComponents()
    {
        memberOperationPanel.add(memberIDLabel, "grow");
        memberOperationPanel.add(memberIDTextField, "grow, span 2, wrap");
        memberOperationPanel.add(dateOfServiceLabel, "grow");
        memberOperationPanel.add(dateChooser, "grow, wrap");
        memberOperationPanel.add(serviceCodeLabel, "grow");
        memberOperationPanel.add(serviceCodeTextField, "grow,wrap");
        memberOperationPanel.add(serviceNameLabel, "grow");
        memberOperationPanel.add(serviceNameTextField, "grow, wrap");
        memberOperationPanel.add(commentsLabel, "span 1 3, aligny center, grow");
        memberOperationPanel.add(commentsScrollPane, "span 1 3, grow, w pref+150, h 60:70:80, wrap");
        memberOperationPanel.add(backToOperationChoiceButton, "cell 0 7");
        memberOperationPanel.add(submitDetailsButton, "cell 1 7, align right");
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
        }
        else if(ae.getSource().equals(generateProviderDirectoryButton))
        {
            
        }
        else if(ae.getSource().equals(backToOperationChoiceButton))
        {
            mainPanelLayout.show(mainPanel, "Choice");
        }
        else if(ae.getSource().equals(submitDetailsButton))
        {
            
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke)
    {
        if(ke.getSource().equals(serviceCodeTextField))
        {
            if(serviceCodeTextField.getText().length()==6)
            {
                serviceNameTextField.setText(getServiceName(serviceCodeTextField.getText()));
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) 
    {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) 
    {
        
    }
    
    private String getServiceName(String serviceCode)
    {
        
    }
    
    public static void main(String args[])
    {
        ProviderTerminal.createProviderTerminal();
    }
}
