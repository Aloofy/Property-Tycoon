package us.group41.propertytycoon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    public TextField playersTextField;
    public Label notIntLabel;
    public TextField playerPosTextField;
    public Label startGameLabel;
    public Label playerPosLabel;
    public TextField movePlayerDistTextField;
    public TextField movePlayerCurPosTextField;
    public Label movePlayerLabel;
    @FXML
    private Label welcomeText;
    private final Board playingBoard = new Board();

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(strNum);
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
    protected void onStartGameButtonClick() {

        int players = getEnteredPlayers();
        if (players > 0) {
            if (playingBoard.startGame(players)) {
                startGameLabel.setText("Success! " + players + " players have been added.");
            } else {
                startGameLabel.setText("Error. Game has already started with " + players + " players.");
            }
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
}