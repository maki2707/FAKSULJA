package Commands;

import Utils.CryptoUtils;
import Utils.FileUtils;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LoginUser
{
    public static void login(String username) throws Exception {

        if(!FileUtils.checkIfUsernameExists(username))
        {
            System.out.println(">>> Account with given username does not exist. Please try again or contact your administrator.");
            System.exit(1);
        }

        int invalidLogins = 0;
        String password;

                while(invalidLogins < 3)
        {
            password = FileUtils.getUserPasswordLogin();

            String oldPasswordHash = FileUtils.getOldPasswordHash(username);

            String oldSaltString = FileUtils.getOldSalt(username);
            byte[] oldSaltBytes = Base64.getDecoder().decode(oldSaltString.getBytes(UTF_8));

            String newPasswordHash = CryptoUtils.generatePasswordHash(password,oldSaltBytes);
            if(!newPasswordHash.equals(oldPasswordHash))
            {
                invalidLogins = invalidLogins + 1;
                System.out.println(">>> Entered username or password incorrect. Please try again.");
            }
            else
            {
                break;
            }
        }
        if(invalidLogins == 3)
        {
            return;
        }

        if(FileUtils.getUserInfo(username).endsWith("1"))
        {
            System.out.println(">>> Administrator ordered you to change your password. Please follow: ");
            String newPassword = FileUtils.getUserPasswordAdd();
            byte[] salt = CryptoUtils.generateSalt(16);
            String hashedPassword = CryptoUtils.generatePasswordHash(newPassword, salt);
            FileUtils.changeUserPassword(Base64.getEncoder().encodeToString(salt), username, hashedPassword);

        }


        System.out.println(">>> Login successful.");
    }
}
