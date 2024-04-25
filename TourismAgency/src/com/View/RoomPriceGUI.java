package com.View;

import com.Helper.Config;
import com.Helper.DBConnect;
import com.Helper.Helper;
import com.Model.Hotel;
import com.Model.Room;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

public class RoomPriceGUI extends JFrame {
    private JPanel container;
    private JLabel lbl_room_price;
    private JButton btn_back;
    private JLabel lbl_price_ultra;
    private JTextField fld_price_ultra;
    private JLabel lbl_price_breakfast;
    private JTextField fld_price_breakfast_adult;
    private JTextField fld_price_half;
    private JLabel lbl_price_full;
    private JTextField fld_price_full;
    private JLabel lbl_price_credit;
    private JTextField fld_price_fullcredit;
    private JLabel lbl_price_all;
    private JTextField fld_price_all;
    private JLabel lbl_price_bed;
    private JTextField fld_price_bed;
    private JButton btn_price_add;
    private JLabel lbl_price_half;
    private JTextField fld_price_ultra_child;
    private JTextField fld_price_breakfast;
    private JTextField fld_price_half_child;
    private JTextField fld_price_full_child;
    private JTextField fld_price_fullcredit_child;
    private JTextField fld_price_all_child;
    private JTextField fld_price_bed_child;
    private Hotel hotel;


    public RoomPriceGUI(String hotel_name) {
        this.hotel = Hotel.getHotelByName(hotel_name);
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(500, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        lbl_room_price.setText("Hostel pricing screen");
        loadLblText();
        btn_back.addActionListener(e -> {
            dispose();
        });

        //start adding room pricing
        btn_price_add.addActionListener(e -> {
            int i = 0;
            String hostel_type;
            int price;
            int roomPrice_id = Room.getHotelIDbyRoomPriceID(HotelGUI.hotel_id);
            int hotel_id = HotelGUI.hotel_id;
            int hostel_price[] = new int[Config.HOSTEL_LIST_ADULT.length];
            hostel_price[i++] = Integer.parseInt(fld_price_full.getText());
            hostel_price[i++] = Integer.parseInt(fld_price_half.getText());
            hostel_price[i++] = Integer.parseInt(fld_price_fullcredit.getText());
            hostel_price[i++] = Integer.parseInt(fld_price_breakfast.getText());
            hostel_price[i++] = Integer.parseInt(fld_price_all.getText());
            hostel_price[i++] = Integer.parseInt(fld_price_ultra.getText());
            hostel_price[i++] = Integer.parseInt(fld_price_bed.getText());

            if (RoomPrice(roomPrice_id, hotel_id, hostel_price)) {
                Helper.showMsg("success");
                dispose();
            } else {
                Helper.showMsg("error");
            }
        });
        //end adding room pricing

    }

    //method to print hotel prices to table start
    private void loadLblText() {
        int i = 0;
        lbl_price_ultra.setText(Config.HOSTEL_LIST_ADULT[i++] + ":");
        lbl_price_full.setText(Config.HOSTEL_LIST_ADULT[i++] + ":");
        lbl_price_breakfast.setText(Config.HOSTEL_LIST_ADULT[i++] + ":");
        lbl_price_bed.setText(Config.HOSTEL_LIST_ADULT[i++] + ":");
        lbl_price_credit.setText(Config.HOSTEL_LIST_ADULT[i++] + ":");
        lbl_price_half.setText(Config.HOSTEL_LIST_ADULT[i++] + ":");
        lbl_price_all.setText(Config.HOSTEL_LIST_ADULT[i++] + ":");
    }
    //method to print hotel prices to table end

    //start printing room rates to database
    private boolean RoomPrice(int roomPrice_id, int hotel_id, int[] hostel_price) {
        String query = "INSERT INTO roomPrice (roomPrice_id,hostel_type, hotel_id, hostel_price) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            for (int i = 0; i < Config.HOSTEL_LIST_ADULT.length; i++) {
                pr.setInt(1, roomPrice_id);
                pr.setObject(2, Config.HOSTEL_LIST_ADULT[i]);
                pr.setInt(3, hotel_id);
                pr.setInt(4, hostel_price[i]);
                pr.addBatch();
            }

            pr.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //end printing room rates to database


}