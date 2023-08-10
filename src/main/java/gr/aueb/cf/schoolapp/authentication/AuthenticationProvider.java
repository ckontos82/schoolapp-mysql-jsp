package gr.aueb.cf.schoolapp.authentication;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.dto.UserLoginDTO;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationProvider {

    private static final IUserDAO userDAO = new UserDAOImpl();

    private AuthenticationProvider() {}

    public static boolean authenticate(UserLoginDTO userLoginDTO) {
        try {
            String username = userLoginDTO.getUsername();
            String password = userLoginDTO.getPassword();

            //System.out.println("authenticate" + userLoginDTO.getUsername());
//        if (userLoginDTO.getUsername().equals("test@aueb.gr")) {
//            return false;
//        }
            if (userDAO.findByUsername(username) != null) {
                return BCrypt.checkpw(password, userDAO.findByUsername(username).getPassword());
            } else return false;
        } catch (UserDAOException | NullPointerException e) {
            return false;
        }
    }
}
