package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.Hotel;
import com.Model.Room;
import com.Model.Season;
import com.Model.User;

import javax.swing.*;
import java.awt.*;


public class RoomAddGUI extends JFrame {
    Label lbl_hotel_name;
    private int room_id;
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_room_hotel;
    private JButton btn_back;
    private JLabel lbl_room_name;
    private JComboBox cmb_room_type;
    private JComboBox cmb_room_bed;
    private JTextField fld_room_stock;
    private JLabel lbl_room_stock;
    private JLabel lbl_room_bed;
    private JLabel lbl_room_feature;
    private JCheckBox check_television;
    private JCheckBox check_safebox;
    private JCheckBox check_minibar;
    private JButton btn_room_add;

    private int hotel_id;

    private String hotel_name;
    private String[] room_type = {"Suit", "Double", "Single"};


    public RoomAddGUI(String hotel_name) {
        this.hotel_name = hotel_name;
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        lbl_room_hotel.setText("Add room screen for " + hotel_name);
        loadRoomTypeCombo();

        btn_back.addActionListener(e -> {
            dispose();
        });

        //room addition function startup
        btn_room_add.addActionListener(e -> {
            int roomID = Room.getHotelIDbyRoomID(room_id);
            String room_type = cmb_room_type.getSelectedItem().toString();
            int hotelID = HotelGUI.hotel_id;
            String hotel_Name = Hotel.getHotelNameByHotelID(hotelID);
            int room_stock = Integer.parseInt(fld_room_stock.getText());
            int room_bed = Integer.parseInt(cmb_room_bed.getSelectedItem().toString());
            boolean room_tv = check_television.isSelected();
            boolean room_minibar = check_minibar.isSelected();
            boolean room_safe = check_safebox.isSelected();

            if (Room.addRoom(roomID, hotelID, hotel_Name, room_type, room_bed, room_stock, room_tv, room_minibar, room_safe)) {
                Helper.showMsg("success");
            } else {
                Helper.showMsg("error");
            }
        });
        //room addition function finish

    }

    //start listing room types
    public void loadRoomTypeCombo() {
        cmb_room_type.removeAllItems();
        for (String obj : room_type) {
            cmb_room_type.addItem(obj);
        }
    }
    //end listing room types

    @Override
    public String toString() {
        return super.toString();
    }
}