package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.Hotel;
import com.Model.Room;
import com.Model.Season;
import com.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchRoomGUI extends JFrame {
    private JPanel container;
    private JButton btn_back;
    private JTable tbl_searchroom_list;
    private JLabel lbl_search_room;

    private final User user;
    private DefaultTableModel mdl_searchRoom_list;
    private Object[] row_searchRoom_list;

    private Hotel hotel;
    private Season season;

    public SearchRoomGUI(User user) {
        this.user = user;
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        lbl_search_room.setText("Welcome " + user.getUser_name());
        //table printing startup
        mdl_searchRoom_list = new DefaultTableModel();
        Object[] col_searchRoom_list = {"Hotel Name", "Room Type", "Room Bed", "Room Stock "};
        mdl_searchRoom_list.setColumnIdentifiers(col_searchRoom_list);
        row_searchRoom_list = new Object[col_searchRoom_list.length];
        loadSearchRoomModel();
        //end table printing


        tbl_searchroom_list.setModel(mdl_searchRoom_list);
        tbl_searchroom_list.getTableHeader().setReorderingAllowed(false);

        btn_back.addActionListener(e -> {
            ReservationGUI reservationGUI = new ReservationGUI(user);
            dispose();
        });
    }

    //room information retrieval method start
    private void loadSearchRoomModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_searchroom_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Room obj : Room.getListByRoom()) {
            i = 0;
            row_searchRoom_list[i++] = obj.getHotel().getHotel_name();
            row_searchRoom_list[i++] = obj.getRoom_type();
            row_searchRoom_list[i++] = obj.getRoom_bed();
            row_searchRoom_list[i++] = obj.getRoom_stock();
            mdl_searchRoom_list.addRow(row_searchRoom_list);
        }
    }
    //room information retrieval method end

    @Override
    public String toString() {
        return super.toString();
    }
}