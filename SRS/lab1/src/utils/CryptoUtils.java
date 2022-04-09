package utils;

import javax.crypto.*;
import java.io.File;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import javax.crypto.spec.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CryptoUtils
{
    private static final int IV_LENGTH = 12;
    private static final int SALT_LENGTH = 16;

    /**
     * method for generating SALT or IV, depending on the lenght value
     * @param length    value of this parameter determines the outcome of the method - 16 -> SALT || 12 -> IV
     * @return
     */
    public static byte[] generateSaltOrIV(int length) {
        byte[] nonce = new byte[length];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * method for generating the key from the given master password
     * @param masterPass    master password chosen by user
     * @param salt          generated with method @generateSaltOrIV
     * @return              returns the AESKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey getAESKeyFromPassword(char[] masterPass, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(masterPass, salt, 50000, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    /**
     *
     * @param plaintext
     * @param masterPassword
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     */
    public static String encrypt(byte[] plaintext,String masterPassword) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        byte[] salt = CryptoUtils.generateSaltOrIV(SALT_LENGTH);
        byte[] IV = CryptoUtils.generateSaltOrIV(IV_LENGTH);
        SecretKey aesKey = CryptoUtils.getAESKeyFromPassword(masterPassword.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, new GCMParameterSpec(128,IV));

        byte[] cipherText_ENCRYPTED = cipher.doFinal(plaintext);
        byte[] cipherText_ENCYRPTEDWithIvAndSalt = ByteBuffer.allocate(IV.length + salt.length + cipherText_ENCRYPTED.length)
                .put(IV)
                .put(salt)
                .put(cipherText_ENCRYPTED)
                .array();

        return Base64.getEncoder().encodeToString(cipherText_ENCYRPTEDWithIvAndSalt);
    }

    public static String decrypt(String cryptedText, String masterPassword) throws IllegalArgumentException, NoSuchPaddingException, NoSuchAlgorithmException {

        byte[] toDecrypt = null;
        try{
            toDecrypt = Base64.getDecoder().decode(cryptedText.getBytes(UTF_8));
        }catch (IllegalArgumentException e )
        {
            System.out.println(">>> Master password incorrect or integrity check failed.");
            System.exit(1);
        }

        ByteBuffer bb = ByteBuffer.wrap(toDecrypt);
        byte[] IV = new byte[IV_LENGTH];
        bb.get(IV);

        byte[] salt = new byte[SALT_LENGTH];
        bb.get(salt);

        byte[] cipherText_ENCYRPTED = new byte[bb.remaining()];
        bb.get(cipherText_ENCYRPTED);

        SecretKey aesKey = null;
        try {
            aesKey = CryptoUtils.getAESKeyFromPassword(masterPassword.toCharArray(), salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        try {
            cipher.init(Cipher.DECRYPT_MODE, aesKey, new GCMParameterSpec(128, IV));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        byte[] plainText = new byte[0];
        try {
            plainText = cipher.doFinal(cipherText_ENCYRPTED);
        } catch (IllegalBlockSizeException e) {
            System.out.println(">>> Master password incorrect or integrity check failed.");
            System.exit(1);
        } catch (BadPaddingException e) {
            System.out.println(">>> Master password incorrect or integrity check failed.");
            System.exit(1);
        }
        return new String(plainText, UTF_8);
    }

    public static String getPairDecrypted(String masterPassword, String line) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String decryptedText = null;
        try {
            decryptedText = CryptoUtils.decrypt(line, masterPassword);
        } catch (Exception e) {
            System.out.println(">>> Master password incorrect or integrity check failed.");
            System.exit(1);
        }

        return decryptedText;
    }

    public static void deleteFile(String path)
    {
        File file = new File(path);
        file.delete();
    }

}
