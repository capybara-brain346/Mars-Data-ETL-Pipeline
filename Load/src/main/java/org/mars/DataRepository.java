package org.mars;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataRepository {

    public void insertMarsWeatherData(List<DataModel> dataModelList) throws SQLException {
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String createTable = String.format("""
                        CREATE TABLE Data_%s (
                        id int PRIMARY KEY AUTO_INCREMENT,
                        firstUTC DATETIME,
                        lastUTC DATETIME,
                        monthOrdinal INT,
                        northernSeason VARCHAR (20),
                                southernSeason VARCHAR(20),
                                season VARCHAR(20),
                                PRE JSON,
                                AT JSON,
                                HWS JSON,
                                WD JSON
                )""", localDateTime);

        String sql = String.format("INSERT INTO Data_%s (firstUTC, lastUTC, monthOrdinal, northernSeason, southernSeason, season, PRE, AT, HWS, WD) VALUES (?,?,?,?,?,?,?,?,?,?)", localDateTime);

        try (Connection conn = DatabaseConnection.getConnection(); Statement createTableStatement = conn.createStatement(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            createTableStatement.executeUpdate(createTable);

            for (DataModel dataModel : dataModelList) {
                preparedStatement.setString(1, dataModel.getFirstUTC());
                preparedStatement.setString(2, dataModel.getLastUTC());
                preparedStatement.setInt(3, dataModel.getMonthOrdinal());
                preparedStatement.setString(4, dataModel.getNorthernSeason());
                preparedStatement.setString(5, dataModel.getSouthernSeason());
                preparedStatement.setString(6, dataModel.getSeason());
                preparedStatement.setString(7, dataModel.getPRE());
                preparedStatement.setString(8, dataModel.getAT());
                preparedStatement.setString(9, dataModel.getHWS());
                preparedStatement.setString(10, dataModel.getWD());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
