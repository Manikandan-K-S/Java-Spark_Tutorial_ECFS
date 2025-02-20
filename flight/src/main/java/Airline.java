package main.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Airline {

    static final String url = "jdbc:mysql://localhost:3306/airline";
    static final String user = "root";
    static final String pass = "";

    static void create_flight(String number, String destination) throws SQLException {
        Connection con = DriverManager.getConnection(url,user,pass);
        String sql = "INSERT INTO flights(flight_number,destination) VALUES (?,?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1,number);
        st.setString(2,destination);
        st.executeUpdate();
        con.close();
    }

    static void create_passenger(String name, String email, int flight_id) throws SQLException {
        Connection con = DriverManager.getConnection(url,user,pass);

        String sql = "INSERT INTO passengers(name,email,flight_id) VALUES (?,?,?)";

        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1,name);
        st.setString(2,email);
        st.setInt(3,flight_id);
        st.executeUpdate();
        con.close();
    }

    static void delete_passenger(int id) throws SQLException {
        Connection con = DriverManager.getConnection(url,user,pass);
        String sql = "Delete from passengers where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1,id);
        st.executeUpdate();
    }
    static void delete_flight(int id) throws SQLException {
        Connection con = DriverManager.getConnection(url,user,pass);
        String sql = "Delete from flights where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1,id);
        st.executeUpdate();
    }

    static final void update_destination(int id, String destination) throws SQLException {
        Connection con = DriverManager.getConnection(url,user,pass);
        String sql = "update flights set destination = ? where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1,destination);
        st.setInt(2,id);
        st.executeUpdate();
    }

    static List<Map<String, Object>> join_tables() throws SQLException {

        List<Map<String, Object>> details = new ArrayList<>();

        Connection con = DriverManager.getConnection(url,user,pass);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM flights f join passengers p where p.flight_id  = f.id;");
        while(rs.next()){
            Map<String, Object> detail = new HashMap<>();
            detail.put("id", rs.getInt(4));
            detail.put("name", rs.getString("name"));
            detail.put("email", rs.getString("email"));
            detail.put("flight_number", rs.getString("flight_number"));
            detail.put("destination",rs.getString("destination"));
            details.add(detail);
        }

        return details;

    }

    static List<Map<String,Object>> passengers() throws SQLException{

        List<Map<String, Object>> details = new ArrayList<>();

        Connection con = DriverManager.getConnection(url,user,pass);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM passengers");
        while(rs.next()){
            Map<String, Object> detail = new HashMap<>();
            detail.put("id", rs.getInt("id"));
            detail.put("name", rs.getString("name"));
            detail.put("email", rs.getString("email"));
            detail.put("flight_id", rs.getInt("flight_id"));
            details.add(detail);
        }

        return details;

    }

    static List<Map<String,Object>> flights() throws SQLException{

        List<Map<String, Object>> details = new ArrayList<>();

        Connection con = DriverManager.getConnection(url,user,pass);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM flights");
        while(rs.next()){
            Map<String, Object> detail = new HashMap<>();
            detail.put("id", rs.getInt("id"));
            detail.put("flight_number", rs.getString("flight_number"));
            detail.put("destination", rs.getString("destination"));
            details.add(detail);
        }

        return details;

    }

    public static void main(String[] args) throws SQLException {
        update_destination(1,"dubai");
    }


    public static String stmtToString(String query, Object... params) {
        for (Object param : params) {
            query = query.replaceFirst("\\?", "'" + param + "'");
        }
        return query;
    }
}
