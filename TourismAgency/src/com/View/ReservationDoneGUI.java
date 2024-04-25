package com.View;

import com.Helper.Config;
import com.Helper.DBConnect;
import com.Helper.Helper;
import com.Model.Hotel;
import com.Model.User;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReservationDoneGUI extends JFrame {
    private JPanel container;
    private JButton btn_back;
    private JLabel lbl_hotel_infor;
    private JLabel lbl_hotel_city;
    private JTextField fld_search_city;
    private JLabel lbl_hotel_region;
    private JTextField fld_search_region;
    private JLabel lbl_hotel_address;
    private JTextField fld_search_address;
    private JLabel lbl_hotel_tel;
    private JTextField fld_search_tel;
    private JLabel lbl_hotel_feature;
    private JLabel lbl_hotel_features;
    private JTextField fld_search_checkin;
    private JLabel lbl_search_checkin;
    private JLabel lbl_search_entry;
    private JLabel lbl_search_release;
    private JTextField fld_search_checkout;
    private JLabel lbl_search_adult;
    private JLabel lbl_search_child;
    private JLabel lbl_search_room;
    private JLabel lbl_room_bed;
    private JLabel lbl_room_tv;
    private JLabel lbl_room_minibar;
    private JLabel lbl_room_safebox;
    private JLabel lbl_reservation;
    private JLabel lbl_search_ultra;
    private JLabel lbl_search_all;
    private JLabel lbl_search_bed;
    private JLabel lbl_search_fullcredit;
    private JLabel lbl_search_half;
    private JLabel lbl_search_full;
    private JLabel lbl_search_breakfast;
    private JComboBox cmb_hostel_tpye;
    private JButton btn_add;
    private JLabel lbl_search_hotel_name;
    private JLabel lbl_search_star;
    private JLabel lbl_room_type;

    private ArrayList<String> hostel_list = new ArrayList<>();
    private ArrayList<String> hostel_price_list = new ArrayList<>();
    private int room_id;
    private int room_bed;
    private final ArrayList<JLabel> labelList = new ArrayList<>(Arrays.asList(lbl_search_ultra, lbl_search_bed, lbl_search_all, lbl_search_half, lbl_search_full, lbl_search_fullcredit, lbl_search_breakfast));

    public ReservationDoneGUI(User user, Hotel hotel, int numAdult, int numChild, Date checkIn, Date checkOut) {
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(1500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);

        lbl_search_room.setText("Welcome " + user.getUser_name());
        fld_search_address.setEditable(false);
        fld_search_checkin.setEditable(false);
        fld_search_checkout.setEditable(false);
        fld_search_city.setEditable(false);
        fld_search_tel.setEditable(false);
        fld_search_region.setEditable(false);

        int hotel_id = hotel.getHotel_id();
        int reservation_date = calculateRezDate(checkIn, checkOut);
        String room_type = getRoomType(hotel.getHotel_id());

        //start to fill labels
        lbl_search_hotel_name.setText(hotel.getHotel_name());
        lbl_search_star.setText(hotel.getHotel_star());
        fld_search_address.setText(hotel.getHotel_address());
        fld_search_city.setText(hotel.getHotel_city());
        fld_search_region.setText(hotel.getHotel_region());
        fld_search_tel.setText(hotel.getHotel_tel());
        fld_search_checkin.setText(String.valueOf(checkIn));
        fld_search_checkout.setText(String.valueOf(checkOut));
        lbl_room_type.setText(room_type + " Room");
        lbl_hotel_features.setText(getHostelFeature(hotel_id));
        lbl_search_adult.setText("Number Of Adult : " + numAdult);
        lbl_search_child.setText("Number Of Child: " + numChild);

        lbl_reservation.setText(String.valueOf(reservation_date) + " For The Night");
        loadHostelList(hotel_id, reservation_date);
        loadHostelCombo();
        roomInfo(room_id);
        //end to fill labels

        btn_add.addActionListener(e -> {
            System.out.println(calculatePayment(reservation_date));
            ResMakeGUI ResMakeGUI = new ResMakeGUI(user, hotel_id, room_id, numAdult, numChild, checkIn, checkOut);
            dispose();
        });

        btn_back.addActionListener(e -> {
            ReservationGUI reservationGUI = new ReservationGUI(user);
            dispose();
        });
    }

    //hotel price calculation start
    private int calculateRezDate(Date checkIn, Date checkOut) {
        String chekinDateStr = String.valueOf(checkIn);
        String chekouDateStr = String.valueOf(checkOut);
        String[] partsOfCheckin = chekinDateStr.split("-");
        String[] partsOfCheckOut = chekouDateStr.split("-");
        int yearToDay = (Integer.parseInt(partsOfCheckOut[0]) - Integer.parseInt(partsOfCheckin[0])) * 365;
        System.out.println(yearToDay);
        int monthToDay = (Integer.parseInt(partsOfCheckOut[1]) - Integer.parseInt(partsOfCheckin[1])) * 30;
        System.out.println(monthToDay);
        int day = (Integer.parseInt(partsOfCheckOut[2]) - Integer.parseInt(partsOfCheckin[2]));
        System.out.println(day);
        return yearToDay + monthToDay + day;

    }
    //hotel price calculation end

    //hotel price pay start
    private int calculatePayment(int reservation_date) {
        String selectedHostel = cmb_hostel_tpye.getSelectedItem().toString();
        int hostelIndex = hostel_list.indexOf(selectedHostel);
        int selectedHostelPrice = Integer.parseInt(hostel_price_list.get(hostelIndex));
        return selectedHostelPrice * reservation_date;
    }
    //hotel price pay end

    //start showing room types
    private String getRoomType(int hotel_id) {

        String query = "SELECT room_id,room_type,room_bed FROM room WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {

                room_id = rs.getInt("room_id");
                return rs.getString("room_type");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    //end showing room types

    //Start showing hotel properties
    private String getHostelFeature(int hotel_id) {

        String query = "SELECT hotel_feature FROM hotel WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {

                return rs.getString("hotel_feature");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    //End showing hotel properties

    //start showing hostel type and hostel prices
    private void hostelList(int hotel_id) {
        String query = "SELECT hostel_price, hostel_type FROM roomPrice WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                hostel_list.add(rs.getString("hostel_type"));
                hostel_price_list.add(String.valueOf(rs.getInt("hostel_price")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //end showing hostel type and hostel prices

    private void loadHostelList(int hotel_id, int reservation_date) {
        hostelList(hotel_id);
        for (int i = 0; i < labelList.size(); i++) {
            labelList.get(i).setText(hostel_list.get(i) + " : " + Integer.parseInt(hostel_price_list.get(i)) * reservation_date + " TL");
        }


    }

    private void roomInfo(int room_id) {
        int room_bed = 0;
        boolean room_tv = true, room_minibar = true, room_safebox = true;
        String query = "SELECT room_bed,room_tv,room_minibar,room_safebox FROM room WHERE room_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, room_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                room_bed = rs.getInt("room_bed");
                room_tv = rs.getBoolean("room_tv");
                room_minibar = rs.getBoolean("room_minibar");
                room_safebox = rs.getBoolean("room_safebox");
            }
            loadRoomInfo(room_bed, room_tv, room_minibar, room_safebox);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Room features start
    private void loadRoomInfo(int room_bed, boolean room_tv, boolean room_minibar, boolean room_safebox) {
        if (true) {
            lbl_room_bed.setText("Yatak : " + room_bed + " Adet");
        }
        if (room_tv) {
            lbl_room_tv.setText("Televizyon : Var");
        } else {
            lbl_room_tv.setText("Televizyon : Yok");
        }
        if (room_minibar) {
            lbl_room_minibar.setText("Minibar : Var");
        } else {
            lbl_room_minibar.setText("Minibar : Yok");
        }
        if (room_safebox) {
            lbl_room_safebox.setText("Kasa : Var");
        } else {
            lbl_room_safebox.setText("Kasa : Yok");
        }
    }
    //Room features end

    private void loadHostelCombo() {
        cmb_hostel_tpye.removeAllItems();
        for (String s : hostel_list) {
            cmb_hostel_tpye.addItem(s);

        }
    }

}