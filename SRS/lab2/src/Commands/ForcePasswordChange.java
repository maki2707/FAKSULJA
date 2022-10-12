package Commands;

import Utils.FileUtils;

public class ForcePasswordChange
{
    public static void force(String username)
    {
        if(!FileUtils.checkIfUsernameExists(username))
        {
            System.out.println(">>> Account with given username does not exist. Please try again.");
            System.exit(1);
        }

        FileUtils.forcePasswordChange(username);
        System.out.println(">>> Account with username: " + username + " will be asked to change password during their next login.");
    }
}
