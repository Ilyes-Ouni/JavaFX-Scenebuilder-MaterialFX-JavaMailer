package com.example.scenebuilderproject.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connexion {
    private Connection con;

    public void connexion() throws SQLException {
        this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/marathon", "root", "");
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return con.prepareStatement(query);
    }

    public void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
