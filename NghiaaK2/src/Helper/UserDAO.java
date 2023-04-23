package Helper;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        connection = DBConnection.getConnection();
    }

    public Vector<User> getUsers() {
        Vector<User> users = new Vector<>();
        try {
            String sql = "SELECT username, password FROM usertbl";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString(1), PasswordEncryption.decrypt(resultSet.getString(2)));
                users.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public User login(User user) {
        try {
            String sql = "SELECT username, password, userRole FROM usertbl WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && user.getPassword().equals(PasswordEncryption.decrypt(resultSet.getString(2)))) {
                user.setRole(resultSet.getString(3));
                return user;
            } else return null;
//            return resultSet.next() && user.getPassword().equals(PasswordEncryption.decrypt(resultSet.getString(1)));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public boolean signUp(User user) {
        try {
            String sql = "INSERT INTO usertbl (username,password) VALUE (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEncryptedPassword());
            statement.execute();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
