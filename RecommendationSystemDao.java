import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class RecommendationSystemDao {
  public static boolean InsertRecommendationData(HealthData hd,RecommendationSystem recommendation) { /* insert health data into database */ 
  String query = "INSERT INTO recommendations (user_id, recommendation_text, date) VALUES (?, ?, ?)";

  try {
    Connection con = DatabaseConnection.getCon();
    PreparedStatement statement = con.prepareStatement(query);
    statement.setInt(1, hd.getUserId());
    statement.setString(2, recommendation.generateRecommendations(hd).toString());
    statement.setString(6, hd.getDate());
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
