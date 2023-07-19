package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Quiz;
import com.patikadev.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateQuizGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_addQuiz;
    private JTextField fld_quiz_update_correctAnswer;
    private JTextField fld_quiz_update_question;
    private JTextField fld_quiz_update_secondAnswer;
    private JTextField fld_quiz_update_thirdAnswer;
    private JTextField fld_quiz_update_fourthAnswer;
    private JButton btn_quiz_update_update;

    private final Quiz quiz;
    public UpdateQuizGUI(int id){
        this.quiz = Quiz.getFetch(id);

        add(wrapper);
        Helper.setLayout();
        setSize(480, 420);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        fld_quiz_update_question.setText(this.quiz.getQuestion());
        fld_quiz_update_correctAnswer.setText(this.quiz.getCorrect_answer());
        fld_quiz_update_secondAnswer.setText(this.quiz.getSecond_answer());
        fld_quiz_update_thirdAnswer.setText(this.quiz.getThird_answer());
        fld_quiz_update_fourthAnswer.setText(this.quiz.getFourth_answer());




        btn_quiz_update_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_quiz_update_question) || Helper.isFieldEmpty(fld_quiz_update_correctAnswer) || Helper.isFieldEmpty(fld_quiz_update_secondAnswer) || Helper.isFieldEmpty(fld_quiz_update_thirdAnswer) ||
                    Helper.isFieldEmpty(fld_quiz_update_fourthAnswer)){
                Helper.showMessage("fill");
            }else{
                if(Quiz.update(id,fld_quiz_update_question.getText(),fld_quiz_update_correctAnswer.getText(),fld_quiz_update_secondAnswer.getText(),
                        fld_quiz_update_thirdAnswer.getText(),fld_quiz_update_fourthAnswer.getText())){
                    Helper.showMessage("done");
                    dispose();
                }else{
                    Helper.showMessage("error");
                }
            }
        });
    }
}
