package Utils;

public class PasswordUtils
{

    public static void checkPasswordComplexity(String password)
    {
        if(password.length() < 8)
        {
            System.out.println("The given password is to short. Please enter a password that is at least 8 characters long. " + password.length() );
            System.exit(1);
        }

        if(!password.matches("^.*[0-9].*$"))
        {
            System.out.println("Please enter a password that contains at least one digit.");
            System.exit(1);
        }

        if(!password.matches("^.*[A-Z].*$"))
        {
            System.out.println("Please enter a password that contains at least one uppercase letter.");
            System.exit(1);
        }


        if(!password.matches("^.*[^a-zA-Z0-9].*$"))
        {
            System.out.println("Please enter a password that contains at least one non alphanumeric character.");
            System.exit(1);
        }
    }
}





