package com.Model;

import com.Helper.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Hotel {
    private int hotel_id;
    private String hotel_name;
    private String hotel_address;
    private String hotel_city;
    private String hotel_region;
    private String hotel_mail;
    private String hotel_tel;
    private String hotel_star;
    private String hotel_hostel;
    private String hotel_feature;


    public Hotel() {

    }

    public Hotel(int hotel_id, String hotel_name, String hotel_address, String hotel_city, String hotel_region, String hotel_mail, String hotel_tel, String hotel_star, String hotel_hostel, String hotel_feature) {
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.hotel_address = hotel_address;
        this.hotel_city = hotel_city;
        this.hotel_region = hotel_region;
        this.hotel_mail = hotel_mail;
        this.hotel_tel = hotel_tel;
        this.hotel_star = hotel_star;
        this.hotel_hostel = hotel_hostel;
        this.hotel_feature = hotel_feature;
    }

    //start passing hotel name from database
    public static Hotel getHotelByName(String hotel_name) {
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE hotel_name=?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setString(1, hotel_name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Hotel(rs.getInt("hotel_id"), rs.getString("hotel_name"), rs.getString("hotel_address"),
                        rs.getString("hotel_city"), rs.getString("hotel_region"), rs.getString("hotel_mail"),
                        rs.getString("hotel_tel"), rs.getString("hotel_star"), rs.getString("hotel_hostel"),
                        rs.getString("hotel_feature"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    //end passing hotel name from database

    public static Hotel getHotelByID(int hotel_id) {
        Hotel obj = null;
        String query = "SELECT *FROM hotel WHERE hotel_id=?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Hotel(rs.getInt("hotel_id"), rs.getString("hotel_name"), rs.getString("hotel_address"),
                        rs.getString("hotel_city"), rs.getString("hotel_region"), rs.getString("hotel_mail"),
                        rs.getString("hotel_tel"), rs.getString("hotel_star"), rs.getString("hotel_hostel"),
                        rs.getString("hotel_feature"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    //start passing hotel name from database
    public static String getHotelName(String hotel_name) {
        String hotelName = null;
        String query = "SELECT * FROM hotel WHERE hotel_name=?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setString(1, hotel_name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                hotelName = rs.getString("hotel_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelName;
    }

    //end passing hotel name from database
    public static int getFetchbyInt(String hotel_name) {
        int hID = 0;
        Hotel obj = null;
        String sql = "SELECT*FROM hotel WHERE hotel_name = ?";

        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(sql);
            pr.setString(1, hotel_name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Hotel();
                obj.setHotel_id(rs.getInt("hotel_id"));
                hID = obj.getHotel_id();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hID;
    }

    public static String getHotelNameByHotel(String hotel_name) {
        String hotelName = null;
        String query = "SELECT * FROM hotel WHERE hotel_name=?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setString(1, hotel_name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                hotelName = rs.getString("hotel_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelName;
    }




    //start adding hotel information to database
    public static boolean addHotel(String hotel_name, String hotel_address, String hotel_city, String hotel_region, String hotel_mail, String hotel_tel, String hotel_star, String hotel_hostel, String hotel_feature) {
        String query = "INSERT INTO hotel (hotel_name,hotel_address,hotel_city,hotel_region,hotel_mail,hotel_tel,hotel_star,hotel_hostel,hotel_feature) VALUES (?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setString(1, hotel_name);
            pr.setString(2, hotel_address);
            pr.setString(3, hotel_city);
            pr.setString(4, hotel_region);
            pr.setString(5, hotel_mail);
            pr.setString(6, hotel_tel);
            pr.setString(7, hotel_star);
            pr.setString(8, hotel_hostel);
            pr.setString(9, hotel_feature);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    //end adding hotel information to database

    //start listing hotel information to database
    public static ArrayList<Hotel> getList() {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        Hotel obj;
        String query = "SELECT * FROM hotel";
        try {
            Statement st = DBConnect.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Hotel(rs.getInt("hotel_id"), rs.getString("hotel_name"), rs.getString("hotel_address"),
                        rs.getString("hotel_city"), rs.getString("hotel_region"), rs.getString("hotel_mail"),
                        rs.getString("hotel_tel"), rs.getString("hotel_star"), rs.getString("hotel_hostel"),
                        rs.getString("hotel_feature"));
                hotelList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelList;
    }

    //end listing hotel information to database

    //start pulling hotel data from database
    public static Hotel getFech(int hotel_id) {
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE hotel_id = ?";
        try (PreparedStatement pr = DBConnect.getInstance().prepareStatement(query)) {
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Hotel(rs.getInt("hotel_id"), rs.getString("hotel_name"), rs.getString("hotel_address"),
                        rs.getString("hotel_city"), rs.getString("hotel_region"), rs.getString("hotel_mail"),
                        rs.getString("hotel_tel"), rs.getString("hotel_star"), rs.getString("hotel_hostel"),
                        rs.getString("hotel_feature"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    //end pulling hotel data from database

    //start pulling hotel name from database to hotel_id
    public static String getHotelNameByHotelID(int hotel_id) {
        String hotel_name = null;
        String query = "SELECT hotel_name FROM hotel WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                hotel_name = rs.getString("hotel_name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotel_name;
    }
    //end pulling hotel name from database to hotel_id


    public static int getHotelIDint(int hotel_id) {
        int hotelIDd = 0;
        String query = "SELECT * FROM hotel WHERE hotel_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                hotelIDd = rs.getInt("hotel_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelIDd;
    }


    //start deleting hotel information
    public static boolean delete(int hotel_id) {
        String query = "DELETE hotel, reservation, room, roomPrice, season\n" +
                "FROM hotel\n" +
                "LEFT JOIN reservation ON hotel.hotel_id = reservation.hotel_id\n" +
                "LEFT JOIN room ON hotel.hotel_id = room.hotel_id\n" +
                "LEFT JOIN roomPrice ON hotel.hotel_id = roomPrice.hotel_id\n" +
                "LEFT JOIN season ON hotel.hotel_id = season.hotel_id\n" +
                "WHERE hotel.hotel_id = ?;\n";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);

            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    //end deleting hotel information

    //start deleting reservations
    public static boolean deleteReservation(int reser_id) {
        String query = "DELETE FROM reservation WHERE reser_id = ?";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, reser_id);

            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    //end deleting reservations

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getHotel_city() {
        return hotel_city;
    }

    public void setHotel_city(String hotel_city) {
        this.hotel_city = hotel_city;
    }

    public String getHotel_region() {
        return hotel_region;
    }

    public void setHotel_region(String hotel_region) {
        this.hotel_region = hotel_region;
    }

    public String getHotel_mail() {
        return hotel_mail;
    }

    public void setHotel_mail(String hotel_mail) {
        this.hotel_mail = hotel_mail;
    }

    public String getHotel_tel() {
        return hotel_tel;
    }

    public void setHotel_tel(String hotel_tel) {
        this.hotel_tel = hotel_tel;
    }

    public String getHotel_star() {
        return hotel_star;
    }

    public void setHotel_star(String hotel_star) {
        this.hotel_star = hotel_star;
    }

    public String getHotel_hostel() {
        return hotel_hostel;
    }

    public void setHotel_hostel(String hotel_hostel) {
        this.hotel_hostel = hotel_hostel;
    }

    public String getHotel_feature() {
        return hotel_feature;
    }

    public void setHotel_feature(String hotel_feature) {
        this.hotel_feature = hotel_feature;
    }
}