package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int id;
    private int course_id;
    private int user_id;
    private String title;
    private String description;
    private String link;

    private User educator;
    private Course course;


    public Content(int id, int user_id, int course_id, String title, String description, String link) {
        this.id = id;
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.course = Course.getFetchById(course_id);
        this.educator = User.getFetch(user_id);
    }

    public Content(int user_id,int course_id){
        this.course = Course.getFetchById(course_id);
        this.educator = User.getFetch(user_id);
    }

    public Content() {

    }

    public static ArrayList<Content> getList(int user_idP) {
        String query = "SELECT * FROM content WHERE user_id = " + user_idP;
        ArrayList<Content> contents = new ArrayList<>();
        Content obj;
        try {
            Statement pr = DBConnector.getInstance().createStatement();
            ResultSet rs = pr.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int course_id = rs.getInt("course_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String link = rs.getString("link");

                obj = new Content(id, user_id, course_id, title, description, link);
                contents.add(obj);
            }
            pr.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contents;
    }
    public static ArrayList<Content> getListByCourse(int course_idP) {
        String query = "SELECT * FROM content WHERE course_id = " + course_idP;
        ArrayList<Content> contents = new ArrayList<>();
        Content obj;
        try {
            Statement pr = DBConnector.getInstance().createStatement();
            ResultSet rs = pr.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int course_id = rs.getInt("course_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String link = rs.getString("link");

                obj = new Content(id, user_id, course_id, title, description, link);
                contents.add(obj);
            }
            pr.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contents;
    }


    public static ArrayList<Content> searchCourseList(int user_id, String title,int course_id){
        ArrayList<Content> contentList= new ArrayList<>();
        Content obj;
        String query = "SELECT * FROM  content  WHERE title LIKE '%{{title}}%'";
        query = query.replace("{{title}}",title);

        if(course_id != 0){
            query += " AND course_id =" + course_id;
        }
        query += " AND user_id="+ user_id;
        try {
            Statement st = DBConnector.getInstance().createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Content(rs.getInt("user_id"),rs.getInt("course_id"));
                obj.setId(rs.getInt("id"));
                obj.setTitle(rs.getString("title"));
                obj.setDescription(rs.getString("description"));
                obj.setLink(rs.getString("link"));

                contentList.add(obj);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contentList;
    }

    public static boolean add(int user_id, int course_id, String title, String description, String link) {
        String query = "INSERT INTO content (course_id,user_id,title,description,link) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, course_id);
            pr.setInt(2, user_id);
            pr.setString(3, title);
            pr.setString(4, description);
            pr.setString(5, link);
            int result = pr.executeUpdate();
            pr.close();
            return result != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(int id) {

        String query = "DELETE FROM content WHERE id=?";

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

    public static boolean update(int id, int course_id, String title, String description, String link) {
        String query = "UPDATE content SET course_id=?,title=?,description=?,link=? WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, course_id);
            pr.setString(2, title);
            pr.setString(3, description);
            pr.setString(4, link);
            pr.setInt(5, id);

            int result = pr.executeUpdate();
            pr.close();

            return result != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Content getFetch(int id){
        Content obj = null;
        String query = "SELECT * FROM content WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Content();
                obj.setId(rs.getInt("id"));
                obj.setCourse_id(rs.getInt("course_id"));
                obj.setUser_id(rs.getInt("user_id"));
                obj.setTitle(rs.getString("title"));
                obj.setDescription(rs.getString("description"));
                obj.setLink(rs.getString("link"));

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

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
