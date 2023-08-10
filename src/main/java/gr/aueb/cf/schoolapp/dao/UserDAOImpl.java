package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.service.util.DBUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements IUserDAO {
//    @Override
//    public User getByUsername(String username) {
//        return new User(1, username, "password");
//    }

//    @Override
//    public User findByUsername(String username) throws UserDAOException {
//        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
//        User user = null;
//
//        try (Connection connection = DBUtil.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ResultSet rs;
//            ps.setString(1, username);
//
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                user = new User(rs.getInt("ID"),
//                        rs.getString("USERNAME"), rs.getString("PASSWORD"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return user;
//    }


    @Override
    public boolean isUserValid(String username, String password) {
        String sql = "SELECT * FROM USERS WHERE USERNAME=?";
        String hashed;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs;
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
               hashed = rs.getString("PASSWORD");
               return BCrypt.checkpw(password, hashed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
