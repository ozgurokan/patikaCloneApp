package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateContentGUI extends JFrame{
    private JPanel pnl_content_add_top;
    private JLabel lbl_content_update_educator_id;
    private JComboBox cmb_content_update_course_list;
    private JTextField fld_content_update_title;
    private JTextField fld_content_update_link;
    private JTextField fld_content_update_description;
    private JButton btn_content_update_update;
    private JPanel wrapper;
    private JLabel lbl_content_update_content_id;

    private final Content content;
    private final Educator educator;
    public UpdateContentGUI(Content content,Educator educator){
        this.content = content;
        this.educator = educator;

        Helper.setLayout();
        add(wrapper);
        setSize(480, 420);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        loadCourseCombo();
        lbl_content_update_content_id.setText(String.valueOf(content.getId()));
        lbl_content_update_educator_id.setText(String.valueOf(educator.getId()));

        fld_content_update_title.setText(content.getTitle());
        fld_content_update_description.setText(content.getDescription());
        fld_content_update_link.setText(content.getLink());



        btn_content_update_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_content_update_title) || Helper.isFieldEmpty(fld_content_update_description) ||Helper.isFieldEmpty(fld_content_update_description)){
                Helper.showMessage("fill");
            }else{
                if(Helper.confirm("Güncellensin mi?")){
                    Item selected_course =(Item) cmb_content_update_course_list.getSelectedItem();
                    if(Content.update(this.content.getId(),selected_course.getKey(),fld_content_update_title.getText(),fld_content_update_description.getText(),fld_content_update_link.getText())){
                        Helper.showMessage("done");
                        dispose();
                    }else{
                        Helper.showMessage("error");
                    }
                }

            }
        });


    }

    public void loadCourseCombo() {
        cmb_content_update_course_list.removeAllItems();

        for (Course obj: Course.getListByUser(educator.getId())){
            cmb_content_update_course_list.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

}

