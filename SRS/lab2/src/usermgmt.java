import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import Commands.*;


public class usermgmt
{
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("The number of arguments should be 2. Number of arguments entered: " + args.length);
            }
            checkIfFileInitialized();
            String commandArgument = args[0];
            switch (commandArgument) {
                case ("add") -> AddUser.add(args[1]);
                case ("passwd") -> ChangePassword.changePass(args[1]);
                case ("forcepass") -> ForcePasswordChange.force(args[1]);
                case ("del") -> DeleteUser.deleteUser(args[1]);
                default -> System.out.println("Please enter a valid command.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        private static void checkIfFileInitialized() throws IOException
        {
            File f = new File("passauth.txt");
            if(!(f.exists() && f.isFile()))
            {
                new FileWriter("passauth.txt");
            }
        }
}

