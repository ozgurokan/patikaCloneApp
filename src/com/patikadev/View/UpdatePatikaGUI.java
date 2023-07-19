package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatikaGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_patika_name;
    private JButton btn_update;
    private Patika patika;

    public UpdatePatikaGUI(Patika patika) {
        this.patika = patika;
        Helper.setLayout();
        add(wrapper);
        setSize(300, 150);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_patika_name.setText(patika.getName());

        // Patika update BUTTON LİSTENER - START
        btn_update.addActionListener(e -> {

            if (Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showMessage("fill");
            } else {
                if (Patika.update(this.patika.getId(), fld_patika_name.getText())) {
                    Helper.showMessage("done");
                }
                dispose();
            }
        });
        // Patika update BUTTON LİSTENER - END

    }
}
