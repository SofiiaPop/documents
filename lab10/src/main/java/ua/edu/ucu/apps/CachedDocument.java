package ua.edu.ucu.apps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CachedDocument extends DocumentDecorator {
    private static final String DB_URL = "jdbc:sqlite:db.sqlite";
    public CachedDocument(Document document) {
        super(document);
    }

    public void insertCashReceived(double amount, String source) {
        String sql = "INSERT INTO cash (amount, received_at, source) VALUES (?, CURRENT_TIMESTAMP, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, source);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
   public String parse(String path) {
    String cachedParsedString = getParsedStringFromDB(path);
    
    if (cachedParsedString != null) {
        return cachedParsedString;
    } else {
        String result = super.parse(path);
        cacheParsedStringInDB(path, result);
        
        return result;
    }
}

private String getParsedStringFromDB(String path) {
    String sql = "SELECT parsed_string FROM files WHERE path = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, path);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            System.out.println("extracted from cash");
            return rs.getString("parsed_string");
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
    
    return null;
}

private void cacheParsedStringInDB(String path, String parsedString) {
    String sql = "INSERT INTO files (path, parsed_string) VALUES (?, ?)";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, path);
        pstmt.setString(2, parsedString);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
}

}
