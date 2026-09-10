package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/restaurant_cnpm?allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Thangveo0703@";

    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM tblIngredient";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String unit =  rs.getString("unit");
                double price = rs.getDouble("unitPrice");

                System.out.println(id + " " + name + " " + unit + " " + price);
            }

            rs.close();
            stmt.close();
            conn.close();

            System.out.println("Đã đóng kết nối.");

        } catch (Exception e) {
            System.out.println("Lỗi kết nối hoặc truy vấn!");
            e.printStackTrace();
        }
    }
}