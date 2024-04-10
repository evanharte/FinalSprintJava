import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * The MedicineReminderManager class should have methods to add reminders, get reminders
 *  1. for a specific user, and
 *  2. get reminders that are DUE for a specific user.
 *
 *  You'll need to integrate this class with your application and database logic to
 *  1. store,
 *  2. update, and
 *  3. delete reminders as needed.
 */

public class MedicineReminderManager {
    private List<MedicineReminder> reminders;

    public MedicineReminderManager() {
        this.reminders = new ArrayList<>();
    }

    public void addReminder(MedicineReminder reminder) {
        this.reminders.add(reminder);
    }

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

    public List<MedicineReminder> getRemindersForUser(int userId) {
        List<MedicineReminder> userReminders = new ArrayList<>();
        // Write your logic here
        for (MedicineReminder reminder : this.reminders) {
            if (reminder.getUserId() == userId) {
                userReminders.add(reminder);
            }
        }

        return userReminders;
    }

    public List<MedicineReminder> getDueReminders(int userId) {
        List<MedicineReminder> dueReminders = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // Write your logic here
        for (MedicineReminder reminder : reminders) {
            if (reminder.getUserId() == userId) {
                LocalDateTime startDate = LocalDateTime.parse(reminder.getStartDate() + " 00:00", formatter);
                LocalDateTime endDate = LocalDateTime.parse(reminder.getEndDate() + " 00:00", formatter);
                if (now.isAfter(startDate) && now.isBefore(endDate)) {
                    dueReminders.add(reminder);
                }
            }
        }

        return dueReminders;
    }
}
