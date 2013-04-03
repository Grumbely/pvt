package se.antongomes.pvt.service;

public interface DomainService {
    public final static String applicationPropertiesResource = "/application.properties";

    public long GetResetPasswordTimeLimit();
    public String EncryptPassword(String password);
}
