package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private int content_id;
    private String question;
    private String correct_answer;
    private String second_answer;
    private String third_answer;
    private String fourth_answer;

    private Content content;

    // Constructors
    public Quiz(int id, int content_id, String question, String correct_answer, String second_answer, String third_answer, String fourth_answer) {
        this.id = id;
        this.content_id = content_id;
        this.question = question;
        this.correct_answer = correct_answer;
        this.second_answer = second_answer;
        this.third_answer = third_answer;
        this.fourth_answer = fourth_answer;

        this.content = Content.getFetch(content_id);

    }
    public Quiz(int content_id){
        this.content = Content.getFetch(content_id);
    }

    public Quiz(){

    }

    //Methods
    public static ArrayList<Quiz> getList(int content_id){
        String query = "SELECT * FROM quiz WHERE content_id="+content_id;
        ArrayList<Quiz> quizList = new ArrayList<>();
        Quiz obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Quiz(content_id);
                obj.setId(rs.getInt("id"));
                obj.setQuestion(rs.getString("question"));
                obj.setCorrect_answer(rs.getString("correct_answer"));
                obj.setSecond_answer(rs.getString("second_answer"));
                obj.setThird_answer(rs.getString("third_answer"));
                obj.setFourth_answer(rs.getString("fourth_answer"));
                quizList.add(obj);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizList;
    }
    public static boolean add(int content_id,String question,String correct_answer,String second_answer,String third_answer,String fourth_answer){
        String query = "INSERT INTO quiz (content_id,question,correct_answer,second_answer,third_answer,fourth_answer) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, content_id);
            pr.setString(2, question);
            pr.setString(3, correct_answer);
            pr.setString(4, second_answer);
            pr.setString(5, third_answer);
            pr.setString(6,fourth_answer);
            int result = pr.executeUpdate();
            pr.close();
            return result != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean delete(int id){
        String query = "DELETE FROM quiz WHERE id="+id;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            int result = pr.executeUpdate();
            pr.close();

            return result != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean update(int id,String question,String correct_answer,String second_answer,String third_answer, String fourth_answer){
        String query = "UPDATE quiz SET question=?,correct_answer=?,second_answer=?,third_answer=?,fourth_answer=? WHERE id=?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,question);
            pr.setString(2,correct_answer);
            pr.setString(3,second_answer);
            pr.setString(4,third_answer);
            pr.setString(5,fourth_answer);
            pr.setInt(6,id);

            int response = pr.executeUpdate();
            pr.close();
            return response != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Quiz getFetch(int id){
        String query = "SELECT * FROM quiz WHERE id="+id;
        Quiz obj = null;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new Quiz();
                obj.setId(rs.getInt("id"));
                obj.setContent_id(rs.getInt("content_id"));
                obj.setQuestion(rs.getString("question"));
                obj.setCorrect_answer(rs.getString("correct_answer"));
                obj.setSecond_answer(rs.getString("second_answer"));
                obj.setThird_answer(rs.getString("third_answer"));
                obj.setFourth_answer(rs.getString("fourth_answer"));

            }
            pr.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    // Getter-Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getSecond_answer() {
        return second_answer;
    }

    public void setSecond_answer(String second_answer) {
        this.second_answer = second_answer;
    }

    public String getThird_answer() {
        return third_answer;
    }

    public void setThird_answer(String third_answer) {
        this.third_answer = third_answer;
    }

    public String getFourth_answer() {
        return fourth_answer;
    }

    public void setFourth_answer(String fourth_answer) {
        this.fourth_answer = fourth_answer;
    }

    public Content getContent() {
        return content;
    }

    // Methods



}
