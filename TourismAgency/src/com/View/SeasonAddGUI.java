package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.Hotel;
import com.Model.Season;
import com.Model.User;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SeasonAddGUI extends JFrame {
    private int hotel_id;
    private String hotel_name;
    private Season season_name;
    private JButton btn_back;
    private JLabel lbl_hotel_season;
    private JCheckBox check_hotel_winter;
    private JCheckBox check_hotel_summer;
    private JTextField fld_hotel_start;
    private JTextField fld_hotel_finish;
    private JPanel container;
    private JLabel lbl_season_name;
    private JButton btn_add;

    private User user;

    public SeasonAddGUI(String hotel_name ) {

        this.hotel_name = hotel_name;
        this.user = user;
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);


        btn_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_start) || Helper.isFieldEmpty(fld_hotel_finish)) {
                Helper.showMsg("fill");
            } else {
                String start_season = fld_hotel_start.getText();
                String finish_season = fld_hotel_finish.getText();

                // Date format adopted as "YYYY-MM-DD"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                try {
                    LocalDate startDate = LocalDate.parse(start_season, formatter);
                    LocalDate finishDate = LocalDate.parse(finish_season, formatter);
                    start_season = startDate.toString();
                    finish_season = finishDate.toString();

                    String season_name;
                    if (!check_hotel_summer.isSelected() && !check_hotel_winter.isSelected()) {
                        Helper.showMsg("select which season the dates you have selected belong to");
                    } else {
                        if (check_hotel_summer.isSelected()) {
                            season_name = check_hotel_summer.getText();
                        } else {
                            season_name = check_hotel_winter.getText();
                        }
                        if (Season.add(HotelGUI.hotel_id, season_name, start_season, finish_season)) {
                            Helper.showMsg("done");
                            fld_hotel_finish.setText(null);
                            fld_hotel_start.setText(null);
                        }
                    }
                    dispose();
                } catch (DateTimeParseException ex) {
                    Helper.showMsg("Invalid date format. Please enter date in YYYY-MM-DD format.");
                }
            }
        });



    }


    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public Season getSeason_name() {
        return season_name;
    }

    public void setSeason_name(Season season_name) {
        this.season_name = season_name;
    }
}