import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showLoginScene();
    }

    private void showLoginScene() {
        Label titleLabel = new Label("Login");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label messageLabel = new Label();

        Button loginButton = new Button("Log In");
        Button registerButton = new Button("Register");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            User user = UserFileManager.login(username, password);

            if (user != null) {
                showWelcomeScene(user.getUsername());
            } else {
                messageLabel.setText("Incorrect username or password.");
            }
        });

        registerButton.setOnAction(e -> showRegisterScene());

        VBox layout = new VBox(10, titleLabel, usernameField, passwordField, loginButton, registerButton, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 350, 300);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showRegisterScene() {
        Label titleLabel = new Label("Register");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        Label messageLabel = new Label();

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");

        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("No field may be empty.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("Passwords do not match.");
                return;
            }

            if (UserFileManager.usernameExists(username)) {
                messageLabel.setText("Username already exists.");
                return;
            }

            if (UserFileManager.emailExists(email)) {
                messageLabel.setText("Email already exists.");
                return;
            }

            User newUser = new User(username, password, email);
            boolean saved = UserFileManager.registerUser(newUser);

            if (saved) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Registration successful!");
                alert.showAndWait();

                showLoginScene();
            } else {
                messageLabel.setText("Error saving user.");
            }
        });

        backButton.setOnAction(e -> showLoginScene());

        VBox layout = new VBox(10,
                titleLabel,
                usernameField,
                emailField,
                passwordField,
                confirmPasswordField,
                registerButton,
                backButton,
                messageLabel
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("Register");
        primaryStage.setScene(scene);
    }

    private void showWelcomeScene(String username) {
        Label welcomeLabel = new Label("Welcome, " + username + "!");
        Button logoutButton = new Button("Log Out");

        logoutButton.setOnAction(e -> showLoginScene());

        VBox layout = new VBox(20, welcomeLabel, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 350, 250);
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
