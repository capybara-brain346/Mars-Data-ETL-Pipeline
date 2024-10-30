package org.mars;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataRepository {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void insertMarsWeatherData(DataModel data) throws SQLException {
        String sql = "INSERT INTO MarsWeatherData (id, firstUTC, lastUTC, monthOrdinal, northernSeason, southernSeason, season, PRE, AT, HWS, WD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, data.getId());
            pstmt.setString(2, data.getFirstUTC());
            pstmt.setString(3, data.getLastUTC());
            pstmt.setInt(4, data.getMonthOrdinal());
            pstmt.setString(5, data.getNorthernSeason());
            pstmt.setString(6, data.getSouthernSeason());
            pstmt.setString(7, data.getSeason());
            pstmt.setString(8, data.getPRE());
            pstmt.setString(9, data.getAT());
            pstmt.setString(10, data.getHWS());
            pstmt.setString(11, data.getWD());  // Store WD JSON string directly

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
