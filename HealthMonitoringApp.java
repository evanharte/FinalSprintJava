import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;


public class HealthMonitoringApp {

    private static UserDaoExample userDao = new UserDaoExample();
    public static User instanceUser = new User();
    public static Doctor instanceDoctor = new Doctor();
    
    public static void main(String[] args) {
        
        // initialiaze any global variables
        HealthData hd = new HealthData();
        MedicineReminder mr = new MedicineReminder();
        MedicineReminderManager mrm = new MedicineReminderManager();

/////////////////////REGISTER//////////////////////////

        // REGISTER a new user with createUser() method via command line input
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
            instanceUser.setFirstName(first_name);
            instanceUser.setLastName(last_name);
            instanceUser.setEmail(email);
            instanceUser.setPassword(password);
            instanceUser.setDoctor(is_doctor);
            UserDaoExample.createUser(instanceUser);

            User user1 = UserDaoExample.getUserByEmail(email);
            int userId = user1.getId();
            System.out.println("Your user ID # is: " + userId);
            System.out.println();
        } 

/////////////////////LOGIN//////////////////////////

        // LOGIN user (call testLoginUser() here) - user will be prompted to enter email and password from within the method
        System.out.println();
        System.out.println("Log In: ");
        System.out.println("-------------------------------");
        testLoginUser();

        
        // Add health data
        System.out.println("Add Health Data: ");
        System.out.println("-------------------------------");
        System.out.println("Would you like to add new health data? (yes/no)");
        String res = scanner.nextLine();
        System.out.println();

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

/////////////////////RECOMMENDATIONS//////////////////////////

            // Generate RECOMMENDATIONS
            System.out.println();
            System.out.println("System Recommendations: ");
            System.out.println("-------------------------------");
            System.out.println("Would you like the system to generate health recommendations for you? (yes/no): ");
            String response1 = scanner.nextLine();
            if (response1.equalsIgnoreCase("yes")) {
                System.out.println();
                System.out.println("Your weight is: " + hd.getWeight());
                System.out.println("Your height is: " + hd.getHeight());
                System.out.println("Your have " + hd.getSteps() + " steps so far today.");
                System.out.println("Your resting heart rate is: " + hd.getHeartRate());
                System.out.println();

                RecommendationSystem recSystem = new RecommendationSystem();
                List<String> recommendations = recSystem.generateRecommendations(hd);

                System.out.println("Your Recommendations: ");
                System.out.println("-------------------------------");
                for (String recommendation : recommendations) {
                    System.out.println(recommendation);
                    // Add recommendation to the database
                    RecommendationSystemDao.InsertRecommendationData(hd, recommendation);
                }
                System.out.println();
            }
        }

/////////////////////MEDICINE REMINDERS//////////////////////////
        
        
        // Add a MEDICINE REMINDER
        System.out.println();
        System.out.println("Medicine Reminders: ");
        System.out.println("-------------------------------");
        System.out.println("Would you like to add a medicine reminder? (yes/no): ");
        String response2 = scanner.nextLine();
        if (response2.equalsIgnoreCase("yes")) {
            System.out.println("Enter your ID #: ");
            int user_id = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("Enter medicine name: ");
            String medicine_name = scanner.nextLine();
            System.out.println("Enter dosage: ");
            String dosage = scanner.nextLine();
            System.out.println("Enter schedule: ");
            String schedule = scanner.nextLine();
            System.out.println("Enter start date (YYYY-MM-DD): ");
            String start_date = scanner.nextLine();
            System.out.println("Enter end date (YYYY-MM-DD): ");
            String end_date = scanner.nextLine();
            mr = new MedicineReminder(user_id, medicine_name, dosage, schedule, start_date, end_date);

            // Add reminder to the array list to be displayed in the terminal
            mrm.addReminder(mr);
            // Add reminder to the database
            boolean isReminderCreated = MedicineReminderManager.createMedicineReminder(mr);
           
            if (isReminderCreated) {
                System.out.println("Medicine reminder added successfully.");
            } else {
                System.out.println("Failed to add medicine reminder.");
            }
        }


        // Get REMINDERS for a SPECIFIC USER
        System.out.println();
        System.out.println("Would you like to see all of your medicine reminders? (yes/no): ");
        String response3 = scanner.nextLine();

        if (response3.equalsIgnoreCase("yes")) {
            System.out.println("Enter your ID #: ");
            int user_id = scanner.nextInt();
            scanner.nextLine();
            List<MedicineReminder> userReminders = mrm.getRemindersForUser(user_id);
            System.out.println();
            System.out.println("Your Medicine Reminders: ");
            System.out.println("-------------------------------");
            for (MedicineReminder reminder : userReminders) {
                System.out.println("Medicine Name: " + reminder.getMedicineName());
                System.out.println("Dosage: " + reminder.getDosage());
                System.out.println("Schedule: " + reminder.getSchedule());
                System.out.println("Start Date: " + reminder.getStartDate());
                System.out.println("End Date: " + reminder.getEndDate());
                System.out.println();
            }
        }

        // Get REMINDERS for a specific user only for CURRENT MEDICATIONS
        System.out.println();
        System.out.println("Would you like to see your due medicine reminders? (yes/no): ");
        String response4 = scanner.nextLine();

        if (response4.equalsIgnoreCase("yes")) {
            System.out.println("Enter your ID #: ");
            int user_id = scanner.nextInt();
            scanner.nextLine();
            List<MedicineReminder> dueReminders = mrm.getDueReminders(user_id);
            System.out.println();
            System.out.println("Your due/current Medicine Reminders: ");
            System.out.println("-------------------------------");
            for (MedicineReminder reminder : dueReminders) {
                System.out.println("Medicine Name: " + reminder.getMedicineName());
                System.out.println("Dosage: " + reminder.getDosage());
                System.out.println("Schedule: " + reminder.getSchedule());
                System.out.println("Start Date: " + reminder.getStartDate());
                System.out.println("End Date: " + reminder.getEndDate());
                System.out.println();
            }
        }

