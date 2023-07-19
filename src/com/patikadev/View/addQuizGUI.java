package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addQuizGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_addQuiz;
    private JTextField fld_quiz_add_correctAnswer;
    private JButton btn_quiz_add_add;
    private JTextField fld_quiz_add_question;
    private JTextField fld_quiz_add_secondAnswer;
    private JTextField fld_quiz_add_thirdAnswer;
    private JTextField fld_quiz_add_fourthAnswer;


    public addQuizGUI(int content_id){
        add(wrapper);
        Helper.setLayout();
        setSize(480, 420);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);


        btn_quiz_add_add.addActionListener(e -> {

            if(Helper.isFieldEmpty(fld_quiz_add_question) || Helper.isFieldEmpty(fld_quiz_add_correctAnswer) || Helper.isFieldEmpty(fld_quiz_add_secondAnswer) || Helper.isFieldEmpty(fld_quiz_add_thirdAnswer) ||
                    Helper.isFieldEmpty(fld_quiz_add_fourthAnswer)){
                Helper.showMessage("fill");
            }else{
                if(Quiz.add(content_id,fld_quiz_add_question.getText(),fld_quiz_add_correctAnswer.getText(),fld_quiz_add_secondAnswer.getText(),
                        fld_quiz_add_thirdAnswer.getText(),fld_quiz_add_fourthAnswer.getText())){
                    Helper.showMessage("done");
                    dispose();
                }else{
                    Helper.showMessage("error");
                }
            }
        });
    }
}


