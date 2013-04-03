package se.antongomes.pvt.data.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_")
public class User {
    public static final int EMAIL_VERIFICATION_HASH_STRENGTH = 64;
    public static final int RESET_PASSWORD_HASH_STRENGTH     = 64;

    @Id
    @Column(name = "name", nullable = false, length = 31)
    private String name;

    @Column(name = "passwordHash", nullable = false, length = 64, columnDefinition = "char(64)")
    private String passwordHash;

    @Column(name = "emailVerificationHash", nullable = true, length = EMAIL_VERIFICATION_HASH_STRENGTH, columnDefinition = "char(64)")
    private String emailVerificationHash;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "resetPasswordHash", nullable = true, length = RESET_PASSWORD_HASH_STRENGTH, columnDefinition = "char(64)")
    private String resetPasswordHash;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "resetPasswordExpiration", nullable = true)
    private Date resetPasswordExpiration;

    @Enumerated(EnumType.STRING)
    @Column(name="accountState", nullable = false)
    private String accountState;

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmailVerificationHash() {
        return emailVerificationHash;
    }

    public void setEmailVerificationHash(String emailVerificationHash) {
        this.emailVerificationHash = emailVerificationHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetPasswordHash() {
        return resetPasswordHash;
    }

    public void setResetPasswordHash(String resetPasswordHash) {
        this.resetPasswordHash = resetPasswordHash;
    }

    public Date getResetPasswordExpiration() {
        return resetPasswordExpiration;
    }

    public void setResetPasswordExpiration(Date resetPasswordExpiration) {
        this.resetPasswordExpiration = resetPasswordExpiration;
    }

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }
}
