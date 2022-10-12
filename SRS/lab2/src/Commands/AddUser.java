package Commands;

import Utils.CryptoUtils;
import Utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.Base64;

public class AddUser
{
    public static void add(String userName) throws Exception
    {
        Boolean alreadyExists = FileUtils.checkIfUsernameExists(userName);
        if(alreadyExists)
        {
            System.out.println("You have entered a username that is already in use. Please try again.");
            System.exit(1);
        }

        String userPassword = FileUtils.getUserPasswordAdd();
        byte[] salt = CryptoUtils.generateSalt(16);
        String hashedPassword = CryptoUtils.generatePasswordHash(userPassword, salt);

        FileUtils.addNewUser(Base64.getEncoder().encodeToString(salt), userName, hashedPassword);
    }
}
