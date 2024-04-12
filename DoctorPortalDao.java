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
    }

    // Add more methods for other doctor-specific tasks

}

