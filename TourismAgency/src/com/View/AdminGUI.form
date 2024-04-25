package com.View;

import com.Helper.Config;
import com.Helper.Helper;
import com.Model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminGUI extends JFrame {
    private ArrayList<User> searchUser;
    private JPanel container;
    private JButton btn_back;
    private JLabel lbl_user_name;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JComboBox cmb_user_type;
    private JLabel lbl_user_register;
    private JLabel lbl_user_name_search;
    private JLabel lbl_user_uname;
    private JButton btn_search;
    private JTable tbl_user_list;
    private JTextField fld_user_name_add;
    private JTextField fld_user_uname_add;
    private JTextField fld_user_pass_add;
    private JComboBox cmb_user_reg_add;
    private JTextField fld_user_id_del;
    private JButton btn_user_add;
    private JButton btn_user_id_del;
    private JLabel lbl_user_name_add;
    private JLabel lbl_user_uname_add;
    private JLabel lbl_user_pass_add;
    private JLabel lbl_user_register_add;
    private JTextField fld_user_mail_add;
    private JLabel lbl_user_mail;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    private User user;

    public AdminGUI(User user) {
        this.user = user;
        this.add(container);
        this.setTitle(Config.PROJECT_TİTLE);
        this.setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(Helper.screenSize("x", getSize()), Helper.screenSize("y", getSize()));
        this.setVisible(true);
        lbl_user_name.setText("Welcome " + user.getUser_name());

        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        //start to list the table
        Object[] col_user_list = {"ID", "Name-Surname", "Username", "Password", "User E-mail", "Register Type"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];
        loadUserModel();
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
        //end to list the table

        //selected hotel id start
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_id_del.setText(selected_id);
            } catch (Exception exception) {

            }

        });
        //selected hotel id end

        tbl_user_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_user_list.rowAtPoint(point);
                tbl_user_list.setRowSelectionInterval(selected_row, selected_row);

            }
        });


        tbl_user_list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                    String selectedRow = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                    fld_user_id_del.setText(selectedRow);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });

        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_pass_add) || Helper.isFieldEmpty(fld_user_uname_add) || Helper.isFieldEmpty(fld_user_name_add) || Helper.isFieldEmpty(fld_user_mail_add)) {
                Helper.showMsg("fill");

            } else {
                String user_name = fld_user_name_add.getText();
                String user_uname = fld_user_uname_add.getText();
                String user_mail = fld_user_mail_add.getText();
                String user_pass = fld_user_pass_add.getText();
                String user_type = cmb_user_reg_add.getSelectedItem().toString();
                if (User.add(user_name, user_uname, user_mail, user_pass, user_type)) {
                    Helper.showMsg("add");
                    loadUserModel();
                    fld_user_name_add.setText(null);
                    fld_user_uname_add.setText(null);
                    fld_user_pass_add.setText(null);
                    fld_user_mail_add.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        btn_user_id_del.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id_del)) {
                Helper.showMsg("fill");
            } else {

                int user_id = Integer.parseInt(fld_user_id_del.getText());
                if (User.delete(user_id)) {
                    Helper.showMsg("Selected ID deleted from the list!");
                    loadUserModel();
                    fld_user_id_del.setText(null);
                } else {
                    Helper.showMsg("error");
                }

            }
        });


        btn_search.addActionListener(e -> {
            String user_name = fld_user_name.getText();
            String user_uname = fld_user_uname.getText();
            String user_type = cmb_user_type.getSelectedItem().toString();

            if ("All".equals(user_type)) {
                // List all users when "All" option is selected
                ArrayList<User> allUsers = User.getList();
                loadUserModel();
                loadUserSearchModel(allUsers);
            } else {
                // Otherwise do normal filtering
                String query = User.searchQuery(user_name, user_uname, user_type);
                ArrayList<User> searchUser = User.searchUserList(query);
                loadUserModel();
                loadUserSearchModel(searchUser);
            }
        });



        btn_back.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            dispose();
        });


    }


    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : User.getList()) {
            row_user_list[0] = obj.getUser_id();
            row_user_list[1] = obj.getUser_name();
            row_user_list[2] = obj.getUser_uname();
            row_user_list[3] = obj.getUser_pass();
            row_user_list[4] = obj.getUser_mail();
            row_user_list[5] = obj.getUser_type();
            mdl_user_list.addRow(row_user_list);
        }

    }


    public void loadUserSearchModel(ArrayList<User> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : list) {
            row_user_list[0] = obj.getUser_id();
            row_user_list[1] = obj.getUser_name();
            row_user_list[2] = obj.getUser_uname();
            row_user_list[3] = obj.getUser_pass();
            row_user_list[4] = obj.getUser_mail();
            row_user_list[5] = obj.getUser_type();
            mdl_user_list.addRow(row_user_list);
        }
    }


}
