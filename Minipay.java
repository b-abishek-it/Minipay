import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Minipay extends Application {

    private double balance = 0.0;
    private Label balanceLabel;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mini Banking App");

        
        Image introImage = new Image("intro.PNG");
        ImageView introImageView = new ImageView(introImage);

        StackPane introPane = new StackPane();
        introPane.getChildren().add(introImageView);
        Scene introScene = new Scene(introPane, 750, 600);

        
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10));
        loginGrid.setVgap(5);
        loginGrid.setHgap(5);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        GridPane.setConstraints(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        Label errorLabel = new Label();
        GridPane.setConstraints(errorLabel, 1, 3);

        loginGrid.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, errorLabel);
        Scene loginScene = new Scene(loginGrid, 750, 600);
        primaryStage.setScene(introScene);
        primaryStage.show();

        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            primaryStage.setScene(loginScene);
        }));
        timeline.play();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (isValidUser(username, password)) {
                switchToBankingScene(primaryStage);
            } else {
                errorLabel.setText("Invalid username or password");
            }
        });
    }

    private boolean isValidUser(String username, String password) {
        
        return username.equals("admin") && password.equals("password");
    }

    private void switchToBankingScene(Stage primaryStage) {
        
        GridPane bankingGrid = new GridPane();
        bankingGrid.setPadding(new Insets(10));
        bankingGrid.setVgap(5);
        bankingGrid.setHgap(5);

        balanceLabel = new Label("Balance: ₹" + balance);
        GridPane.setConstraints(balanceLabel, 0, 0);

        TextField depositField = new TextField();
        depositField.setPromptText("Enter deposit amount");
        GridPane.setConstraints(depositField, 0, 1);

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(e -> deposit(Double.parseDouble(depositField.getText())));
        GridPane.setConstraints(depositButton, 1, 1);

        TextField withdrawalField = new TextField();
        withdrawalField.setPromptText("Enter withdrawal amount");
        GridPane.setConstraints(withdrawalField, 0, 2);

        Button withdrawalButton = new Button("Withdraw");
        withdrawalButton.setOnAction(e -> withdraw(Double.parseDouble(withdrawalField.getText())));
        GridPane.setConstraints(withdrawalButton, 1, 2);

        bankingGrid.getChildren().addAll(balanceLabel, depositField, depositButton, withdrawalField, withdrawalButton);
        Scene bankingScene = new Scene(bankingGrid, 750, 600);
        primaryStage.setScene(bankingScene);
    }

    private void deposit(double amount) {
        balance += amount;
        updateBalanceLabel();
    }

    private void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            updateBalanceLabel();
        } else {
          
            System.out.println("Insufficient funds");
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: ₹" + balance);
    }

    public static void main(String[] args) {
        launch(args);
    }
}