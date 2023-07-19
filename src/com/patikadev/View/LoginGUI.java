package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Educator;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Student;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JTextField fld_login_uname;
    private JPasswordField fld_login_pass;
    private JButton btn_login_login;
    private JButton btn_register;

    public LoginGUI(){

        add(wrapper);
        setSize(400,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);


        btn_login_login.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_login_uname) ||Helper.isFieldEmpty(fld_login_pass)){
                Helper.showMessage("fill");
            }else{
                    User u = User.getFetch(fld_login_uname.getText(),fld_login_pass.getText());
                    if(u == null){
                        Helper.showMessage("Kullanıcı Bulunamadı!");
                    }else{
                        switch (u.getType()){
                            case "operator":
                                OperatorGUI opGUI = new OperatorGUI((Operator) u);
                                break;
                            case "educator":
                                EducatorGUI edGUI = new EducatorGUI((Educator) u);
                                break;
                            case "student":
                                StudentGUI stGUI = new StudentGUI((Student) u);
                                break;

                        }
                        dispose();
                    }
            }
        });
        btn_register.addActionListener(e -> {
                RegisterGUI registerGUI = new RegisterGUI();

        });
    }

    public static void main(String[] args){
        Helper.setLayout();
        LoginGUI login = new LoginGUI();

    }
}
