package managerCommands;

import utils.CryptoUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class ManagerGet
{
    public static void get(String masterPassword, String domain)
    {
        try(BufferedReader br = new BufferedReader(new FileReader("pswmng.txt")))
        {
            String passwordFromFile = "";
            String line = br.readLine();
            while(line != null)
            {
                String pair = CryptoUtils.getPairDecrypted(masterPassword,line.trim());
                String domainFromFile = pair.trim().split("đ")[1];
                if(domainFromFile.equals(domain))
                {
                    passwordFromFile = pair.trim().split("đ")[0];
                    break;
                }

                line = br.readLine();
            }

            if(passwordFromFile.equals(""))
            {
                System.out.println(">>> No password found for the given domain -> ||" + domain + "|| !");
            }
            else
            {
                System.out.println(">>> The password for the domain -> ||"+domain+"|| is -> ||" + passwordFromFile +"||" );
            }
        }
        catch (IOException e)
        {
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
    }
}
