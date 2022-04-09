package managerCommands;

import utils.CryptoUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class ManagerPut
{
    public static void put(String masterPassword, String password, String domain) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {

        String passwordDomainPair = password + "đ" + domain;
        String encryptedTextBase64ed = CryptoUtils.encrypt(passwordDomainPair.getBytes(StandardCharsets.UTF_8),masterPassword);
        addToFile(encryptedTextBase64ed, masterPassword, domain);
    }

    private static void addToFile(String line, String masterPassword, String domain)
    {
        String domainToBeOverwritten = null;

        try (BufferedReader br = new BufferedReader(new FileReader("pswmng.txt"))) {
            String l = br.readLine();
            while(l != null)
            {
                String pair = CryptoUtils.getPairDecrypted(masterPassword, l);
                if(pair.split("đ")[1].equals(domain))
                {
                    domainToBeOverwritten = l;
                    break;
                }
                l = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        boolean overWriteFile = true;
        String forWriting = null;

        if(domainToBeOverwritten == null)
        {
            forWriting = line + System.getProperty( "line.separator" );
        }
        else
        {
            forWriting = overWriteLine(domainToBeOverwritten,line);
            overWriteFile = false;
        }

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter("pswmng.txt", overWriteFile))) {
            myWriter.write(forWriting);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String overWriteLine(String oldLine, String newLine)
    {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br2 = new BufferedReader(new FileReader("pswmng.txt"))) {

            String s = br2.readLine();
            while(s != null)
            {
                if(s.equals(oldLine))
                {
                    sb.append(newLine);
                    sb.append(System.getProperty( "line.separator" ));
                }
                else
                {
                    sb.append(s);
                    sb.append(System.getProperty( "line.separator" ));
                }
                s = br2.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

}
