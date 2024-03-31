import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDao {
    public boolean createUser(User user) {
        // insert user into database 
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Prepare the SQL query
        String query = "INSERT INTO users (first_name, last_name, email, password, is_doctor) VALUES (?, ?, ?, ?, ?)";

         // Database logic to insert data using PREPARED Statement
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, user.isDoctor());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }  
    }

    public User getUserById(int id) { //get user by id from database 
        User user = null;

        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE id = ?";

        // Database logic to get data by ID Using Prepared Statement
        try (Connection connection = DatabaseConnection.getCon();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("is_doctor")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByEmail(String email) { // get user by email from database 
        User user = null;

        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE email = ?";

        // Database logic to get data by ID Using Prepared Statement
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("is_doctor")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public boolean updateUser(User user) {
        // Prepare the SQL query
        String query = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, is_doctor = ? WHERE id = ?";
        // Database logic to get update user Using Prepared Statement
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, user.isDoctor());
            statement.setInt(6, user.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id) { // delete user from the database 
        // Prepare the SQL query
        String query = "DELETE FROM users WHERE id = ?";
        // Database logic to delete user
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyPassword(String email, String password) {
        // Prepare the SQL query
        String query = "SELECT password FROM users WHERE email = ?";
        //Implement logic to retrieve password using the Bcrypt
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
