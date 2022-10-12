package Utils;

import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import javax.crypto.spec.*;

public class CryptoUtils
{
    /**
     * method for generating SALT or IV, depending on the lenght value
     * @param length    value of this parameter determines the outcome of the method - 16 -> SALT || 12 -> IV
     * @return
     */
    public static byte[] generateSalt(int length) {
        byte[] nonce = new byte[length];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * method for hashing a given password string with given salt
     * @param password
     * @param salt
     * @return hashed password as byte array
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String generatePasswordHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] charPassword = password.toCharArray();

        PBEKeySpec spec = new PBEKeySpec(charPassword, salt,50000 , 256);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashedPassword = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hashedPassword);

    }
}
