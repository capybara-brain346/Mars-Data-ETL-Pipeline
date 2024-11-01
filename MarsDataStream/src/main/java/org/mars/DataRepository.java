package org.mars;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DataRepository {

    public void insertMarsWeatherData(DataModel dataModel, List<String> attributes) throws SQLException {
        String localDateTime = LocalDateTime.now().toString();

//        TODO Write different logic for this (horrible practise), keep this as a temporary workaround for now!!!!
        StringBuilder attributesSql = new StringBuilder();
        StringBuilder parameters = new StringBuilder();
        attributes.forEach(n -> attributesSql.append(n).append(", "));
        parameters.append("?, ".repeat(attributes.size()));

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", localDateTime, attributesSql, parameters);

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
