package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.*;
import java.util.ArrayList;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_content_list;
    private JScrollPane scrl_content_list;
    private JTable tbl_content_list;
    private JButton btn_content_add;
    private JButton btn_content_update;
    private JButton btn_content_delete;
    private JPanel pnl_content_manage;
    private JPanel pnl_content_filter;
    private JComboBox cmb_content_courseList;
    private JTextField fld_content_title;
    private JButton btn_content_search;
    private JTextField fld_content_id;
    private JButton btn_content_logout;
    private JButton içerikQuizleriButton;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;

    private final Educator educator;

    public EducatorGUI(Educator educator) {
        this.educator = educator;

        Helper.setLayout();

        add(wrapper);
        setSize(1000, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        loadCourseCombo();
        // PNL_CONTENT_LİST
        // TBL_CONTENT_LİST
        fld_content_id.setEnabled(false);

        mdl_content_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        Object[] col_course_list = {"ID", "Title", "Açıklama", "Link", "Ders", "Eğitmen"};
        mdl_content_list.setColumnIdentifiers(col_course_list);
        row_content_list = new Object[col_course_list.length];
        tbl_content_list.setModel(mdl_content_list);
        JTableHeader tableHeader = tbl_content_list.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_content_list.getSelectionModel().addListSelectionListener(e ->{
            try{
                String selected_content_id = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString();
                fld_content_id.setText(selected_content_id);
            }catch (Exception exception){

            }

        });


        loadContentModel();
        // ##TBL_CONTENT_LİST
        // ##PNL_CONTENT_LİST

        // PNL_CONTENT_FİLTER


        // ##PNL_CONTENT_FİLTER


        btn_content_add.addActionListener(e -> {
                AddContentGUI addContentGUI = new AddContentGUI(this.educator);
                addContentGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadContentModel();
                    }
                });

        });
        btn_content_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_content_id)){
                Helper.showMessage("fill");
            }else{

                if(Content.delete(Integer.parseInt(fld_content_id.getText()))){
                    if(Helper.confirm("Emin misin?")){
                        Helper.showMessage("done");
                        loadContentModel();
                        fld_content_id.setText(null);
                    }
                }

            }

        });
        btn_content_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_content_id)){
                Helper.showMessage("Bir İçerik Seçiniz!");
            }else{
                UpdateContentGUI updateContentGUI = new UpdateContentGUI(Content.getFetch(Integer.parseInt(fld_content_id.getText())),this.educator);
                updateContentGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadContentModel();
                    }
                });
            }

        });
        btn_content_search.addActionListener(e -> {
                String title = fld_content_title.getText();
                Item course = (Item) cmb_content_courseList.getSelectedItem();

                loadContentModel(Content.searchCourseList(this.educator.getId(),title,course.getKey()));
        });
        btn_content_logout.addActionListener(e -> {
            if(Helper.confirm("Çıkış Yapmak istiyor musunuz")){
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }

        });
        içerikQuizleriButton.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_content_id)){
                Helper.showMessage("İçerik Seçiniz!");
            }else{
                QuizEducatorGUI quizEducatorGUI = new QuizEducatorGUI(Integer.parseInt(fld_content_id.getText()));
                quizEducatorGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadContentModel();
                    }
                });
            }
        });
    }

    // METHODS
    public void loadContentModel() {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);

        int i = 0;
        for (Content obj : Content.getList(educator.getId())) {
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
    public void loadContentModel(ArrayList<Content> contentsP) {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);

        int i = 0;
        for (Content obj :contentsP) {
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

    public void loadCourseCombo() {
        cmb_content_courseList.removeAllItems();
        cmb_content_courseList.addItem(new Item(0,"Hepsi"));
        for (Course obj: Course.getListByUser(educator.getId())) {
            cmb_content_courseList.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
}
