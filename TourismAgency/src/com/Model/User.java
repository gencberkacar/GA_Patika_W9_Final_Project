package com.Model;

import com.Helper.DBConnect;
import com.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int user_id;
    private String user_name;
    private String user_uname;
    private String user_pass;
    private String user_mail;
    private String user_type;

    public User(int user_id, String user_name, String user_uname, String user_pass, String user_mail, String user_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_uname = user_uname;
        this.user_pass = user_pass;
        this.user_mail = user_mail;
        this.user_type = user_type;
    }

    public User() {

    }



    //start pulling data from database by username and password
    public static User getFetch(String user_uname, String user_pass) {
        User obj = null;
        String query = "SELECT * FROM user WHERE user_uname=? AND user_pass=?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setString(1, user_uname);
            pr.setString(2, user_pass);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                obj = new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_uname"),
                        rs.getString("user_pass"), rs.getString("user_mail"), rs.getString("user_type"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    //end pulling data from database by username and password


    //start pulling data from user table
    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        User obj;
        try {
            Statement st = DBConnect.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User();
                obj.setUser_id(rs.getInt("user_id"));
                obj.setUser_name(rs.getString("user_name"));
                obj.setUser_uname(rs.getString("user_uname"));
                obj.setUser_pass(rs.getString("user_pass"));
                obj.setUser_mail(rs.getString("user_mail"));
                obj.setUser_type(rs.getString("user_type"));
                userList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
    //end pulling data from user table

    //start deleting data based on user id
    public static boolean delete(int user_id) {
        String query = "DELETE FROM user WHERE user_id=?";

        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, user_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //end deleting data based on user id

    //start searching by user type, user name, username
    public static String searchQuery(String user_name, String user_uname, String user_type) {
        String query = "SELECT user_id, user_name, user_uname, user_pass, user_mail, user_type FROM user WHERE user_uname LIKE '%{{user_uname}}%' AND user_name LIKE '%{{user_name}}%'";
        query = query.replace("{{user_name}}", user_name);
        query = query.replace("{{user_uname}}", user_uname);
        if (!user_type.isEmpty()) {
            query += " AND user_type = '{{user_type}}'";
            query = query.replace("{{user_type}}", user_type);
        }
        return query;
    }
    //end searching by user type, user name, username


    public static ArrayList<User> searchUserList(String query) {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnect.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User();
                obj.setUser_id(rs.getInt("user_id"));
                obj.setUser_name(rs.getString("user_name"));
                obj.setUser_uname(rs.getString("user_uname"));
                obj.setUser_pass(rs.getString("user_pass"));
                obj.setUser_mail(rs.getString("user_mail"));
                obj.setUser_type(rs.getString("user_type"));

                userList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    //start adding user
    public static boolean add(String user_name, String user_uname, String user_mail, String user_pass, String user_type) {
        String query = "INSERT INTO user (user_name, user_uname, user_pass, user_mail, user_type) VALUES (?, ?, ?, ?, ?);\n";
        User findUser = User.getFetch(user_uname, user_pass);
        if (findUser != null) {
            Helper.showMsg("This username has already in use !");
            return false;
        }
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setString(1, user_name);
            pr.setString(2, user_uname);
            pr.setString(3, user_pass);
            pr.setString(4, user_mail);
            pr.setString(5, user_type);

            int response = pr.executeUpdate();
            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return true;
    }
    //end adding user

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_uname() {
        return user_uname;
    }

    public void setUser_uname(String user_uname) {
        this.user_uname = user_uname;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}