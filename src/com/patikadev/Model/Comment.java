package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Comment {
    private int id;
    private int user_id;
    private String comment;
    private int content_id;


    public Comment(int id, int user_id, String comment, int content_id) {
        this.id = id;
        this.user_id = user_id;
        this.comment = comment;
        this.content_id = content_id;
    }

    public Comment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public static ArrayList<Comment> getList() {
        String query = "SELECT * FROM comment";
        ArrayList<Comment> comments = new ArrayList<>();
        Comment obj;
        Statement st = null;
        try {
            st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Comment();
                obj.setId(rs.getInt("id"));
                obj.setUser_id(rs.getInt("user_id"));
                obj.setComment(rs.getString("comment"));
                obj.setContent_id(rs.getInt("content_id"));

                comments.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return comments;
    }
    public static ArrayList<Comment> getList(int content_idP) {
        String query = "SELECT * FROM comment WHERE content_id = "+content_idP;
        ArrayList<Comment> comments = new ArrayList<>();
        Comment obj;
        Statement st = null;
        try {
            st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Comment();
                obj.setId(rs.getInt("id"));
                obj.setUser_id(rs.getInt("user_id"));
                obj.setComment(rs.getString("comment"));
                obj.setContent_id(rs.getInt("content_id"));

                comments.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return comments;
    }
    public static boolean add(int user_id,String comment,int content_id){
        String query = "INSERT INTO comment (user_id,comment,content_id) VALUES (?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setString(2,comment);
            pr.setInt(3,content_id);
            int response = pr.executeUpdate();
            pr.close();
            return response != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



