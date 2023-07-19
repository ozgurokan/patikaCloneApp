package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuizEducatorGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_quiz_top;
    private JLabel lbl_quiz_info;
    private JPanel pnl_quiz_bottom;
    private JTable tbl_quiz_list;
    private JScrollPane scrl_quiz_list;
    private JPanel pnl_quiz_manage;
    private JButton btn_quiz_add;
    private JTextField fld_quiz_id;
    private JButton btn_quiz_update;
    private JButton btn_quiz_delete;


    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;

    private final Content content;

    public QuizEducatorGUI(int content_id) {
        this.content = Content.getFetch(content_id);

        Helper.setLayout();
        add(wrapper);
        setSize(900, 450);

        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());

        setLocation(x, y);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_quiz_info.setText(this.content.getTitle().toUpperCase() + " QUIZ PANEL");


        // QUİZ TABLE
        mdl_quiz_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] col_quiz_list = {"ID", "Soru", "Doğru Cevap", "İkinci Şık", "Üçüncü Şık", "Dördüncü Şık"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];
        tbl_quiz_list.setModel(mdl_quiz_list);
        JTableHeader tableHeader = tbl_quiz_list.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        tableHeader.getColumnModel().getColumn(0).setMaxWidth(50);

        fld_quiz_id.setEnabled(false);
        tbl_quiz_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_content_id = tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(), 0).toString();
                fld_quiz_id.setText(selected_content_id);
            } catch (Exception exception) {

            }

        });
        loadQuizModel();
        btn_quiz_add.addActionListener(e -> {
            addQuizGUI addQuizGUI = new addQuizGUI(this.content.getId());
            addQuizGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadQuizModel();
                }
            });
        });
        btn_quiz_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_quiz_id)) {
                Helper.showMessage("Lütfen bir quiz seçin!");
            } else {
                if (Helper.confirm("Silmek istiyor musun?")) {
                    if (Quiz.delete(Integer.parseInt(fld_quiz_id.getText()))) {
                        Helper.showMessage("done");
                        loadQuizModel();
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });
        btn_quiz_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_quiz_id)){
                Helper.showMessage("fill");
            }else{
                UpdateQuizGUI updateQuizGUI = new UpdateQuizGUI(Integer.parseInt(fld_quiz_id.getText()));
                updateQuizGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        loadQuizModel();
                    }
                });
            }
        });
    }

    public void loadQuizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
        clearModel.setRowCount(0);

        int i = 0;
        for (Quiz obj : Quiz.getList(content.getId())) {
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
