package com.View;

import com.Helper.Config;
import com.Helper.DBConnect;
import com.Helper.Helper;
import com.Model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResMakeGUI extends JFrame {
    private JPanel container;
    private JPanel c_top;
    private JLabel lbl_res_connect;
    private JLabel lbl_res_request;
    private JTextField fld_res_name;
    private JTextField fld_res_note;
    private JLabel lbl_res_name;
    private JLabel lbl_res_note;
    private JLabel lbl_res_tel;
    private JLabel lbl_res_mail;
    private JTextField fld_res_mail;
    private JTextField fld_res_tel;
    private JButton btn_add;
    private JLabel lbl_rest_cust;
    private JTextField fld_name;
    private JTextField fld_nat;
    private JTextField fld_id;

    private ArrayList<JLabel> guestNumLabelList = new ArrayList<>();
    private ArrayList<JTextField> nameTextFieldList = new ArrayList<>();
    private ArrayList<JTextField> nationTextFieldList = new ArrayList<>();
    private ArrayList<JTextField> TCTextFieldList = new ArrayList<>();
    private ArrayList<JPanel> panelList = new ArrayList<>();
    private User user;
    private int numGuest;

    public ResMakeGUI(User user, int hotel_id, int room_id, int numAdult, int numChild, Date checkIn, Date checkOut) {
        numGuest = numAdult + numChild;
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(1100, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        GridLayout myLayout = new GridLayout(5, 2);
        setLayout(new FlowLayout());

        fld_res_name.setPreferredSize(new Dimension(250, 25));
        fld_res_mail.setPreferredSize(new Dimension(250, 25));
        fld_res_note.setPreferredSize(new Dimension(250, 25));
        fld_res_tel.setPreferredSize(new Dimension(250, 25));

        // Start creating entrance areas for each guest
        for (int i = 0; i < numGuest; i++) {
            guestNumLabelList.add(new JLabel());
            nameTextFieldList.add(new JTextField("Ad Soyad"));
            nationTextFieldList.add(new JTextField("Ülke"));
            TCTextFieldList.add(new JTextField("Kimlik/Pasaport No"));
            panelList.add(new JPanel());

        }
        // end creating entrance areas for each guest

        // Add created input fields to the screen start
        for (int i = 0; i < numGuest; i++) {
            lbl_rest_cust = guestNumLabelList.get(i);
            fld_name = nameTextFieldList.get(i);
            fld_nat = nationTextFieldList.get(i);
            fld_id = TCTextFieldList.get(i);
            JPanel w_container = panelList.get(i);
            lbl_rest_cust.setText(i + 1 + ". Misafir");
            fld_name.setPreferredSize(new Dimension(250, 25));
            fld_nat.setPreferredSize(new Dimension(250, 25));
            fld_id.setPreferredSize(new Dimension(250, 25));
            w_container.add(lbl_rest_cust);
            w_container.add(fld_name);
            w_container.add(fld_nat);
            w_container.add(fld_nat);
            w_container.add(fld_id);
            this.add(w_container);
        }
        setVisible(true);
        // Add created input fields to the screen

        btn_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_res_mail) ||
                    Helper.isFieldEmpty(fld_name) ||
                    Helper.isFieldEmpty(fld_res_note) ||
                    Helper.isFieldEmpty(fld_res_tel)) {
                Helper.showMsg("fill");
            } else {
                Boolean status = false;
                String contact_name = fld_res_name.getText();
                String contact_res_note = fld_res_note.getText();
                String contact_tel = fld_res_tel.getText();
                String contact_mail = fld_res_mail.getText();

                // Get login details for each guest start
                for (int i = 0; i < numGuest; i++) {
                    String guest_name = nameTextFieldList.get(i).getText();
                    String guest_nation = nationTextFieldList.get(i).getText();
                    String guest_tc = TCTextFieldList.get(i).getText();
                    status = addRes(contact_name, contact_res_note, contact_tel, contact_mail, guest_name, guest_nation, guest_tc, hotel_id, room_id, checkIn, checkOut);
                }
                // Get login details for each guest end

                //start screen with completion warning
                if (Boolean.TRUE.equals(status)) {
                    updateStock(room_id);
                    Helper.showMsg("Reservation Completed!");
                    dispose();
                    ReservationGUI reservationGUI = new ReservationGUI(user);
                } else {
                    Helper.showMsg("Bir hata oluştu.");
                }
                //end screen with completion warning

            }

        });
    }

    //start reservation addition method
    private Boolean addRes(String reser_custom_name, String reser_contact_note, String reser_contact_tel, String reser_contact_mail, String reser_guest_name, String reser_guest_nat, String reser_guest_tc, int hotel_id, int room_id, Date reser_checkIn, Date reser_checkOut) {
        String query = "INSERT INTO reservation (reser_custom_name,reser_contact_note,reser_contact_tel,reser_contact_mail,reser_guest_name,reser_guest_nat,reser_guest_tc,hotel_id,reser_checkIn,reser_checkOut) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setString(1, reser_custom_name);
            pr.setString(2, reser_contact_note);
            pr.setString(3, reser_contact_tel);
            pr.setString(4, reser_contact_mail);
            pr.setString(5, reser_guest_name);
            pr.setString(6, reser_guest_nat);
            pr.setString(7, reser_guest_tc);
            pr.setInt(8, hotel_id);
            pr.setDate(9, reser_checkIn);
            pr.setDate(10, reser_checkOut);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //end reservation addition method

    //beginning stock diminution method
    public boolean updateStock(int room_id) {
        int room_stock = getStockSizeOfRoom(room_id);
        if (room_stock == 0) {
            Helper.showMsg("Yeterli Oda sayısı Bulunmamaktadır.");
            return false;
        } else {

            String query = "UPDATE room SET room_stock = room_stock - 1  WHERE room_id = ? AND room_stock > 0";
            try {
                PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);

                pr.setInt(1, room_id);
                return pr.executeUpdate() != -1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //stock diminution method end

    //start showing stock information for rooms
    public int getStockSizeOfRoom(int room_id) {
        int room_stock = 0;
        String query = "SELECT room_stock FROM room WHERE room_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, room_id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                room_stock = rs.getInt("room_stock");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return room_stock;
    }
    //end showing stock information for rooms
}