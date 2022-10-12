import Commands.*;

import java.io.IOException;

public class login
{
    public static void main(String[] args) throws IOException {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("The number of arguments should be 2. Number of arguments entered: " + args.length);
            }

            String commandArgument = args[0];
            if ("login".equals(commandArgument)) {
                LoginUser.login(args[1]);
            } else {
                System.out.println("Please enter a valid command.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
