package Utils;

import java.io.*;


public class FileUtils
{
    /**
     * methods goes through the given file and looks for the target username
     * @param userName
     * @return return boolean value, true if given username is found, false if it is not present
     */
    public static Boolean checkIfUsernameExists(String userName) {
        try(BufferedReader br = new BufferedReader(new FileReader("passauth.txt")))
        {
            String line = br.readLine();
            while(line != null)
            {
                String oldUserName = line.split("đ")[0];
                if(oldUserName.equals(userName))
                {
                   return true;
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method used for enabling password input
     * @return
     * @throws Exception
     */
    public static String getUserPasswordAdd() throws Exception {
        Console console = System.console();
        if (console == null) try {
            throw new Exception(">>> No system console available.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String password = String.valueOf(console.readPassword(">>> Enter password: "));

        PasswordUtils.checkPasswordComplexity(password);
        String repeatedPassword =  String.valueOf(console.readPassword(">>> Repeat password: "));

        if(!password.equals(repeatedPassword))
        {
            System.out.println(">>> Entered passwords do not match. Try again.");
            System.exit(1);
        }
        return password;
    }

    /**
     * method that adds new user line to the file
     * @param salt
     * @param username
     * @param hashedPassword
     */
    public static void addNewUser(String salt, String username, String hashedPassword) {
        String nLine = username + "đ" + hashedPassword + salt + "0" + System.lineSeparator();
        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter("passauth.txt",true))) {
            myWriter.write(nLine);
            System.out.println(">>> User: " + username + " added successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method used for changing user password because user wanted a new one
     * @param salt
     * @param username
     * @param hashedPassword
     *
     * @throws FileNotFoundException
     */
    public static void changeUserPassword(String salt, String username, String hashedPassword) throws FileNotFoundException
    {
        StringBuilder newFileContent = new StringBuilder();
        String nLine = username + "đ" + hashedPassword + salt + "0" + System.lineSeparator();
        try (BufferedReader myReader = new BufferedReader(new FileReader("passauth.txt")))
        {
            String line = myReader.readLine();
            while (line != null) {
                String oldUserName = line.split("đ")[0];
                if (!oldUserName.equals(username))
                {
                    newFileContent.append(line).append(System.lineSeparator());
                }
                else
                {
                    newFileContent.append(nLine);
                }
                line = myReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter("passauth.txt", false)))
        {
            myWriter.write(newFileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method deletes user row from a file
     * @param userName
     * @throws IOException
     */
    public static void deleteUserFromFile(String userName) throws IOException
    {
        StringBuilder newFileContent = new StringBuilder();

        try (BufferedReader myReader = new BufferedReader(new FileReader("passauth.txt")))
        {
            String line = myReader.readLine();
            while (line != null) {
                String oldUserName = line.split("đ")[0];
                if (!oldUserName.equals(userName)) {
                    newFileContent.append(line).append(System.lineSeparator());
                }
                line = myReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter("passauth.txt", false)))
        {
            myWriter.write(newFileContent.toString());
        }
    }

    /**
     * adds a flag for changing user password
     * @param username
     */
    public static void forcePasswordChange(String username)
    {
        StringBuilder newFileContent = new StringBuilder();
        String nLine = getUserInfo(username);
        nLine = nLine.substring(0, nLine.length() - 1);
        nLine = nLine + "1";

        try (BufferedReader myReader = new BufferedReader(new FileReader("passauth.txt")))
        {
            String line = myReader.readLine();
            while (line != null) {
                String oldUserName = line.split("đ")[0];
                if (!oldUserName.equals(username))
                {
                    newFileContent.append(line).append(System.lineSeparator());
                }
                else
                {
                    newFileContent.append(nLine).append(System.lineSeparator());
                }
                line = myReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter myWriter = new BufferedWriter(new FileWriter("passauth.txt", false)))
        {
            myWriter.write(newFileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * method that enables passworod input during login process
     * @return
     */
    public static String getUserPasswordLogin()
    {
        Console console = System.console();
        if (console == null) try {
            throw new Exception(">>> No system console available.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(console.readPassword(">>> Enter password: "));
    }

    /**
     * method return the row that starts with given username
     * @param username
     * @return
     */
    public static String getUserInfo(String username)
    {
        String userInfo = "";
        try (BufferedReader myReader = new BufferedReader(new FileReader("passauth.txt")))
        {
            String line = myReader.readLine();
            while (line != null) {
                String oldUserName = line.split("đ")[0];
                if (oldUserName.equals(username)) {
                    userInfo = line;
                }
                line = myReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    /**
     * return hashed password from file
     * @param username
     * @return
     */
    public static String getOldPasswordHash(String username)
    {
        String fileLine = getUserInfo(username);
        return fileLine.split("đ")[1].substring(0,44);
    }

    /**
     * return used salt
     * @param username
     * @return
     */
    public static String getOldSalt(String username)
    {
        String fileLine = getUserInfo(username);
        return fileLine.split("đ")[1].substring(44,fileLine.split("đ")[1].length()-1);
    }


}
