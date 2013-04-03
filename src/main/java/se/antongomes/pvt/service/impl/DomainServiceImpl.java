package se.antongomes.pvt.service.impl;

import org.springframework.stereotype.Service;
import se.antongomes.pvt.service.DomainService;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@Service
public class DomainServiceImpl implements DomainService {
    private class InvalidApplicationPropertyException extends RuntimeException {
        private static final long serialVersionUID = 4810965507632397993L;

        private InvalidApplicationPropertyException(String message) {
            super(message);
        }
    }


    private static String salt;

    @Override
    public long GetResetPasswordTimeLimit() {
        return 24 * 60 * 60 * 1000; //One day in milliseconds
    }

    @Override
    public String EncryptPassword(String password) {
        return EncryptString(password + GetSalt());
    }

    private String EncryptString(String string) {
        String hash = null;
        try {
            byte[] stringBytes = string.getBytes("UTF-8");
            MessageDigest encryptionAlgorithm = MessageDigest.getInstance("SHA-256");
//            hash = new String(sha256.digest(stringBytes), "UTF-8");
            byte[] messageDigest = encryptionAlgorithm.digest(stringBytes);
            StringBuilder hashString = new StringBuilder(messageDigest.length*2);
            for (byte hashByte : messageDigest) {
                String hexadecimal = Integer.toHexString(0xFF & hashByte);
                if (hexadecimal.length() == 1) //Integer.toHexString removes trailing zeroes
                    hashString.append('0');
                hashString.append(hexadecimal);
            }
            hash = hashString.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    private String GetApplicationProperty(String property) {
        Properties applicationProperties = new Properties();
        InputStream applicationPropertiesStream = getClass().getResourceAsStream(applicationPropertiesResource);
        try {
            applicationProperties.load(applicationPropertiesStream);
            return applicationProperties.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                applicationPropertiesStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //TODO: Great, we're stuck with an InputStream that won't close... Now what?
            }
        }
        throw new InvalidApplicationPropertyException("Either the application property '"+property+"' was not found or the property file itself is missing.");
    }

    private String GetSalt() {
        if (salt == null)
            salt = GetApplicationProperty("pvt.passwordSalt");
        return salt;
    }
}
