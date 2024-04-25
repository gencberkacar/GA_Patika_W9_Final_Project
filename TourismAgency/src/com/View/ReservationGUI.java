package com.View;

import com.Helper.Config;
import com.Helper.DBConnect;
import com.Helper.Helper;
import com.Model.Hotel;
import com.Model.Room;
import com.Model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationGUI extends JFrame {
    private JPanel container;
    private JLabel lbl_res_name;
    private JButton btn_back;
    private JScrollPane scrl_res;
    private JTable tbl_res_list;
    private JButton btn_reser_add;
    private JButton btn_reser_search;
    private JButton btn_res_del;
    private JTextField fld_res_del;

    private final User user;
    private DefaultTableModel mdl_res_list;
    private Object[] row_res_list;


    public ReservationGUI(User user) {
        this.user = user;
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(800, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        lbl_res_name.setText("Welcome " + user.getUser_name());

        mdl_res_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        //start to print the table
        Object[] col_res_list = {"Reservation ID", "Customer Name - Surname", "Customer ID", "Contact Name Surname", "Reservation Note", "Contact Telephone", "Customer E-mail"};
        mdl_res_list = new DefaultTableModel();
        mdl_res_list.setColumnIdentifiers(col_res_list);
        row_res_list = new Object[col_res_list.length];
        loadResListModel();
        tbl_res_list.setModel(mdl_res_list);
        tbl_res_list.getTableHeader().setReorderingAllowed(false);
        //end to print the table

        btn_reser_add.addActionListener(e -> {
            SearchGUI searchGUI = new SearchGUI(user);
            dispose();
        });


        btn_reser_search.addActionListener(e -> {
            SearchRoomGUI searchRoomGUI = new SearchRoomGUI(user);
            dispose();
        });
        btn_back.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            dispose();
        });

        tbl_res_list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    String selectedRow = tbl_res_list.getValueAt(tbl_res_list.getSelectedRow(), 0).toString();
                    fld_res_del.setText(selectedRow);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });

        btn_res_del.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_res_del)) {
                Helper.showMsg("fill");
            } else {
                int reser_id = Integer.parseInt(fld_res_del.getText());
                if (Hotel.deleteReservation(reser_id)) {
                    Helper.showMsg("done");
                    loadResListModel();
                    increaseStock(reser_id);
                    fld_res_del.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    //start showing booking properties
    private void loadResListModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_res_list.getModel();
        clearModel.setRowCount(0);
        String query = "SELECT * FROM reservation";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int i = 0;
                row_res_list[i++] = rs.getInt("reser_id");
                row_res_list[i++] = rs.getString("reser_guest_name");
                row_res_list[i++] = rs.getString("reser_guest_tc");
                row_res_list[i++] = rs.getString("reser_custom_name");
                row_res_list[i++] = rs.getString("reser_contact_note");
                row_res_list[i++] = rs.getString("reser_contact_tel");
                row_res_list[i++] = rs.getString("reser_contact_mail");
                mdl_res_list.addRow(row_res_list);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //end showing booking properties

    //start stock increase method
    private static void increaseStock(int room_id) {
        String query = "UPDATE room SET room_stock = room_stock + 1 WHERE room_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, room_id);
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //end stock increase method
}