package application;


import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "";
        try (Connection conn = DriverManager.getConnection(url,user,password);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
        ){
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println("|");
                System.out.println(resultSet.getString("name"));
                System.out.println("|");
                System.out.println(resultSet.getDouble("price"));
                System.out.println("|");
                System.out.println(resultSet.getDate("date_register"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}