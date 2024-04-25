package com.Model;

import com.Helper.DBConnect;
import com.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Season {
    private int season_id;
    private int hotel_id;
    private String season_name;
    private String start_date;
    private String finish_date;

    public Season(int season_id, int hotel_id, String season_name, String start_date, String finish_date) {
        this.season_id = season_id;
        this.hotel_id = hotel_id;
        this.season_name = season_name;
        this.start_date = start_date;
        this.finish_date = finish_date;
    }

    public Season() {
    }

    //start listing season information
    public static boolean add(int hotel_id, String season_name, String start_date, String finish_date) {
        String query = "INSERT INTO season (hotel_id,season_name,start_date,finish_date) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);

            pr.setInt(1, hotel_id);
            pr.setString(2, season_name);
            pr.setString(3, start_date);
            pr.setString(4, finish_date);

            int response = pr.executeUpdate();
            if (response == -1) {
                Helper.showMsg("error");
            }
            return response != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //end listing season information

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


    public static ArrayList<Season> getList(int hotel_id) {
        ArrayList<Season> seasonList = new ArrayList<>();
        String query = "SELECT * FROM season WHERE hotel_id = ?";
        Season obj;
        try {
            PreparedStatement pr = DBConnect.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                obj = new Season();
                obj.setSeason_name(rs.getString("season_name"));
                seasonList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seasonList;
    }


    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getSeason_name() {
        return season_name;
    }

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(String finish_date) {
        this.finish_date = finish_date;
    }

    public void add(String text) {
    }

    public void remove(String text) {
    }
}