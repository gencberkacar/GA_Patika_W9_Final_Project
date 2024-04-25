package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.Hotel;

import com.Model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class HotelGUI extends JFrame {
    private JPanel container;
    private JLabel lbl_welcome;
    private JButton btn_back;
    private JTable tbl_hotel_list;
    private JPanel pnl_left;
    private JButton btn_room_add;
    private JButton btn_room_price;
    private JButton btn_hotel_add;
    private JButton btn_season_add;
    private JButton btn_delete;
    private JTextField fld_hotel_del;
    private JTable tbl_room_list;

    private DefaultTableModel mdl_hotel_list;
    private DefaultTableModel mdl_room_list;
    private Object[] row_room_list;

    private Object[] row_hotel_list;
    private User user;
    public static String hotel_name;

    public static int hotel_id;

    public HotelGUI(User user) {
        this.user = user;
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(1300, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        lbl_welcome.setText("Hosgeldiniz " + user.getUser_name());

        // to close the window start
        if (this.user == null) {
            dispose();
        }
        // to close the window end

        mdl_hotel_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        //start to print the table
        Object[] col_hotel = {"Hotel ID", "Hotel Name", "Hotel Address", "Hotel E-Mail", "Hotel Telephone", "Hotel Hostel", "Hotel City", "Hotel Region", "Hotel Star", "Hotel Feature"};
        mdl_hotel_list = new DefaultTableModel();
        mdl_hotel_list.setColumnIdentifiers(col_hotel);
        row_hotel_list = new Object[col_hotel.length];
        tbl_hotel_list.setModel(mdl_hotel_list);
        tbl_hotel_list.getTableHeader().setReorderingAllowed(false);
        loadHotelModel();
        //end to print the table

        btn_hotel_add.addActionListener(e -> {
            HotelAddGUI hotelAddGUI = new HotelAddGUI(user);
            dispose();
        });


        btn_back.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            dispose();
        });

        tbl_hotel_list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    hotel_id = Integer.parseInt(tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString());
                    String selectedRow = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(), 0).toString();
                    fld_hotel_del.setText(selectedRow);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });

        tbl_hotel_list.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = tbl_hotel_list.getSelectedRow();
                tbl_hotel_list.setRowSelectionInterval(selected_row, selected_row);
                hotel_name = tbl_hotel_list.getValueAt(selected_row, 0).toString();

            }


        });
        btn_room_add.addActionListener(e -> {
            if (hotel_name != null) {
                RoomAddGUI roomAddGUI = new RoomAddGUI(hotel_name);
            } else {
                Helper.showMsg("Please Select a Hotel!");
            }

        });


        btn_room_price.addActionListener(e -> {
            if (hotel_name != null) {
                RoomPriceGUI rooomPriceGUI = new RoomPriceGUI(hotel_name);

            } else {
                Helper.showMsg("Please Select a Hotel!");
            }
        });


        btn_season_add.addActionListener(e -> {
            if (hotel_name != null) {
                SeasonAddGUI SeasonAddGUI = new SeasonAddGUI(hotel_name);

            } else {
                Helper.showMsg("Please Select a Hotel!");
            }
        });
        btn_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_del)) {
                Helper.showMsg("fill");
            } else {
                int hotel_id = Integer.parseInt(fld_hotel_del.getText());
                if (Hotel.delete(hotel_id)) {
                    Helper.showMsg("done");
                    loadHotelModel();

                    fld_hotel_del.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }


    private void loadHotelModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Hotel obj : Hotel.getList()) {
            i = 0;
            row_hotel_list[i++] = obj.getHotel_id();
            row_hotel_list[i++] = obj.getHotel_name();
            row_hotel_list[i++] = obj.getHotel_address();
            row_hotel_list[i++] = obj.getHotel_mail();
            row_hotel_list[i++] = obj.getHotel_tel();
            row_hotel_list[i++] = obj.getHotel_hostel();
            row_hotel_list[i++] = obj.getHotel_city();
            row_hotel_list[i++] = obj.getHotel_region();
            row_hotel_list[i++] = obj.getHotel_star();
            row_hotel_list[i++] = obj.getHotel_feature();
            mdl_hotel_list.addRow(row_hotel_list);
        }
    }


}