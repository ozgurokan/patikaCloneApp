package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.User;
import com.patikadev.Model.Course;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane pnl_patika_list;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JPasswordField fld_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_sh_user;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_patika_tab;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JTextField fld_course_name;
    private JTextField fld_course_language;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_educator;
    private JButton btn_course_add;
    private JButton btn_course_update;
    private JButton btn_course_delete;
    private JTextField fld_course_id;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;


    private final Operator operator;
    public OperatorGUI(Operator operator) {
        this.operator = operator;
        Helper.setLayout();
        add(wrapper);
        setSize(1000, 500);

        int x = Helper.screenCenterPoint("x", getSize());
        int y = Helper.screenCenterPoint("y", getSize());

        setLocation(x, y);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin," + operator.getName());

        // ModelUserList
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };


        // USER TABLE - START
        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];


        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
        tbl_user_list.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_id.setText(select_user_id);
            } catch (Exception exception) {

            }

        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String user_pass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();


                if (User.update(user_id, user_name, user_uname, user_pass, user_type)) {
                    Helper.showMessage("done");
                    loadCourseModel();
                    loadEducatorCombo();
                }

                loadUserModel();
            }
        });
        // USER TABLE - END

        // PATİKA TABLE - START
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);


        updateMenu.addActionListener(e -> {
            int selected_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            UpdatePatikaGUI updatePatikaGUI =new UpdatePatikaGUI(Patika.getFetch(selected_id));
            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {

            if(Helper.confirm("sure")){
                int selected_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
                if(Patika.delete(selected_id)){
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }else{
                    Helper.showMessage("error");
                }

            }

        });

        mdl_patika_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;

            }
        };
        Object[] col_patika_list = {"ID", "Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();


        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(100);

        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });



        // PATİKA TABLE - END

        //COURSE TAB - START
        // COURSE LİST - START
        mdl_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Object[] col_course_list = {"ID","Ders Adı", "Programlama Dili","Patika","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        fld_course_id.setEnabled(false);

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadCourseModel();
        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_course_id = tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 0).toString();

                fld_course_id.setText(selected_course_id);
                fld_course_name.setText(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 1).toString());
                fld_course_language.setText(tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 2).toString());



            } catch (Exception exception) {

            }

        });
        // COURSE LİST - END

        //CMB_PATİKA_
        loadPatikaCombo();
        loadEducatorCombo();



        //COURSE TAB - END



        // Add BUTTON LİSTENER - START

        btn_user_add.addActionListener(e -> {

            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_pass)) {
                Helper.showMessage("fill");
            } else {
                String name = fld_user_name.getText();
                String uname = fld_user_uname.getText();
                String pass = fld_pass.getText();
                String type = cmb_user_type.getSelectedItem().toString();
                if (User.add(name, uname, pass, type)) {
                    Helper.showMessage("done");
                    loadUserModel();
                    loadEducatorCombo();
                    fld_user_uname.setText(null);
                    fld_pass.setText(null);
                    fld_user_name.setText(null);


                } else {
                    Helper.showMessage("error");
                }


            }

        });
        // Add BUTTON LİSTENER - END

        // Delete BUTTON LİSTENER- START
        btn_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_user_id)) {
                    Helper.showMessage("fill");
                } else {
                    if (Helper.confirm("sure")){
                        int user_id = Integer.parseInt(fld_user_id.getText());
                        if (User.delete(user_id)) {
                            Helper.showMessage("done");
                            loadUserModel();
                            loadEducatorCombo();
                            loadCourseModel();
                            fld_user_id.setText(null);
                        } else {
                            Helper.showMessage("error");
                        }
                    }
                    }
            }
        });
        // Delete BUTTON LİSTENER- END

        // Search BUTTON LİSTENER - START
        btn_sh_user.addActionListener(e -> {
            String name = fld_sh_user_name.getText();
            String uname = fld_sh_user_uname.getText();
            String type = cmb_sh_user_type.getSelectedItem().toString();

            String query = User.searchQuery(name,uname,type);
            ArrayList<User> searchedUser = User.searchUserList(query);
            loadUserModel(searchedUser);
        });

        // Search BUTTON LİSTENER - END

        // logout BUTTON LİSTENER - START
        btn_logout.addActionListener(e -> {
            if(Helper.confirm("Çıkış Yapmak istiyor musunuz?")){
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }

        });
        // logout BUTTON LİSTENER - END

        // addPatika LİSTENER - START
        btn_patika_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_patika_name)){
                Helper.showMessage("fill");
            }else{
                if(Patika.add(fld_patika_name.getText())){
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    fld_patika_name.setText(null);
                }else{
                    Helper.showMessage("error");
                }
            }
        });

        // addPatika LİSTENER - END
        // addCourse LİSTENER -START
        btn_course_add.addActionListener(e -> {
            Item patikaItem =(Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_educator.getSelectedItem();
            if(Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_language)){
                Helper.showMessage("fill");
            }else{
                if(Course.add(userItem.getKey(), patikaItem.getKey(), fld_course_name.getText(), fld_course_language.getText())){
                    Helper.showMessage("done");
                    loadCourseModel();
                    fld_course_name.setText(null);
                    fld_course_language.setText(null);
                }else{
                    Helper.showMessage("error");
                }

            }
        });
        // addCourse LİSTENER -END
        // updateCourse LİSTENER -START

        btn_course_update.addActionListener(e -> {
            int id = Integer.parseInt(fld_course_id.getText().toString());
            Item patikaItem =(Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_educator.getSelectedItem();
            if(Course.update(id,userItem.getKey(),patikaItem.getKey(),fld_course_name.getText(),fld_course_language.getText())){
                Helper.showMessage("done");
                loadCourseModel();
                fld_course_language.setText(null);
                fld_course_name.setText(null);

            }else{
                Helper.showMessage("error");
            }

        });

        // updateCourse LİSTENER -END

        // deleteCourse LİSTENER -START
        btn_course_delete.addActionListener(e -> {
            if(Course.delete(Integer.parseInt(fld_course_id.getText().toString()))){
                Helper.showMessage("done");
                loadCourseModel();
                fld_course_id.setText(null);
            }else{
                Helper.showMessage("error");
            }

        });

        // deleteCourse LİSTENER -END


    }



    // METHODS - START
    public void loadUserModel() {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (User obj : User.getList()) {
            i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }

    }
    public void loadUserModel(ArrayList<User> userList) {

        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (User obj : userList) {
            i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }

    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel =(DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);

        int i = 0;
        for (Patika obj : Patika.getList()) {
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);

        }
    }

    private void loadCourseModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(Course obj : Course.getList()){
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLanguage();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();

        for(Patika obj : Patika.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(), obj.getName()));
        }


    }

    public void loadEducatorCombo(){
        cmb_course_educator.removeAllItems();
        for( User obj : User.getListOnlyEducator()){
            cmb_course_educator.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

}
