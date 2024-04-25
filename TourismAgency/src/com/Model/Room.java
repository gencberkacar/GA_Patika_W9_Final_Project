package com.Model;

import com.Helper.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Room {
    private int room_id;

    private String room_type;
    private int room_bed;

    private int room_stock;
    private boolean room_tv;
    private boolean room_minibar;
    private boolean room_safebox;
    private Hotel hotel_id;
    private String hotel_name;

    private Hotel hotel;

    public Room(int room_id, String room_type, int room_bed, String hotelName, int room_stock, boolean room_tv, boolean room_minibar, boolean room_safebox) {
        this.room_id = room_id;
        this.room_type = room_type;
        this.room_bed = room_bed;
        this.room_stock = room_stock;
        this.room_tv = room_tv;
        this.room_minibar = room_minibar;
        this.room_safebox = room_safebox;

        this.hotel_name = Hotel.getHotelName(hotelName);

    }

    public Room(int room_id, String hotel_Name, String room_type, int room_bed, int room_stock, boolean room_tv, boolean room_minibar, boolean room_safebox, Hotel hotel_id, String hotel_name) {
        this.room_id = room_id;
        this.room_type = room_type;
        this.room_bed = room_bed;
        this.room_stock = room_stock;
        this.room_tv = room_tv;
        this.room_minibar = room_minibar;
        this.room_safebox = room_safebox;
        this.hotel_id = hotel_id;
        this.hotel_name = Hotel.getHotelName(hotel_Name);
    }


    public Room(String hotelName, String room_type, int room_bed, int room_stock) {
        this.room_type = room_type;
        this.room_bed = room_bed;
        this.room_stock = room_stock;
        this.hotel = new Hotel();
        this.hotel.setHotel_name(hotelName);
    }

    public Room(String hotelName) {
        this.hotel_name = hotelName;

    }

    public static int roomTypeAdd(String room_type) {
        String query = "SELECT * FROM room WHERE room_type=?";
        int i = 0;
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setString(1, room_type);

            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                i = rs.getInt("room_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    //start pulling room_id data by hotel id
    public static int getHotelIDbyRoomID(int room_id) {
        String query = "SELECT * FROM room WHERE room_id = ?";
        int obj = 0;
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, room_id);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                obj = rs.getInt("room_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    //end pulling room_id data by hotel id

    //start pulling room rate data by hotel id
    public static int getHotelIDbyRoomPriceID(int roomPrice_id) {
        String query = "SELECT * FROM roomPrice WHERE roomPrice_id = ?";
        int obj = 0;
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, roomPrice_id);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                obj = rs.getInt("roomPrice_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    //end pulling room rate data by hotel id

    // start adding rooms
    public static boolean addRoom(int room_id, int hotel_id, String hotel_name, String room_type, int room_bed, int room_stock, boolean room_tv, boolean room_minibar, boolean room_safebox) {
        String query = "INSERT INTO room (room_id, hotel_id, hotel_name, room_type, room_bed, room_stock, room_tv, room_minibar, room_safebox,room_initial_stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, room_id);
            pr.setInt(2, hotel_id);
            pr.setString(3, hotel_name);
            pr.setString(4, room_type);
            pr.setInt(5, room_bed);
            pr.setInt(6, room_stock);
            pr.setBoolean(7, room_tv);
            pr.setBoolean(8, room_minibar);
            pr.setBoolean(9, room_safebox);
            pr.setInt(10, room_stock);
            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
            // veya
            System.out.println("SQL Hatası: " + e.getMessage());
        }

        return true;
    }
    // end adding rooms

    //start listing room information
    public static ArrayList<Room> getListByRoom() {
        ArrayList<Room> roomList = new ArrayList<>();
        Room obj;
        String query = "SELECT * FROM room";
        try {
            Statement st = DBConnect.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Room(rs.getString("hotel_name"), rs.getString("room_type"), rs.getInt("room_bed"), rs.getInt("room_stock")
                );
                roomList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomList;
    }
    //end listing room information


    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getRoom_bed() {
        return room_bed;
    }

    public void setRoom_bed(int room_bed) {
        this.room_bed = room_bed;
    }

    public int getRoom_stock() {
        return room_stock;
    }

    public void setRoom_stock(int room_stock) {
        this.room_stock = room_stock;
    }

    public boolean isRoom_tv() {
        return room_tv;
    }

    public void setRoom_tv(boolean room_tv) {
        this.room_tv = room_tv;
    }
    public String getHotel_name() {
        return hotel_name;
    }
    public boolean isRoom_minibar() {
        return room_minibar;
    }

    public void setRoom_minibar(boolean room_minibar) {
        this.room_minibar = room_minibar;
    }

    public boolean isRoom_safebox() {
        return room_safebox;
    }

    public void setRoom_safebox(boolean room_safebox) {
        this.room_safebox = room_safebox;
    }

    public Hotel getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Hotel hotel_id) {
        this.hotel_id = hotel_id;
    }



    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }
}