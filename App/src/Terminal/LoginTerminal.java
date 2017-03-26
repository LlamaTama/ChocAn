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
import java.awt.Font;

/**
 *
 * @author Tanesh Manjrekar
 */
public class LoginTerminal extends JFrame implements ActionListener
{
    private JTextField idTextField;
    private JPasswordField passwordField;
    private JLabel idLabel,
            passwordLabel;
    private ButtonGroup userType;
    private JRadioButton providerTypeRadioButton, 
            managerTypeRadioButton;
    private JButton loginButton;
    
    private String tableName = "APP.PROVIDER";
    
    /*
    Making the constructor private and using a static method for creation of the terminal, 
    to ensure that at any given moment, there's only one instance of the Login Terminal
    */
    private LoginTerminal()
    {
        //Calling super class constructor and setting layout constraints
        super();
        
        /*
        fillx - allows component to fill the space provided horizontally
        align center center - aligns components horizontally and vertically
        */
        setLayout(new MigLayout("fillx, align center center"));
        
        //Creating and adding components to frame
        initializeComponents();
        addComponents();
        
        //Frame constraints
        setSize(500, 258);
        setTitle("Login");
        setResizable(true);
        setLocationRelativeTo(null); //centers frame on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void initializeComponents()
    {
        idTextField = new JTextField();
        passwordField = new JPasswordField();
        
        Font font1 = new Font("DejaVu Sans", Font.PLAIN, 22);
        
        idLabel = new JLabel("ID");
        idLabel.setFont(font1);
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(font1);
        
        providerTypeRadioButton = new JRadioButton("Provider");
        providerTypeRadioButton.setFont(font1);
        providerTypeRadioButton.setSelected(true);
        managerTypeRadioButton = new JRadioButton("Manager");
        managerTypeRadioButton.setFont(font1);
        userType = new ButtonGroup();
        userType.add(providerTypeRadioButton);
        userType.add(managerTypeRadioButton);
        
        loginButton = new JButton("Login");
        loginButton.setFont(font1);
        loginButton.addActionListener(this);
    }
    
    private void addComponents()
    {
        /*
        wrap - go to next line after this
        grow - expand to fill available space
        split 2 - ensures 2 components (this one and next) are in the same cell
        span 2 - makes the cell span 2 cell spaces horizontally
        align center - centers the component
        */
        add(idLabel);
        add(idTextField, "wrap, grow");
        add(passwordLabel);
        add(passwordField, "wrap, grow");
        add(providerTypeRadioButton, "split 2, span 2, align center,gapy 0.4cm");
        add(managerTypeRadioButton, "wrap,");
        add(loginButton, "span 2, align center,gapy 0.4cm,gapafter 0.4cm");
        
    }
    
    public static void createLoginTerminal()
    {
        LoginTerminal lt = new LoginTerminal();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource().equals(loginButton))
        {
            if(validateID())
            {
                //Testing validation
                //JOptionPane.showMessageDialog(this, "Success", "Yay!", JOptionPane.INFORMATION_MESSAGE);
                
                if(providerTypeRadioButton.isSelected())
                {
                    tableName = "APP.PROVIDER";
                }
                else
                {
                    tableName = "APP.MANAGER";
                }
                
                validateUser();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "ID must be 9 digits only!", "ID Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validateID()
    {
        //Regex for 9 digit ID
        return idTextField.getText().matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d");
    }
    
    private void validateUser()
    {
        String id = idTextField.getText();
        String pw = new String(passwordField.getPassword());
        String sql = "select * from " + tableName + " where id=" + id + " and pass='" + pw + "'";
        
        //Creating helper object to access database
        DatabaseHelper dbHelper = new DatabaseHelper();
        dbHelper.open();
        if(dbHelper.checkUser(sql))
        {
            if(providerTypeRadioButton.isSelected())
            {
                ProviderTerminal.createProviderTerminal();
            }
            else
            {
                ManagerTerminal.createManagerTerminal();
            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dbHelper.close();
    }
}
