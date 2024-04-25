package com.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static int screenSize(String axis, Dimension size) {
        return switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }
    //start notifying the user of a message
    public static void showMsg(String str) {
        String msg;
        String title;
        switch (str) {
            case "fill":
                msg = "Please fill all the blanks!";
                title = "Error!";
                break;
            case "success":
                msg = "Successfully added!";
                title = "Add";
                break;
            case "error":
                msg = "Error occurred!";
                title = "Error";
                break;
            case "null":
                msg = " username not found";
                title = "Not Found";
                break;
            default:
                msg = str;
                title = "Message";

        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
    //end notifying the user of a message

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldEmpty(JEditorPane pane) {
        return pane.getText().trim().isEmpty();
    }
}