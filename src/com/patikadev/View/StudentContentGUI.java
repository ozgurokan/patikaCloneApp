package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Quiz;
import com.patikadev.Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentContentGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JPanel pnl_content_list;
    private JScrollPane scrl_content_list;
    private JTable tbl_content_list;
    private JPanel pnl_content_manage;
    private JButton btn_comment;
    private JTable tbl_quiz_list;
    private JScrollPane scrl_quiz_list;
    private JPanel pnl_quiz;
    private JTextField fld_quiz_id;
    private JTextField fld_content_id;
    private JComboBox comboBox1;
    private JButton btn_summit;

    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;

    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;


    private final Student student;
    private final Course course;
    public StudentContentGUI(Course course,Student student){
        this.course = course;
        this.student = student;

        Helper.setLayout();

        add(wrapper);
        setSize(840, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        fld_quiz_id.setEnabled(false);
        fld_content_id.setEnabled(false);

        mdl_content_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        Object[] col_course_list = {"ID", "Başlık", "Açıklama", "Link", "Ders", "Eğitmen"};
        mdl_content_list.setColumnIdentifiers(col_course_list);
        row_content_list = new Object[col_course_list.length];
        tbl_content_list.setModel(mdl_content_list);
        JTableHeader tableHeader = tbl_content_list.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String selected_content_id = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(),0).toString();
                fld_content_id.setText(selected_content_id);
                loadQuizModel();
            }catch (Exception exception){

            }

        });
        loadContentModel();


        // Quiz table
        mdl_quiz_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] col_quiz_list = {"ID", "Soru", "Birinci Şık", "İkinci Şık", "Üçüncü Şık", "Dördüncü Şık"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];
        tbl_quiz_list.setModel(mdl_quiz_list);
        JTableHeader tableHeader1 = tbl_quiz_list.getTableHeader();
        tableHeader1.setReorderingAllowed(false);
        tableHeader1.getColumnModel().getColumn(0).setMaxWidth(50);

        fld_quiz_id.setEnabled(false);
        tbl_quiz_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_quiz_id = tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(), 0).toString();
                fld_quiz_id.setText(selected_quiz_id);

            } catch (Exception exception) {

            }

        });

        btn_summit.addActionListener(e -> {
            Helper.showMessage("Cevap Kaydedildi");
        });
        btn_comment.addActionListener(e -> {
            CommentAddGUI commentAddGUI = new CommentAddGUI(Content.getFetch(Integer.parseInt(fld_content_id.getText())),this.student);
            
        });
    }

    public void loadContentModel() {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);

        int i = 0;
        for (Content obj :Content.getListByCourse(this.course.getId())) {
            i = 0;
            row_content_list[i++] = obj.getId();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getDescription();
            row_content_list[i++] = obj.getLink();
            row_content_list[i++] = obj.getCourse().getName();
            row_content_list[i++] = obj.getEducator().getName();
            mdl_content_list.addRow(row_content_list);
        }
    }
    public void loadQuizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
        clearModel.setRowCount(0);

        int i = 0;
        for (Quiz obj : Quiz.getList(Integer.parseInt(fld_content_id.getText()))) {
            i = 0;
            row_quiz_list[i++] = obj.getId();
            row_quiz_list[i++] = obj.getQuestion();
            row_quiz_list[i++] = obj.getCorrect_answer();
            row_quiz_list[i++] = obj.getSecond_answer();
            row_quiz_list[i++] = obj.getThird_answer();
            row_quiz_list[i++] = obj.getFourth_answer();
            mdl_quiz_list.addRow(row_quiz_list);
        }
    }
}
