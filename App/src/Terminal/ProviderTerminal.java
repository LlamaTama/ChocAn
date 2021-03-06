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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultStyledDocument;
import net.miginfocom.swing.MigLayout;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 *
 * @author Tanesh Manjrekar
 */
public class ProviderTerminal extends JFrame implements ActionListener, KeyListener
{
    private final int providerID;
    private final DatabaseHelper dbHelper;
    private boolean serviceCodeValid;
    
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
    
    private ProviderTerminal(int providerID)
    {
        //Calling super class constructor and setting layout constraints
        super();
        this.providerID = providerID;
        dbHelper = new DatabaseHelper();
        serviceCodeValid = false;
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
        dateChooser.setDateFormatString("yyyy-MM-dd");
        ((JTextFieldDateEditor) dateChooser.getComponent(1)).setHorizontalAlignment(JTextField.CENTER);
        
        serviceCodeTextField = new JTextField();
        serviceCodeTextField.setFont(Initializer.getDefaultFont());
        serviceCodeTextField.addKeyListener(this);
        serviceNameTextField = new JTextField();
        serviceNameTextField.setFont(Initializer.getDefaultFont());
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
    
    private void createAndShowProviderDirectory()
    {
        String filename = Initializer.getHomeDirectory() + "\\Provider Directory.xls";
        int rowCount = 0;
        
        //create workbook
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Services");
        
        //create title row
        HSSFRow titleRow = sheet.createRow(rowCount);
        titleRow.createCell((short) 0).setCellValue("Service Name");
        titleRow.createCell((short) 1).setCellValue("Service Code");
        titleRow.createCell((short) 2).setCellValue("Service Fee");
        
        //get all services
        ArrayList<String[]> services = dbHelper.getAllServices();
        Iterator<String[]> servicesIterator = services.iterator();
        while(servicesIterator.hasNext())
        {
            HSSFRow row = sheet.createRow((short)++rowCount);
            String service[] = servicesIterator.next();
            row.createCell((short) 0).setCellValue(service[0]);
            row.createCell((short) 1).setCellValue(service[1]);
            row.createCell((short) 2).setCellValue(service[2]);
        }
        
        //bold style for rows
        CellStyle style = hwb.createCellStyle();
        org.apache.poi.ss.usermodel.Font f = hwb.createFont();
        f.setBold(true);
        style.setFont(f);
        
        //autosize columns
        for(int i = 0; i < 3; i++)
        {
            sheet.autoSizeColumn(i);
            titleRow.getCell(i).setCellStyle(style);
        }
        
        //write file
        try (FileOutputStream fileOut = new FileOutputStream(filename)) 
        {
            hwb.write(fileOut);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ProviderTerminal.class.getName()).log(Level.SEVERE, null, ex);
        }
        try 
        {
            Desktop.getDesktop().open(new File(filename));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ProviderTerminal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createProviderTerminal(int providerID)
    {
        ProviderTerminal pt = new ProviderTerminal(providerID);
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
            createAndShowProviderDirectory();
        }
        else if(ae.getSource().equals(backToOperationChoiceButton))
        {
            mainPanelLayout.show(mainPanel, "Choice");
        }
        else if(ae.getSource().equals(submitDetailsButton))
        {
            if(serviceCodeValid)
            {        
                //insert appointment details
                if(dbHelper.insertAppointment(Integer.parseInt(memberIDTextField.getText()), providerID, Integer.parseInt(serviceCodeTextField.getText()), dateChooser.getDate(), new Date(), commentsTextArea.getText()))
                {
                    JOptionPane.showMessageDialog(this, "Fees due are " + dbHelper.getServicePrice(Integer.parseInt(serviceCodeTextField.getText())), "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    memberIDTextField.setText("");
                    dateChooser.setDate(new Date());
                    serviceCodeTextField.setText("");
                    serviceNameTextField.setText("");
                    commentsTextArea.setText("");
                    
                }
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke)
    {
        //get service name from service code
        if(ke.getSource().equals(serviceCodeTextField))
        {
            if(serviceCodeTextField.getText().length()==6)
            {
                serviceNameTextField.setText(dbHelper.getServiceName(serviceCodeTextField.getText()));
                if(dbHelper.checkServiceCode(serviceCodeTextField.getText()))
                {
                    serviceCodeValid = true;
                }
            }
            else
            {
                serviceNameTextField.setText("");
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
    
    public static void main(String args[])
    {
        ProviderTerminal.createProviderTerminal(111111111);
    }
}
