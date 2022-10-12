package Commands;

import Utils.CryptoUtils;
import Utils.FileUtils;

import java.util.Base64;

public class ChangePassword
{
    public static void changePass(String username) throws Exception
    {
        if(!FileUtils.checkIfUsernameExists(username))
        {
            System.out.println(">>> The given username does not yet exist. Please try again.");
            System.exit(1);
        }
        String userPassword = FileUtils.getUserPasswordAdd();
        byte[] salt = CryptoUtils.generateSalt(16);
        String hashedPassword = CryptoUtils.generatePasswordHash(userPassword, salt);
        FileUtils.changeUserPassword(Base64.getEncoder().encodeToString(salt), username, hashedPassword);
        System.out.println(">>> Password for user: " + username + " has been changed.");
    }
}
