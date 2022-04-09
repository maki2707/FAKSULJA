import managerCommands.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class PasswordManager
{
    public static boolean managerInitialized = false;

    public static void main(String[] args)
    {
        try
        {
         if (args.length < 1 || args.length > 4)
         {
             throw new IllegalArgumentException("The number of arguments should be 3 for INIT or GET and 4 for PUT command. Number of arguments entered: " + args.length);
         }

         checkIfManagerInitialized();
         String commandArgument = args[0];
         switch (commandArgument) {
             case "init" ->
                     {
                         if (args.length > 2)
                         {
                             throw new IllegalArgumentException("The number of arguments should be 2 for INITIALIZATON. Number of arguments entered: " + args.length);
                         }
                         ManagerInitialization.initialize();
                     }

             case "put" ->
                     {
                         if (!managerInitialized)
                         {
                             throw new IllegalArgumentException("Password cannot be stored because the password manager is yet to be initialized. Use * init yourMasterPassword * to initialize the password manager.");
                         }
                         if(args.length != 4)
                         {
                             throw new IllegalArgumentException("Password cannot be stored because given number of arguments needs to be 4. Use command * help * to see examples of usage.");
                         }
                         ManagerPut.put(args[1],args[2],args[3]);
                     }

             case "get" ->
                     {
                         if (!managerInitialized) {
                             throw new IllegalArgumentException("Password cannot be retrieved because the password manager is yet to be initialized. Use * init yourMasterPassword * to initialize the password manager.");
                         }
                         if (args.length != 3) {
                             throw new IllegalArgumentException("Password cannot be retrieved because given number of arguments needs to be 3. Use command * help * to see examples of usage.");
                         }
                         ManagerGet.get(args[1],args[2]);
                     }


             default -> System.out.println("Please enter a valid command.");
         }

        } catch (IllegalArgumentException | IOException e) {
            System.err.println(e.getMessage());
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

    private static void checkIfManagerInitialized()
    {
        File f = new File("pswmng.txt");
        if(f.exists() && f.isFile())
        {
            managerInitialized = true;
        }

    }
}
