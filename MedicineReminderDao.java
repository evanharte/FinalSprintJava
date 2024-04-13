import java.sql.*;

public class MedicineReminderDao {
  public static boolean createMedicineReminder(MedicineReminder reminder) {
    String query = "INSERT INTO medicine_reminders (user_id, medicine_name, dosage, schedule, start_date, end_date) VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'))";


    try {
      Connection con = DatabaseConnection.getCon();
      PreparedStatement statement = con.prepareStatement(query);
      statement.setInt(1, reminder.getUserId());
      statement.setString(2, reminder.getMedicineName());
      statement.setString(3, reminder.getDosage());
      statement.setString(4, reminder.getSchedule());
      statement.setString(5, reminder.getStartDate());
      statement.setString(6, reminder.getEndDate());

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
