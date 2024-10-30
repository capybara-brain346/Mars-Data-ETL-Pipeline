package org.mars;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataRepository {

    public void insertMarsWeatherData(DataModel dataModel) throws SQLException {
        String sql = "INSERT INTO MarsWeatherData (firstUTC, lastUTC, monthOrdinal, northernSeason, southernSeason, season, PRE, AT, HWS, WD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {


            preparedStatement.setString(1, dataModel.getFirstUTC());
            preparedStatement.setString(2, dataModel.getLastUTC());
            preparedStatement.setInt(3, dataModel.getMonthOrdinal());
            preparedStatement.setString(4, dataModel.getNorthernSeason());
            preparedStatement.setString(5, dataModel.getSouthernSeason());
            preparedStatement.setString(6, dataModel.getSeason());
            preparedStatement.setString(7, dataModel.getPRE());
            preparedStatement.setString(8, dataModel.getAT());
            preparedStatement.setString(9, dataModel.getHWS());
            preparedStatement.setString(10, dataModel.getWD());  // Store WD JSON string directly

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
