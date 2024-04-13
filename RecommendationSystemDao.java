import java.sql.*;

public class RecommendationSystemDao {
  public static boolean InsertRecommendationData(HealthData hd, String recommendation) {
    String query = "INSERT INTO recommendations (user_id, recommendation_text, date) VALUES (?, ?, ?)";

    try {
      Connection con = DatabaseConnection.getCon();
      PreparedStatement statement = con.prepareStatement(query);
      statement.setInt(1, hd.getUserId());
      statement.setString(2, recommendation);
      statement.setString(3, hd.getDate());
      int updatedRows = statement.executeUpdate();
      if (updatedRows != 0) {
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
