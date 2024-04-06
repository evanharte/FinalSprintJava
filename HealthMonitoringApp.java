

// import com.DataBaseConnection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;


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

        // initialiaze any global variables
        HealthData hd = new HealthData();
        int DailySteps = 10000;

        // test register a new user with createUser() method via command line input
        System.out.println("-------------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Health Monitoring System. Would you like to register a new user? (yes/no)");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Register: ");
            System.out.println("-------------------------------");
            System.out.println("Enter first name: ");
            String first_name = scanner.nextLine();
            System.out.println("Enter last name: ");
            String last_name = scanner.nextLine();
            System.out.println("Enter email: ");
            String email = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            System.out.println("Are you a doctor? (true/false): ");
            boolean is_doctor = scanner.nextBoolean();
            User user = new User(first_name, last_name, email, password, is_doctor);
            UserDaoExample.createUser(user);

            User user1 = UserDaoExample.getUserByEmail(email);
            int userId = user1.getId();
            System.out.println("Your user ID # is: " + userId);
            System.out.println();
        } 

        // test Login user (call testLoginUser() here) - user will be prompted to enter email and password from within the method
        System.out.println();
        System.out.println("Log In: ");
        System.out.println("-------------------------------");
        testLoginUser();
        
        // Add health data
        System.out.println("Add Health Data: ");
        System.out.println("-------------------------------");
        System.out.println("Would you like to add new health data? (yes/no)");
        String res = scanner.nextLine();

        if (res.equalsIgnoreCase("yes")) {
            System.out.println("Enter your ID #: ");
            int user_id = scanner.nextInt();
            System.out.println("Enter weight: ");
            double weight = scanner.nextDouble();
            System.out.println("Enter height: ");
            double height = scanner.nextDouble();
            System.out.println("Enter steps: ");
            int steps = scanner.nextInt();
            System.out.println("Enter resting heart rate: ");
            int heart_rate = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()
            System.out.println("Enter Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();
            hd = new HealthData(user_id, weight, height, steps, heart_rate, date);
            boolean isDataCreated = HealthDataDao.createHealthData(hd);
            if (isDataCreated) {
                System.out.println("Health data added successfully.");
            } else {
                System.out.println("Failed to add health data.");
            }
        }
        
        // Generate recommendations
        System.out.println();
        System.out.println("System Recommendations: ");
        System.out.println("-------------------------------");
        System.out.println("Would you like the system to generate health recommendations for you? (yes/no): ");
        String response1 = scanner.nextLine();
        if (response1.equalsIgnoreCase("yes")) {
            System.out.println("-------------------------------");
            System.out.println("Your weight is: " + hd.getWeight());
            System.out.println("Your height is: " + hd.getHeight());
            System.out.println("Your have " + hd.getSteps() + " steps so far today.");
            System.out.println("Your resting heart rate is: " + hd.getHeartRate());
            System.out.println("-------------------------------");

            if (hd.getWeight() > 200 && hd.getHeight() < 5.5) {
                System.out.println("Because your weight is over 200lbs and your height is under 5.5, consider watching less TV and eating healthier foods.");
            } else if (hd.getWeight() < 100 && hd.getHeight() > 6.0) {
                System.out.println("Because your weight is under 100lbs and your height is over 6.0, you need to increase your daily calorie intake to gain some weight!");
            } else {
                System.out.println("You're maintaining a healthy weight for your height and weight! What's your secret??");
            }

            int StepsToGo = DailySteps - hd.getSteps();

            if (hd.getSteps() < 1000) {
                System.out.println("You got loss than 1000 steps today. You should consider walking more or going for a jog.");
                System.out.println("You have " + StepsToGo + " steps to go to reach the daily goal.");
            } else if (hd.getSteps() > DailySteps) {
                System.out.println("Your steps are over 10,000! Way to go, champ.");
            } else {
                System.out.println("You're doing okay with your steps.");
                System.out.println("You have " + StepsToGo + " steps to go to reach the daily goal.");
            }

            if (hd.getHeartRate() > 100) {
                System.out.println("Your resting heart rate is very high. Consider seeing your doctor.");
            } else if (hd.getHeartRate() > 80) {
                System.out.println("Your resting heart rate is a bit high.");
                System.out.println("You should consider doing some daily light exercise. Going for 20-30 walks is a good place to start!");
            } else if (hd.getHeartRate() < 40) {
                System.out.println("Your resting heart rate is low. Consider seeing your doctor.");
            } else if (hd.getHeartRate() == 0 || hd.getHeartRate() < 0) {
                System.out.println("You are dead. Rest in Peace.");
            } else {
                System.out.println("Your resting heart rate is normal. Keep up the good work!");
            }
        }



        // Add a medicine reminder
        // Get reminders for a specific user
        // Get due reminders for a specific user
        //test doctor portal (call testDoctorPortal() here)
    }


    // FUNCTIONS //////////////////////////////////////////
    public static boolean loginUser(String email, String password) {
        //implement method to login user.
        User user = UserDaoExample.getUserByEmail(email);

        if (user != null) {
            // Compare the stored hashed password with the given password and return result
            return BCrypt.checkpw(password, user.getPassword());
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
        // Prompt the user to enter their email and password to log them in and test whether it was successful or not all at once

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter email: ");
            String email = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            boolean loginSuccess = loginUser(email, password);

            if (loginSuccess) {
                // Print to console, "Login Successful"
                System.out.println("Login Successful");
                User user1 = UserDaoExample.getUserByEmail(email);
                int userId = user1.getId();
                System.out.println("Your user ID # is: " + userId);
                System.out.println();
                break;
            } else {
                // Print to console, "Incorrect email or password. Please try again.");
                System.out.println("Incorrect email or password. Please try again.");
                // Show an error message and prompt the user to re-enter their credentials
            }
        }      
    }

    // addHealthData() method to add health data to the database
    public static void addHealthData(HealthData healthData) {
        // Add health data to the database
        
    }
}
