package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Course;
import com.patikadev.Model.Patika;
import com.patikadev.Model.Student;
import com.patikadev.Model.UserPatikaRelation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JButton btn_std_logout;
    private JPanel pnl_stu_top;
    private JTabbedPane tabbedPane1;
    private JPanel pnl_std_patikaList;
    private JPanel pnl_std_myPatikaList;
    private JScrollPane scrl_std_patikaList;
    private JPanel pnl_std_patikaList_manage;
    private JTable tbl_std_patikaList;
    private JTable tbl_std_myPatikaList;
    private JScrollPane scrl_std_myPatikaList;
    private JPanel pnl_std_myPatikaList_manage;
    private JButton btn_std_register;
    private JButton derslerButton;
    private JTextField fld_std_patika_id;
    private JButton btn_std_delete;
    private JTextField fld_std_myPatika_id;
    private JLabel lbl_welcome;
    private JTable tbl_std_course_list;
    private JButton btn_std_contents;
    private JTextField fld_course_id;
    private JPanel pnl_std_course_manage;
    private JScrollPane std_course_list;
    private JPanel pnl_std_courses;
    private JComboBox cmb_filter_byPatika;
    private JButton btn_filter;

    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;

    private DefaultTableModel mdl_myPatika_list;
    private Object[] row_myPatika_list;

    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    private ArrayList<Integer> patikaIDs = new ArrayList<>();
    private final Student student;

    public StudentGUI(Student student) {
        this.student = student;

        Helper.setLayout();
        add(wrapper);
        setSize(1000, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin! " + this.student.getName());
        // PatikaList
        mdl_patika_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;

            }
        };
        fld_std_patika_id.setEnabled(false);
        fld_std_myPatika_id.setEnabled(false);
        fld_course_id.setEnabled(false);

        Object[] col_patika_list = {"ID", "Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        tbl_std_patikaList.setModel(mdl_patika_list);
        JTableHeader tableHeader = tbl_std_patikaList.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.getColumnModel().getColumn(0).setMaxWidth(50);

        loadPatikaModel();

        tbl_std_patikaList.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_content_id = tbl_std_patikaList.getValueAt(tbl_std_patikaList.getSelectedRow(), 0).toString();
                fld_std_patika_id.setText(selected_content_id);
            } catch (Exception exception) {

            }
        });

        // MyPatikaList
        mdl_myPatika_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;

            }
        };

        Object[] col_myPatika_list = {"ID", "Patika Adı"};
        mdl_myPatika_list.setColumnIdentifiers(col_myPatika_list);
        row_myPatika_list = new Object[col_myPatika_list.length];
        tbl_std_myPatikaList.setModel(mdl_myPatika_list);
        JTableHeader tableHeader1 = tbl_std_myPatikaList.getTableHeader();
        tableHeader1.setReorderingAllowed(false);
        tableHeader1.getColumnModel().getColumn(0).setMaxWidth(50);

        loadMyPatikaModel();


        tbl_std_myPatikaList.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_myPatika_id = tbl_std_myPatikaList.getValueAt(tbl_std_myPatikaList.getSelectedRow(), 0).toString();
                fld_std_myPatika_id.setText(selected_myPatika_id);
            } catch (Exception exception) {
            }
        });

        btn_std_register.addActionListener(e ->

        {
            if (Helper.isFieldEmpty(fld_std_patika_id)) {
                Helper.showMessage("fill");
            } else {
                if (Helper.confirm("Bu Patikaya Kayıt Olunsun mu?")) {
                    if (UserPatikaRelation.add(this.student.getId(), Integer.parseInt(fld_std_patika_id.getText()))) {
                        Helper.showMessage("done");
                        loadMyPatikaModel();
                        loadCourseModel();
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });
        btn_std_delete.addActionListener(e ->

        {
            if (Helper.isFieldEmpty(fld_std_myPatika_id)) {
                Helper.showMessage("fill");
            } else {
                if (Helper.confirm("Patika Takipten Çıkılsın mı?")) {
                    if (UserPatikaRelation.delete(Integer.parseInt(fld_std_myPatika_id.getText()), this.student.getId())) {
                        Helper.showMessage("done");
                        loadMyPatikaModel();
                        loadCourseModel();
                    } else {
                        Helper.showMessage("error");
                    }

                }
            }
        });


        // course table

        mdl_course_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;

            }
        };

        Object[] col_std_course_list = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_std_course_list);
        row_course_list = new Object[col_std_course_list.length];
        tbl_std_course_list.setModel(mdl_course_list);
        JTableHeader tableHeader2 = tbl_std_course_list.getTableHeader();
        tableHeader2.setReorderingAllowed(false);
        tableHeader2.getColumnModel().getColumn(0).setMaxWidth(50);

        loadCourseModel();


        tbl_std_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_course = tbl_std_course_list.getValueAt(tbl_std_course_list.getSelectedRow(), 0).toString();
                fld_course_id.setText(selected_course);


            } catch (Exception exception) {
            }
        });
        btn_filter.addActionListener(e -> {
            Item patika = (Item) cmb_filter_byPatika.getSelectedItem();
            if(patika.getKey() == 0){
                loadCourseModel();
            }else{
                loadCourseModel(patika.getKey());
            }
        });
        btn_std_contents.addActionListener(e -> {
            StudentContentGUI studentContentGUI = new StudentContentGUI(Course.getFetchById(Integer.parseInt(fld_course_id.getText())),this.student);

        });
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_std_course_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Integer patikaID : patikaIDs) {
            for (Course obj : Course.getListByPatika(patikaID)) {
                i = 0;
                row_course_list[i++] = obj.getId();
                row_course_list[i++] = obj.getName();
                row_course_list[i++] = obj.getLanguage();
                row_course_list[i++] = obj.getPatika().getName();
                row_course_list[i++] = obj.getEducator().getName();
                mdl_course_list.addRow(row_course_list);
            }
        }

    }

    private void loadCourseModel(int patika_id) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_std_course_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Course obj : Course.getListByPatika(patika_id)) {
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLanguage();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);

        }

    }


    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_std_patikaList.getModel();
        clearModel.setRowCount(0);

        int i = 0;
        for (Patika obj : Patika.getList()) {
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);

        }
    }

    private void loadMyPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_std_myPatikaList.getModel();
        clearModel.setRowCount(0);
        loadPatikaCombo();
        patikaIDs.clear();
        int i = 0;
        for (Patika obj : UserPatikaRelation.getStudentPatikaList(this.student.getId())) {
            i = 0;
            patikaIDs.add(obj.getId());
            row_myPatika_list[i++] = obj.getId();
            row_myPatika_list[i++] = obj.getName();
            mdl_myPatika_list.addRow(row_myPatika_list);

        }
    }

    private void loadPatikaCombo() {

        cmb_filter_byPatika.removeAllItems();
        cmb_filter_byPatika.addItem(new Item(0, "Hepsi"));
        for (Patika obj : UserPatikaRelation.getStudentPatikaList(this.student.getId())) {
            cmb_filter_byPatika.addItem(new Item(obj.getId(), obj.getName()));

        }
    }
}
