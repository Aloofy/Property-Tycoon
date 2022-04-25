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
    public Button endTurnButton;
    public ComboBox playerTokenComboBox;
    public Button buyPropertyButton;
    int playerInt = 0;
    boolean tokenFirstTime = true;
    //int players = 0;
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
    protected void onEndTurnButtonClick() {
        endTurnButton.setDisable(true);
        rollDiceButton.setDisable(false);
        Board.endTurn();
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
            if (playingBoard.startGame(playingBoard)) {
                startGameLabel.setText("Success! Game has started");
            } else {
                startGameLabel.setText("Error. Game has already started with " + playingBoard.getNumPlayers() + " players.");
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
        int playersOnPos = 0; //playingBoard.getPlayersOnSquare(pos);
        playerPosLabel.setText("There are " + playersOnPos + " players on the " + pos + " square.");
    }

    @FXML
    protected void movePlayer() {
        if (isNumeric(movePlayerDistTextField.getText())) {
            int distance = Integer.parseInt(movePlayerDistTextField.getText());
            if (isNumeric(movePlayerCurPosTextField.getText())) {
                int curPos = Integer.parseInt(movePlayerCurPosTextField.getText());
                int newPos = curPos + distance;
                //if (playingBoard.movePlayer(newPos, curPos)) {
                //    movePlayerLabel.setText("Success! Player has been moved to the " + newPos + " square.");
                //} else {
                //    movePlayerLabel.setText("Error. No players on that square.");
                //}
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
        if (playingBoard.rolledDice(totalRoll, firstRoll == secondRoll)) {
            rollDiceButton.setDisable(false);
            endTurnButton.setDisable(true);
        } else {
            rollDiceButton.setDisable(true);
            endTurnButton.setDisable(false);
        }
        playingBoard.getCurrentPlayer().setLastRoll((short) totalRoll); // save last roll for utilities rent calculation
        Player player = playingBoard.getCurrentPlayer();
        Property property = player.getCurrentTile(playingBoard);
        String streetName = property.getStreetName();
        Action action = property.getAction();

        Card card;
        String actionType = null;
        if (action != null) {
            actionType = action.getActionType();
        }

        if (actionType != null && !actionType.equals("RollMultiple")) {

            switch (actionType) {
                case "PotLuck":
                    card = playingBoard.takeCard("PotLuck", Board.getPlayers(), playingBoard);
                    break;
                case "Opportunity":
                    card = playingBoard.takeCard("Opportunity", Board.getPlayers(), playingBoard);
                    break;
                case "PayBank":
                case "Collect":
                case "FreeParking":
                case "Jail":
                    property.getAction().performAction(Board.getPlayers(), playingBoard);
                    break;
            }
        } else {
            Player owner = property.getOwner();
            if (owner != null) {
                // calculate any rent due
                short rent = property.getDueRent(player);
                player.payMoney(rent);
                owner.giveMoney(rent);
            } else {
                buyPropertyButton.setDisable(false);
                //property.setOwner(player);
            }
        }
        rollDiceLabel.setText("Player has rolled a " + firstRoll + " and a " + secondRoll + " and moved to " + streetName + ".");
    }

    public void onBuyPropertyButtonClick() {
        Player player = playingBoard.getCurrentPlayer();
        Property property = player.getCurrentTile(playingBoard);
        Bank bank = playingBoard.bank;
        property.buyProperty(player, bank);
    }
}