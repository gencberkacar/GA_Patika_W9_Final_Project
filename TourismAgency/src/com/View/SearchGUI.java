package com.View;

import com.Helper.Config;
import com.Helper.DBConnect;
import com.Helper.Helper;
import com.Model.Hotel;
import com.Model.User;

import javax.swing.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;


public class SearchGUI extends JFrame {
    private JPanel container;
    private JLabel lbl_search_user;
    private JButton btn_back;
    private JLabel lbl_search_hotel;
    private JTextField fld_search_hotel;
    private JTextField fld_search_entry;
    private Hotel hotel;
    private JLabel lbl_search_entry;
    private JTextField fld_search_release;
    private JLabel lbl_search_release;
    private JSpinner spn_search_adults;
    private JLabel lbl_search_adults;
    private JLabel lbl_search_child;
    private JSpinner spn_search_child;
    private JButton btn_add;
    private User user;

    public SearchGUI(User user) {
        this.user = user;
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        lbl_search_user.setText("Welcome " + user.getUser_name());


        SpinnerModel spinnerModelAdult = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerModel spinnerModelChild = new SpinnerNumberModel(0, 0, 100, 1);

        spn_search_adults.setModel(spinnerModelAdult);
        spn_search_child.setModel(spinnerModelChild);

        fld_search_entry.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                formatTextFieldDate(fld_search_entry);
            }
        });

        fld_search_release.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                formatTextFieldDate(fld_search_release);
            }
        });

        btn_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_search_hotel) || Helper.isFieldEmpty(fld_search_entry) || Helper.isFieldEmpty(fld_search_release)) {
                Helper.showMsg("fill");
            } else {
                String location = fld_search_hotel.getText();
                String entryText = fld_search_entry.getText();
                String releaseText = fld_search_release.getText();

                //Date format adopted as "YYYY-MM-DD"
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                try {
                    LocalDate entryDate = LocalDate.parse(entryText, inputFormatter);
                    LocalDate releaseDate = LocalDate.parse(releaseText, inputFormatter);

                    java.sql.Date checkIn = java.sql.Date.valueOf(entryDate);
                    java.sql.Date checkOut = java.sql.Date.valueOf(releaseDate);

                    int numAdult = (int) spn_search_adults.getValue();
                    int numChild = (int) spn_search_child.getValue();
                    search(location, checkIn, checkOut, numAdult, numChild);
                } catch (DateTimeParseException ex) {
                    Helper.showMsg("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                }
            }
        });


        btn_back.addActionListener(e -> {
            ReservationGUI reservationGUI = new ReservationGUI(user);
            dispose();
        });

    }

    //text editing method starter
    private void formatTextFieldDate(JTextField textField) {
        String text = textField.getText();
        if (text.length() == 7 || text.length() == 4) {
            text += "-";
            textField.setText(text);
        }
    }
    //text editing method end

    //start room search method
    private void search(String location, Date checkIn, Date checkOut, int numAdult, int numChild) {
        ArrayList<Integer> hotels = searchHotel(location);
        for (int hotel_id : hotels) {
            if (!isRoomAvailable(hotel_id)) {
                Helper.showMsg("No suitable rooms available.");
            } else {
                ArrayList<Date> dates = getCheckInOutDates(hotel_id);
                if (isDateAvailable(dates, checkIn, checkOut)) {
                    Hotel hotel = Hotel.getFech(hotel_id);
                    dispose();
                    ReservationDoneGUI reservationDoneGUI = new ReservationDoneGUI(user, hotel, numAdult, numChild, (java.sql.Date) checkIn, (java.sql.Date) checkOut);
                }
            }
        }
    }
    //end room search method

    //start hotel search method
    private ArrayList<Integer> searchHotel(String location) {
        ArrayList<Integer> searchHotelList = new ArrayList<>();
        String query = "SELECT hotel_id FROM hotel WHERE hotel_name LIKE ? OR hotel_city LIKE ? OR hotel_region LIKE ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setString(1, "%" + location + "%");
            pr.setString(2, "%" + location + "%");
            pr.setString(3, "%" + location + "%");
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                searchHotelList.add(rs.getInt("hotel_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchHotelList;
    }
    //start hotel search method

    //start room stock available method
    private boolean isRoomAvailable(int hotel_id) {
        String query = "SELECT COUNT(*) AS room_stock FROM room WHERE hotel_id = ? AND room_stock > 0";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getInt("room_stock") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //end room stock available method

    //method to retrieve date information by hotels start
    private ArrayList<Date> getCheckInOutDates(int hotel_id) {
        ArrayList<Date> dates = new ArrayList<>();
        String query = "SELECT start_date, finish_date FROM season WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                dates.add(rs.getDate("start_date"));
                dates.add(rs.getDate("finish_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }
    //method to retrieve date information by hotels end

    private boolean isDateAvailable(ArrayList<Date> dates, Date checkIn, Date checkOut) {
        for (int i = 0; i < dates.size(); i += 2) {
            Date check_in = dates.get(i);
            Date check_out = dates.get(i + 1);
            if (!(checkOut.before(check_in) || checkIn.after(check_out))) {
                return true;
            }
        }
        return false;
    }
}