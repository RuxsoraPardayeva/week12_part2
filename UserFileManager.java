import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserFileManager {
    private static final String FILE_NAME = "users.txt";
    private static final String SEPARATOR = "\\|";
    private static final String WRITE_SEPARATOR = " | ";

    public static boolean registerUser(User user) {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(user.getUsername() + WRITE_SEPARATOR + user.getPassword() + WRITE_SEPARATOR + user.getEmail());
            writer.newLine();
            writer.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User login(String username, String password) {
        Path path = Paths.get(FILE_NAME);

        if (!Files.exists(path)) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);

                if (parts.length == 3) {
                    String savedUsername = parts[0].trim();
                    String savedPassword = parts[1].trim();
                    String savedEmail = parts[2].trim();

                    if (savedUsername.equals(username) && savedPassword.equals(password)) {
                        return new User(savedUsername, savedPassword, savedEmail);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean usernameExists(String username) {
        Path path = Paths.get(FILE_NAME);

        if (!Files.exists(path)) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);

                if (parts.length == 3) {
                    if (parts[0].trim().equals(username)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean emailExists(String email) {
        Path path = Paths.get(FILE_NAME);

        if (!Files.exists(path)) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);

                if (parts.length == 3) {
                    if (parts[2].trim().equals(email)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
