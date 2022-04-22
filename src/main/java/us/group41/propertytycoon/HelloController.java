package us.group41.propertytycoon;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.text.WordUtils;

import java.util.Random;

public class HelloController {
    private final Board playingBoard = new Board();
    public TextField playersTextField;
    public Label notIntLabel;
    public TextField playerPosTextField;
    public Label startGameLabel;
    public Label playerPosLabel;
    public TextField movePlayerDistTextField;
    public TextField movePlayerCurPosTextField;
    public Label movePlayerLabel;
    public TextField rollDiceCurPosTextField;
    public Label rollDiceLabel;
    public Label addPlayerLabel;
    public Button rollDiceButton;
    public ComboBox playerTokenComboBox;
    int playerInt = 0;
    boolean tokenFirstTime = true;
    int players = 0;
    @FXML
    private Label welcomeText;

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onPlayerTokenComboBoxClick() {
        if (tokenFirstTime) {
            playerTokenComboBox.getItems().addAll(Player.Token.values());
            tokenFirstTime = false;
        }
    }

    @FXML
    protected void onAddPlayerButtonClick() {
        Player.Token token = (Player.Token) playerTokenComboBox.getValue();
        if (token == null) {
            addPlayerLabel.setText("Please select a valid token.");
            return;
        }
        playerInt += 1;
        if (playerInt <= 5) {
            if (Board.addPlayer(playerInt, token)) {
                addPlayerLabel.setText("Player" + playerInt + " has been added to the game with the " + WordUtils.capitalizeFully(String.valueOf(token)) + " token. " + playerInt + "/5");
            } else {
                addPlayerLabel.setText("failed to add player");
            }

        } else {
            addPlayerLabel.setText("Maximum of 5 players added. Please start the game.");
        }
    }

    @FXML
    protected void onStartGameButtonClick() {
        if (playerInt > 0) {
            if (playingBoard.startGame()) {
                startGameLabel.setText("Success! Game has started");
            } else {
                startGameLabel.setText("Error. Game has already started with " + players + " players.");
            }
        } else {
            startGameLabel.setText("No players yet. Please add some players.");
        }
    }

    @FXML
    protected int getEnteredPlayers() {
        if (isNumeric(playersTextField.getText())) {
            return Integer.parseInt(playersTextField.getText());
        } else {
            notIntLabel.setText("Not a number, please try again.");
            return 0;
        }
    }

    @FXML
    protected void getPlayersOnPos() {
        int pos = Integer.parseInt(playerPosTextField.getText());
        int playersOnPos = playingBoard.getPlayersOnSquare(pos);
        playerPosLabel.setText("There are " + playersOnPos + " players on the " + pos + " square.");
    }

    @FXML
    protected void movePlayer() {
        if (isNumeric(movePlayerDistTextField.getText())) {
            int distance = Integer.parseInt(movePlayerDistTextField.getText());
            if (isNumeric(movePlayerCurPosTextField.getText())) {
                int curPos = Integer.parseInt(movePlayerCurPosTextField.getText());
                int newPos = curPos + distance;
                if (playingBoard.movePlayer(newPos, curPos)) {
                    movePlayerLabel.setText("Success! Player has been moved to the " + newPos + " square.");
                } else {
                    movePlayerLabel.setText("Error. No players on that square.");
                }
            } else {
                movePlayerLabel.setText("Current Position not a number, please try again.");
            }
        } else {
            movePlayerLabel.setText("Distance not a number, please try again.");
        }
    }

    @FXML
    protected void rollDice() {
        Random random = new Random();
        int[] rolls = random.ints(2, 1, 6).toArray();
        int firstRoll = rolls[0];
        int secondRoll = rolls[1];
        int totalRoll = firstRoll + secondRoll;
        //disable end turn
        //enable end turn button
        rollDiceButton.setDisable(!playingBoard.rolledDice(totalRoll, firstRoll == secondRoll));
        rollDiceLabel.setText("Player has rolled a " + firstRoll + " and a " + secondRoll + " and moved a total of " + totalRoll + " places!");
    }
}