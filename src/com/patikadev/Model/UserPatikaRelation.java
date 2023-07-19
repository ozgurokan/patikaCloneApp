package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserPatikaRelation {
    private int user_id;
    private int patika_id;

    public UserPatikaRelation(int user_id, int patika_id) {
        this.user_id = user_id;
        this.patika_id = patika_id;
    }

    public static ArrayList<Patika> getStudentPatikaList(int user_id){

        String query = "SELECT * FROM user_patika_relation WHERE user_id="+user_id;

        ArrayList<Patika> studentPatikaList = new ArrayList<>();


        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                studentPatikaList.add(Patika.getFetch(rs.getInt("patika_id")));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    return studentPatikaList;

    }


    public static boolean add(int user_id, int patika_id) {

        boolean logic = true;
        for(Patika obj: UserPatikaRelation.getStudentPatikaList(user_id)){
            if(obj.getId() == patika_id){
                Helper.showMessage("Bu Patikaya Zaten Kayıtlısın");
                logic = false;
                break;
            }
        }

        if (logic == true){
            String query = "INSERT INTO user_patika_relation (user_id, patika_id) VALUES (?,?)";
            try {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setInt(1, user_id);
                pr.setInt(2, patika_id);
                int response = pr.executeUpdate();
                pr.close();

                return response != -1;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else{
            return false;
        }

    }

    public static boolean delete(int patika_id,int user_id){
        String query = "DELETE FROM user_patika_relation WHERE patika_id="+patika_id+" AND user_id="+user_id;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            int response = pr.executeUpdate();
            pr.close();

            return response != -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
