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

public class AddContentGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_content_add_top;
    private JLabel lbl_content_add_educator_id;
    private JComboBox cmb_content_add_course_list;
    private JTextField fld_content_add_title;
    private JTextField fld_content_add_link;
    private JButton btn_content_add_add;
    private JTextField fld_content_add_description;


    private final Educator educator;
    public AddContentGUI(Educator educator){
        this.educator = educator;

        add(wrapper);
        Helper.setLayout();
        setSize(480, 420);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        lbl_content_add_educator_id.setText(String.valueOf(educator.getId()));
        loadCoursesCombo();

        btn_content_add_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_content_add_title) || Helper.isFieldEmpty(fld_content_add_description) ||Helper.isFieldEmpty(fld_content_add_link) ){
                Helper.showMessage("fill");
            }else{
                Item courseItem = (Item) cmb_content_add_course_list.getSelectedItem();
               if(Content.add(this.educator.getId(),courseItem.getKey(),fld_content_add_title.getText(),fld_content_add_description.getText(),fld_content_add_link.getText())){
                   Helper.showMessage("done");

                   dispose();
               }else{
                   Helper.showMessage("error");
               }
            }

        });
    }

    public void loadCoursesCombo(){
        cmb_content_add_course_list.removeAllItems();
        for (Course obj: Course.getListByUser(educator.getId())) {
            cmb_content_add_course_list.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

}


