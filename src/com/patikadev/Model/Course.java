package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String language;

    private Patika patika;
    private User educator;

    public Course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;
        this.patika = Patika.getFetch(patika_id);
        this.educator = User.getFetch(user_id);

    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courseList = new ArrayList<>();
        String query = "SELECT * FROM course";
        Course obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String language = rs.getString("language");
                obj = new Course(id, user_id, patika_id, name, language);
                courseList.add(obj);
            }
            st.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    public static ArrayList<Course> getListByUser(int user_idP) {
        ArrayList<Course> courseList = new ArrayList<>();
        String query = "SELECT * FROM course WHERE user_id = " + user_idP;
        Course obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String language = rs.getString("language");
                obj = new Course(id, user_id, patika_id, name, language);
                courseList.add(obj);
            }
            st.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    public static ArrayList<Course> getListByPatika(int patika_idP) {
        ArrayList<Course> courseList = new ArrayList<>();
        String query = "SELECT * FROM course WHERE patika_id = " + patika_idP;
        Course obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String language = rs.getString("language");
                obj = new Course(id, user_id, patika_id, name, language);
                courseList.add(obj);
            }
            st.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courseList;
    }

    public static boolean add(int user_id, int patika_id, String name, String language) {
        String query = "INSERT INTO course (user_id, patika_id, name, language) VALUES (?,?,?,?)";
        Course findCourse = Course.getFetch(name);

        if (findCourse != null && findCourse.getName() != name) {
            Helper.showMessage("Bu ders daha önceden eklenmiş!");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, user_id);
            pr.setInt(2, patika_id);
            pr.setString(3, name);
            pr.setString(4, language);
            int result = pr.executeUpdate();
            pr.close();
            return result != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(int id) {

        String query = "DELETE FROM course WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            int result = pr.executeUpdate();
            pr.close();
            return result != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(int id, int user_id, int patika_id, String name, String language) {
        String query = "UPDATE course SET name=?,language=?,user_id=?,patika_id=? WHERE id = ?";
        Course findCourse = Course.getFetch(name);

        if (findCourse != null && findCourse.getName() != name) {
            Helper.showMessage("Bu ders daha önceden eklenmiş!");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, language);
            pr.setInt(3, user_id);
            pr.setInt(4, patika_id);
            pr.setInt(5, id);
            int result = pr.executeUpdate();
            pr.close();
            return result != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Course getFetch(String name) {

        Course obj = null;
        String query = "SELECT * FROM course WHERE name = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),rs.getString("name"),rs.getString("language"));
            }
            pr.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }
    public static Course getFetchById(int id) {

        Course obj = null;
        String query = "SELECT * FROM course WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),rs.getString("name"),rs.getString("language"));
            }
            pr.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
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

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }
}
