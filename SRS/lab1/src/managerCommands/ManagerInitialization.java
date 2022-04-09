package managerCommands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ManagerInitialization
{
    public static void initialize() throws IOException {
        Scanner sc = new Scanner(System.in);
        File f = new File("pswmng.txt");

        if(f.exists() && f.isFile())
        {
            System.out.print(">>> Password manager has already been initialized and the created. Do you wish to overwrite the old password manager and create a new one? Enter (Y/N)");
            String input = sc.nextLine().trim().toLowerCase();
            switch (input)
            {
                case "y" ->
                        {
                            createNewManager("pswmng.txt");
                        }
                case "n" ->
                        {
                            System.out.println(">>> Existing password manager won't be overwritten.");
                            break;
                        }

                default ->
                        {
                            System.out.println(">>> Invalid input. Please enter * Y/y *  or * N/n *");
                        }
            }
        }
        else
        {
            createNewManager("pswmng.txt");
        }



    }

    private static void createNewManager(String path) throws IOException {
        try
        {
            new FileWriter(path);
            System.out.print(">>> New password manager has been initialized.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
