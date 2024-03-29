

// import com.DataBaseConnection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HealthMonitoringApp {

    private static UserDaoExample userDao = new UserDaoExample();
    /**
     * Test the following functionalities within the Main Application
     *  1. Register a new user
     *  2. Log in the user
     *  3. Add health data
     *  4. Generate recommendations
     *  5. Add a medicine reminder
     *  6. Get reminders for a specific user
     *  7. Get due reminders for a specific user
     *  8. test doctor portal
     */
    public static void main(String[] args) {
       DatabaseConnection databaseConnection = new DatabaseConnection();
        UserDaoExample userDao = new UserDaoExample();
        // test register a new user
        // test Login user (call testLoginUser() here)
        // Add health data
        // Generate recommendations
        // Add a medicine reminder
        // Get reminders for a specific user
        // Get due reminders for a specific user
        //test doctor portal (call testDoctorPortal() here)


        List<User> userList = new ArrayList<>();

        User user1 = new User(5,"Ainee", "Malik","qmalik@gmail.com", "guggu", false);
        userList.add(user1);

        for (User users : userList) {
            userDao.createUser(users);
        }
    }


    public static boolean loginUser(String email, String password) {
        //implement method to login user.
        User user = userDao.getUserByEmail(email);

        if (user != null) {
            // Compare the stored hashed password with the given password and return result
        }

        return false;

    }


    /**
     * To test the Doctor Portal in your Health Monitoring System, provide a simple test code method that you can add
     * to your main application class.
     * In this method, we'll test the following functionalities:
     * 1. Fetching a doctor by ID
     * 2. Fetching patients associated with a doctor
     * 3. Fetching health data for a specific patient
      */
    public static void testDoctorPortal() {
        // Replace the doctorId with a valid ID from your database
        int doctorId = 1;

        // Add code to Fetch the doctor by ID

        // Add code to Fetch patients associated with the doctor

        // Add code to Fetch health data for the patient

    }


    /**
     * To test the login user functionality in your Health Monitoring System, you can
     * add a test method to your main application class
     */
    public static void testLoginUser() {
        // Replace the email and password with valid credentials from your database
        String userEmail = "john@example.com";
        String userPassword = "password";

        boolean loginSuccess = loginUser(userEmail, userPassword);

        if (loginSuccess) {
            // Print to console, "Login Successful"
        } else {
            // Print to console, "Incorrect email or password. Please try again.");
            // Show an error message and prompt the user to re-enter their credentials
        }
    }

}
