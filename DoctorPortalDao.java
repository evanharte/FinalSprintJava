import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorPortalDao {
    private UserDao userDao;
    private HealthDataDao healthDataDao;

   // Complete all these methods and add more as needed

    public DoctorPortalDao() {
        userDao = new UserDao();
        healthDataDao = new HealthDataDao();
    }

    public User getDoctorById(int doctorId) {
        // Implement this method
        User doctor = userDao.getUserById(doctorId);
        return doctor;
    }

    public List<User> getPatientsByDoctorId(int doctorId) {
        // Implement this method
        List<User> patients = new ArrayList<>();
        
        String query = "SELECT * FROM doctor_patient WHERE doctor_id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int patientId = rs.getInt("patient_id");
                User patient = userDao.getUserById(patientId);
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public List<HealthData> getHealthDataByPatientId(int patientId) {
        // Implement this method
        String query = "SELECT * FROM health_data WHERE user_id = ?";
        List<HealthData> healthData = new ArrayList<>();

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, patientId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int healthDataId = rs.getInt("id");
                HealthData data = healthDataDao.getHealthDataById(healthDataId);
                healthData.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return healthData;
    }
}

