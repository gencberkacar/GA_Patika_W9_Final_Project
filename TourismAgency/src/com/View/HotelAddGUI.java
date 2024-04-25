package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.Hotel;
import com.Model.User;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

public class HotelAddGUI extends JFrame {
    private JPanel container;
    private JLabel lbl_welcome;
    private JButton btn_back;
    private JTextField fld_hotel_city;
    private JTextField fld_hotel_name;
    private JTextField fld_hotel_region;
    private JTextField fld_hotel_tel;
    private JTextField fld_hotel_address;
    private JTextField fld_hotel_mail;
    private JLabel lbl_hotel_name;
    private JLabel lbl_hotel_city;
    private JLabel lbl_hotel_region;
    private JLabel lbl_hotel_tel;
    private JLabel lbl_hotel_address;
    private JLabel lbl_hotel_hostel;
    private JComboBox cmb_hotel_hostel;
    private JCheckBox check_park;
    private JCheckBox cmb_wifi;
    private JCheckBox cmb_pool;
    private JCheckBox cmb_ftnss_center;
    private JCheckBox cmb_hotel_con;
    private JCheckBox cmb_hotel_spa;
    private JCheckBox cmb_service;
    private JLabel lbl_hotel_mail;
    private JButton btn_hotel_add;
    private JLabel lbl_hotel_star;
    private JComboBox cmb_hotel_star;
    private JLabel lbl_hotel_feature;
    private JTabbedPane tbl_hotel_add;
    private JCheckBox check_hotel_ultra;
    private JCheckBox check_hotel_all;
    private JCheckBox check_hotel_fullBoard;
    private JCheckBox check_hotel_breakfast;
    private JCheckBox check_hotel_bed;
    private JCheckBox check_hotel_half;
    private JCheckBox check_hotel_alc;
    private JTextField fld_hotel_start;
    private JTextField fld_hotel_finish;


    private Hotel hotel;

    private final ArrayList<String> hotel_feature = new ArrayList<>();
    private final ArrayList<String> hotel_hostel = new ArrayList<>();

    public HotelAddGUI(User user) {
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        lbl_welcome.setText("Welcome " + user.getUser_name());
        loadComboStar();

        //initialization codes used to add features
        check_park.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_feature.add(check_park.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_feature.remove(check_park.getText());
            }
        });


        cmb_wifi.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_feature.add(cmb_wifi.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_feature.remove(cmb_wifi.getText());
            }
        });
        cmb_pool.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_feature.add(cmb_pool.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_feature.remove(cmb_wifi.getText());
            }
        });
        cmb_ftnss_center.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_feature.add(cmb_ftnss_center.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_feature.remove(cmb_ftnss_center.getText());
            }
        });
        cmb_hotel_con.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_feature.add(cmb_hotel_con.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_feature.remove(cmb_hotel_con.getText());
            }
        });
        cmb_hotel_spa.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_feature.add(cmb_hotel_spa.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_feature.remove(cmb_hotel_spa.getText());
            }
        });
        cmb_service.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_feature.add(cmb_service.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_feature.remove(cmb_service.getText());
            }
        });

        check_hotel_alc.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_hostel.add(check_hotel_alc.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_hostel.remove(check_hotel_alc.getText());
            }
        });
        check_hotel_all.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_hostel.add(check_hotel_all.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_hostel.remove(check_hotel_all.getText());
            }
        });
        check_hotel_bed.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_hostel.add(check_hotel_bed.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_hostel.remove(check_hotel_bed.getText());
            }
        });
        check_hotel_breakfast.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_hostel.add(check_hotel_breakfast.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_hostel.remove(check_hotel_breakfast.getText());
            }
        });
        check_hotel_ultra.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_hostel.add(check_hotel_ultra.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_hostel.remove(check_hotel_ultra.getText());
            }
        });
        check_hotel_fullBoard.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_hostel.add(check_hotel_fullBoard.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_hostel.remove(check_hotel_fullBoard.getText());
            }
        });
        check_hotel_half.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hotel_hostel.add(check_hotel_half.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                hotel_hostel.remove(check_hotel_half.getText());
            }
        });
        //end codes used to add features

        //start adding hotels
        btn_hotel_add.addActionListener(e -> {
            String hotel_name = fld_hotel_name.getText();
            String hotel_address = fld_hotel_address.getText();
            String hotel_city = fld_hotel_city.getText();
            String hotel_region = fld_hotel_region.getText();
            String hotel_mail = fld_hotel_mail.getText();
            String hotel_tel = fld_hotel_tel.getText();
            String hotel_star = cmb_hotel_star.getSelectedItem().toString();


            if (Helper.isFieldEmpty(fld_hotel_name) ||
                    Helper.isFieldEmpty(fld_hotel_city) ||
                    Helper.isFieldEmpty(fld_hotel_region) ||
                    Helper.isFieldEmpty(fld_hotel_tel) ||
                    Helper.isFieldEmpty(fld_hotel_mail) ||
                    Helper.isFieldEmpty(fld_hotel_address)) {
                Helper.showMsg("fill");
            } else {
                if (Hotel.addHotel(hotel_name, hotel_address, hotel_city, hotel_region, hotel_mail, hotel_tel, hotel_star, String.valueOf(hotel_hostel), String.valueOf(hotel_feature))) {
                    Helper.showMsg("Hotel Successfully Added!");
                } else {
                    Helper.showMsg("error");
                }
            }

        });
        //end adding hotels

        btn_back.addActionListener(e -> {
            HotelGUI hotelGUI = new HotelGUI(user);
            dispose();
        });
    }

    private void loadComboStar() {
        cmb_hotel_star.removeAllItems();
        for (String obj : Config.STAR_LIST) {
            cmb_hotel_star.addItem(obj);
        }
    }


}