/////////////////////DOCTOR PORTAL//////////////////////////

        //test DOCTOR PORTAL (call testDoctorPortal() here)
        if (instanceUser.isDoctor()) {
            System.out.println();
            System.out.println("Welcome to the Doctor Portal, Doctor " + instanceUser.getLastName());
            System.out.println("-----------------------------------------------");
            testDoctorPortal();
        }
    }


/////////////////////FUNCTIONS/////////////////////////////

    public static boolean loginUser(String email, String password) {
        //implement method to login user.
        User user = UserDaoExample.getUserByEmail(email);

        if (user != null) {
            // Compare the stored hashed password with the given password and return result
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }


    public static void testDoctorPortal() {
        DoctorPortalDao doctorPortalDao = new DoctorPortalDao();
       
        // Add code to Fetch the doctor by ID
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your Doctor ID #: ");
            int doctorId = scanner.nextInt();
            scanner.nextLine();
            System.out.println("What is your medical license #?: ");
            String medicalLicenseNumber = scanner.nextLine();
            System.out.println("What is your specialization?: ");
            String specialization = scanner.nextLine();
            System.out.println();

            User doctor = doctorPortalDao.getDoctorById(doctorId);
            if (!doctor.isDoctor()) {
                System.out.println("The user with this ID # is not a doctor. Please try again.");
            } else {
                instanceDoctor.setId(doctorId);
                instanceDoctor.setFirstName(doctor.getFirstName());
                instanceDoctor.setLastName(doctor.getLastName());
                instanceDoctor.setEmail(doctor.getEmail());
                instanceDoctor.setPassword(doctor.getPassword());
                instanceDoctor.setDoctor(true);
                instanceDoctor.setMedicalLicenseNumber(medicalLicenseNumber);
                instanceDoctor.setSpecialization(specialization);

                System.out.println("Doctor: " + instanceDoctor.getFirstName() + " " + instanceDoctor.getLastName());
                System.out.println("Medical License #: " + instanceDoctor.getMedicalLicenseNumber());
                System.out.println("Specialization: " + instanceDoctor.getSpecialization());
                System.out.println();
                break;
            }
        }

        // Add code to Fetch patients associated with the doctor
        List<User> patients = doctorPortalDao.getPatientsByDoctorId(instanceDoctor.getId());
        System.out.println("Patients associated with Doctor " + instanceDoctor.getLastName() + ": ");
        System.out.println("-------------------------------");
        for (User patient : patients) {
            System.out.println("Patient ID #: " + patient.getId());
            System.out.println("Patient Name: " + patient.getFirstName() + " " + patient.getLastName());
            System.out.println("Patient Email: " + patient.getEmail());
            System.out.println();
        }
        
        // Add code to Fetch health data for the patient
        System.out.println("Would you like to see health data for a specific patient? (yes/no): ");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Enter patient ID #: ");
            int patientId = scanner.nextInt();
            scanner.nextLine();
            List<HealthData> healthData = doctorPortalDao.getHealthDataByPatientId(patientId);
            System.out.println("Health Data for Patient ID #: " + patientId);
            System.out.println("-------------------------------");
            for (HealthData data : healthData) {
                System.out.println("Weight: " + data.getWeight());
                System.out.println("Height: " + data.getHeight());
                System.out.println("Steps: " + data.getSteps());
                System.out.println("Resting Heart Rate: " + data.getHeartRate());
                System.out.println("Date: " + data.getDate());
                System.out.println();
            }
        }
    }


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
                instanceUser = user1;
                System.out.println();
                break;
            } else {
                System.out.println("Incorrect email or password. Please try again.");
            }
        }      
    }
}
