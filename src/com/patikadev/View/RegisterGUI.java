package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_uname;
    private JTextField fld_name;
    private JPasswordField fld_pass;
    private JButton btn_register;

    public RegisterGUI(){
        add(wrapper);
        setSize(400,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        btn_register.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_name) || Helper.isFieldEmpty(fld_uname) ||Helper.isFieldEmpty(fld_pass)){
                Helper.showMessage("fill");
            }else{
                if(User.add(fld_name.getText(),fld_uname.getText(),fld_pass.getText(),"student")){
                    Helper.showMessage("done");
                    dispose();
                }else{
                    Helper.showMessage("error");
                }
            }
        });
    }
}
