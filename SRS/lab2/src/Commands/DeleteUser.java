package Commands;

import Utils.FileUtils;

import java.io.*;

public class DeleteUser
{
    public static void deleteUser(String username) throws IOException
    {
        if(!FileUtils.checkIfUsernameExists(username))
        {
            System.out.println(">>> Account with given username does not exist. Please try again.");
            System.exit(1);
        }
        FileUtils.deleteUserFromFile(username);
        System.out.println("User: " + username + " successfully deleted.");
    }
}
