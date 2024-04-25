package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.User;

import javax.swing.*;

public class LoginGUI extends JFrame {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_img;
    private JLabel lbl_welcome;
    private JPanel w_bot;
    private JTextField fld_login_uname;
    private JPasswordField fld_login_pass;
    private JButton btn_login;

    public LoginGUI() {
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        btn_login.addActionListener(e -> {
            login();
        });
    }

    public void login() {
        if (Helper.isFieldEmpty(fld_login_uname) || Helper.isFieldEmpty(fld_login_pass)) {
            Helper.showMsg("fill");
        } else {
            User user = User.getFetch(fld_login_uname.getText(), fld_login_pass.getText());
            if (user == null) {
                Helper.showMsg("User Not Found!");
            } else {
                switch (user.getUser_type()) {
                    case "admin":
                        AdminGUI AdminGUI = new AdminGUI(user);
                        break;


                    case "employee":
                        HotelGUI hotelGUI = new HotelGUI(user);
                        break;
                    case "user":
                        ReservationGUI reservationGUI = new ReservationGUI(user);
                        break;

                }
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        LoginGUI logGui = new LoginGUI();
    }
}