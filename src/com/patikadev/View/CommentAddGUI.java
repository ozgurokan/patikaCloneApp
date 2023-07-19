package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Comment;
import com.patikadev.Model.Content;
import com.patikadev.Model.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommentAddGUI extends JFrame{
    private JPanel wrapper;
    private JTextPane txtp_comment;
    private JButton btn_add;

    private final Content content;
    private final Student student;
    public CommentAddGUI(Content content, Student student){
        this.content = content;
        this.student = student;
        Helper.setLayout();
        add(wrapper);
        setSize(450, 450);

        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());

        setLocation(x, y);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        btn_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(txtp_comment)){
                Helper.showMessage("fill");
            }else{
                if(Comment.add(this.student.getId(),txtp_comment.getText(),this.content.getId())){
                    Helper.showMessage("done");
                    dispose();
                }else{
                    Helper.showMessage("error");
                }
            }

        });
    }
}
