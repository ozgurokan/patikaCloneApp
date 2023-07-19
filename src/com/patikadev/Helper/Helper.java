package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static int screenCenterPoint(String axis, Dimension size) {
        int point;
        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2 ;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
        }
        return point;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }
    public static boolean isFieldEmpty(JTextPane field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String str) {
        optionPageTR();
        String msg;
        String title;
        switch (str) {
            case "fill":
                msg = "Lütfen Tüm Alanları Doldurun!";
                title = "HATA";
                break;
            case "done":
                msg = "İşlem Başarılı!";
                title = "Başarılı!";
                break;
            case "error":
                msg = "Bir Hata Oluştu!";
                title = "HATA!";
                break;
            default:
                msg = str;
                title = "Mesaj";


        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {

        String msg;
        switch (str) {
            case "sure":
                msg = "Emin misinz?";
                break;
            default:
                msg = str;
        }

        return JOptionPane.showConfirmDialog(null, msg, "Doğrulama", JOptionPane.YES_NO_OPTION) == 0;

    }

    public static void optionPageTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
    }
}
