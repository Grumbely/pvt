package se.antongomes.pvt.service;

import se.antongomes.pvt.data.model.User;

/**
 * Service layer class for User entity.
 * Encapsulates UserRepository.
 * @see User
 */
public interface UserService {
    public class ReservedUsernameException extends Exception {
        private static final long serialVersionUID = -5486805587633037627L;
    }
    public class DuplicateEMailException extends Exception {
        private static final long serialVersionUID = -709467804322475770L;
    }

    /**
     * Find a user by their username.
     */
    public User findUserByName(String username);
//    public User findByID(int id);

    /**
     * Get the number of user objects in the database.
     */
    public long userCount();

    /**
     * Try to find a user with the specified username and password.
     * @return If successful, returns the User object, otherwise returns null.
     */
    public User attemptLogin(String username, String password);

    public User createUserAccount(String username, String password, String email) throws ReservedUsernameException, DuplicateEMailException;
    public void LostPassword(User player);
}
