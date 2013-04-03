package se.antongomes.pvt.service.impl;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import se.antongomes.pvt.data.model.User;
import se.antongomes.pvt.data.repository.UserRepository;
import se.antongomes.pvt.service.DomainService;
import se.antongomes.pvt.service.UserService;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Implementation of service layer class for Player entity.
 * @see UserService
 */
@Service
@Repository
public class UserServiceImpl implements UserService {
    @SuppressWarnings("SpringJavaAutowiringInspection") //TODO - Make sure the beans in applicationContext.xml and applicationContext-jpa.xml don't overlap
    @Resource
    private UserRepository userRepo;

    @Resource
    private DomainService domainService;

    @Override
    public User findUserByName(String username) {
        return userRepo.findByName(username);
    }

    @Override
    public long userCount() {
        return userRepo.count();
    }

    @Override
    public User attemptLogin(String username, String password) {
        return userRepo.findByNameAndPasswordHash(username, domainService.EncryptPassword(password));
    }

    @Override
    public User createUserAccount(String username, String password, String email) throws ReservedUsernameException, DuplicateEMailException {
        if (userRepo.findByName(username) != null)
            throw new ReservedUsernameException();
        if (userRepo.findByEmail(email) != null)
            throw new DuplicateEMailException();

        User user = new User();
        user.setName(username);
        user.setPasswordHash(domainService.EncryptPassword(password));
        user.setEmail(email);
        user.setAccountState("EMAIL_NOT_VERIFIED");

        user = userRepo.save(user); //get the database's changes to the entity
        return user;
    }

    @Override
    public void LostPassword(User user) {
        user.setResetPasswordHash(GenerateResetPasswordHash());
        user.setResetPasswordExpiration(GetResetPasswordGrace());

        //Reset password request flood protection
        if (user.getAccountState() != "EMAIL_NOT_VERIFIED"
                && (user.getResetPasswordExpiration() == null
                || user.getResetPasswordExpiration().before(new Date()))) {
            //TODO - send out an email!
            //TODO - (internationalized?) email templates (Wicket?)
        }
        userRepo.save(user);
    }

    private String GenerateEmailVerificationHash() {
        return GenerateRandomHash(User.EMAIL_VERIFICATION_HASH_STRENGTH);
    }
    private String GenerateResetPasswordHash() {
        return GenerateRandomHash(User.RESET_PASSWORD_HASH_STRENGTH);
    }

    private String GenerateRandomHash(int numHexDigits) {
        return new BigInteger(numHexDigits*4, new SecureRandom()).toString(16);
    }

    private Date GetResetPasswordGrace() {
        DateTime now = new DateTime();
        return now.plus(domainService.GetResetPasswordTimeLimit()).toDate();
    }
}
