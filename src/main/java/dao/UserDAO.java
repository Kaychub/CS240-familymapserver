package dao;

import model.User;

import java.sql.*;

/**
 * Accesses data about users from the database.
 */
public class UserDAO {
    /**
     * The connection to the database.
     */
    private final Connection conn;

    /**
     * Creates a user DAO.
     * @param conn the connection to the database.
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds a user to the database.
     * @param user the user to add to the database.
     * @throws DataAccessException Error accessing data
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO Users (Username, Password, Email, FirstName, LastName, Gender, PersonID) " +
                "VALUES (?,?,?,?,?,?,?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting user");
        }
    }

    /**
     * Validates that the username and password go together and are in the database.
     * @param username the username of the user.
     * @param password the password of the user.
     * @return whether the user's username and password match those in the database.
     * @throws DataAccessException Error accessing data
     */
    public boolean validate(String username, String password) throws DataAccessException {
        String sql = "SELECT Password, PersonID FROM Users WHERE Username = ?;";

        ResultSet rs = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();
            if (rs.next()) {
                if(password.equals(rs.getString("Password"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while validating user");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Finds a specified user in the database.
     * @param username the username of the user.
     * @return the user matching the username
     * @throws DataAccessException Error accessing data
     */
    public User find(String username) throws DataAccessException {
        String sql = "SELECT * FROM Users WHERE Username = ?;";
        ResultSet rs = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("Username"), rs.getString("Password"),
                                rs.getString("Email"), rs.getString("FirstName"),
                                rs.getString("LastName"), rs.getString("Gender"),
                                rs.getString("PersonID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * Removes a specified user from the database.
     * @param username the username of the user to remove
     * @throws DataAccessException Error accessing data
     */
    public void delete(String username) throws DataAccessException {
        String sql = "DELETE FROM Users WHERE Username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting User");
        }
    }

    /**
     * Removes all users from the database.
     * @throws DataAccessException Error accessing data
     */
    public void clearTable() throws DataAccessException {
        try (Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM Users";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while deleting Users table");
        }
    }

}
