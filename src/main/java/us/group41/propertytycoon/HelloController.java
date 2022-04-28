package us.group41.propertytycoon;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This class is used to control the ui and handles generic logic where it needs to output messages to the UI.
 *
 * @author 235288
 * @version 0.5.1
 * @since 29/04/2022
 */
public class HelloController {
    private final Board playingBoard = new Board();
    public Button rollDiceButton, endTurnButton, buyPropertyButton, pay10Button, startGameButton, payOutOfJailButton, getOutJailFreeButton, addBuildingButton, opportunityKnockButton, bankruptButton, addRobotButton, addPlayerButton, sellPropertyButton;
    public Label startGameLabel, rollDiceLabel, addPlayerLabel, rollDiceActionLabel, buyPropertyLabel, potLuckChoiceLabel, jailLabel, addBuildingLabel, sellPropertyLabel, bankruptLabel, endTurnLabel;
    boolean tokenFirstTime = true, playerFirstTime = true;
    private short bankDebtAmount = 0;
    private short playerDebtAmount = 0;
    public TextField addBuildingTextField;
    public ComboBox playerTokenComboBox;
    public TextField sellPropertyTextField;
    public StackPane tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9, tile10, tile11, tile12, tile13, tile14, tile15, tile16, tile17, tile18, tile19, tile20, tile21, tile22, tile23, tile24, tile25, tile26, tile27, tile28, tile29, tile30, tile31, tile32, tile33, tile34, tile35, tile36, tile37, tile38, tile39;
    public Label name0, name1, name2, name3, name4, name5, name6, name7, name8, name9, name10, name11, name12, name13, name14, name15, name16, name17, name18, name19, name20, name21, name22, name23, name24, name25, name26, name27, name28, name29, name30, name31, name32, name33, name34, name35, name36, name37, name38, name39;

    int playerInt = 0;
    private short freeParkingDebtAmount = 0;
    public Label owner1, owner3, owner5, owner6, owner8, owner9, owner11, owner12, owner13, owner14, owner15, owner16, owner18, owner19, owner21, owner23, owner24, owner25, owner26, owner27, owner28, owner29, owner31, owner32, owner34, owner35, owner37, owner39;
    public Label cost1, cost3, cost5, cost6, cost8, cost9, cost11, cost12, cost13, cost14, cost15, cost16, cost18, cost19, cost21, cost23, cost24, cost25, cost26, cost27, cost28, cost29, cost31, cost32, cost34, cost35, cost37, cost39;
    public StackPane group1, group3, group6, group8, group9, group11, group13, group14, group16, group18, group19, group21, group23, group24, group26, group27, group29, group31, group32, group34, group37, group39;
    public Label group5, group12, group15, group25, group28, group35;
    public Label player1, player2, player3, player4, player5, money1, money2, money3, money4, money5, pos1, pos2, pos3, pos4, pos5, jail1, jail2, jail3, jail4, jail5, lose1, lose2, lose3, lose4, lose5;
    Label[] boardNameLabels = new Label[40], boardOwnerLabels = new Label[40], boardCostLabels = new Label[40];
    Object[] boardGroupLabels = new Object[40];
    StackPane[] boardStackPanes = new StackPane[40];

    /**
     * Adds the tokens to the token combo box on click
     */
    @FXML
    protected void onPlayerTokenComboBoxClick() {
        if (tokenFirstTime) {
            playerTokenComboBox.getItems().addAll(Player.Token.values());
            tokenFirstTime = false;
        }
    }

    /**
     * Disables and enables many buttons to prepare for the next player's turn, also checks if the game is over, or if it needs to have the robot automatically have its turn.
     */
    @FXML
    protected void onEndTurnButtonClick() {
        endTurnButton.setDisable(true);
        rollDiceButton.setDisable(false);
        addBuildingLabel.setText("");
        addBuildingTextField.setText("");
        buyPropertyLabel.setText("");
        if (!Board.endTurn()) {
            startGameLabel.setText("Player" + playingBoard.getCurrentPlayer().getPlayerToken() + " has won");
            rollDiceButton.setDisable(true);
            addBuildingButton.setDisable(true);
            playingBoard.endGame();
            startGameButton.setDisable(false);
        } else {
            if (playingBoard.getCurrentPlayer().isRobot()) {
                autoMove();
            }
            if (playingBoard.getCurrentPlayer().getInJail()) {
                payOutOfJailButton.setDisable(false);
                if (playingBoard.getCurrentPlayer().getNumJailFree() > 0) {
                    getOutJailFreeButton.setDisable(false);
                }
            } else {
                payOutOfJailButton.setDisable(true);
                getOutJailFreeButton.setDisable(true);
            }
        }
        addBuildingButton.setDisable(false);
        pay10Button.setDisable(true);
        opportunityKnockButton.setDisable(true);
        potLuckChoiceLabel.setText("");
        rollDiceLabel.setText("Player" + (playingBoard.getCurrentPlayerNo() + 1));
        rollDiceActionLabel.setText("");
    }

    /**
     * Adds a player and enables you to add 1 robot player
     */
    @FXML
    protected void onAddPlayerButtonClick() {
        if (addPlayer(false)) {
            if (playerFirstTime) {
                addRobotButton.setDisable(false);
                playerFirstTime = false;
            }
        }
    }

    /**
     * Attempts to start the game, if successful loads the board
     */
    @FXML
    protected void onStartGameButtonClick() {
        if (playerInt > 0) {
            if (playingBoard.startGame()) {
                startGameLabel.setText("Success! Game has started");
                fillArrays();
                fillBoard();
            } else {
                startGameLabel.setText("Error. Game has already started with " + playingBoard.getNumPlayers() + " players.");
            }
        } else {
            startGameLabel.setText("No players yet. Please add some players.");
        }
        rollDiceButton.setVisible(true);
        rollDiceButton.setDisable(false);
        addPlayerLabel.setText("");
        addBuildingButton.setDisable(true);
        addBuildingTextField.setDisable(false);
        startGameButton.setDisable(true);
        for (int i = 0; i < Board.getPlayers().size(); i++) {
            showPlayer(Board.getPlayers().get(i));
        }
    }

    /**
     * Makes the robot have its turn. automatically buys properties as long as it's got £500 more than the cost of the property and houses/hotels if it's got £1000.
     */
    public void autoMove() {
        Random random = new Random();
        int[] rolls;
        int firstRoll;
        int secondRoll;
        int totalRoll;
        Boolean rollDice; // indicates another roll is required.
        // while throwing doubles and not in jail
        do {
            rolls = random.ints(2, 1, 6).toArray();
            firstRoll = rolls[0];
            secondRoll = rolls[1];
            totalRoll = firstRoll + secondRoll;
            rollDice = playingBoard.rolledDice(totalRoll, firstRoll == secondRoll);
            playingBoard.getCurrentPlayer().setLastRoll((short) totalRoll); // save last roll for utilities rent calculation
            Player player = playingBoard.getCurrentPlayer();
            Property property = player.getCurrentTile();
            Action action = property.getAction();
            Card card = null;
            String actionType = null;
            if (action != null) {
                actionType = action.getActionType();
            }
            if (actionType != null && !actionType.equals("RollMultiple")) {
                // property action
                switch (actionType) {
                    case "PotLuck" -> card = takeCard("PotLuck", Board.getPlayers(), playingBoard);
                    case "Opportunity" -> card = takeCard("Opportunity", Board.getPlayers(), playingBoard);
                    case "PayBank" ->
                            bankDebtAmount = property.getAction().performAction(Board.getPlayers(), playingBoard);
                    case "Collect", "FreeParking", "Jail" ->
                            property.getAction().performAction(Board.getPlayers(), playingBoard);
                }
            }
            if (card != null) {
                if (Objects.equals(card.getAction().getActionType(), "FineOpp")) {
                    if (playingBoard.getCurrentPlayer().getMoney() >= 10) {
                        playingBoard.getCurrentPlayer().payMoney((short) 10);  // what if not have £10
                        playingBoard.freeParking.addMoney((short) 10);
                    } else {
                        //bankrupt or opportunity
                    }
                }
                if (Objects.equals(card.getAction().getActionType(), "MoveBack")) {
                    playingBoard.movePlayerBack(card.getAction().getActionValue());
                }
            }
            Player owner = property.getOwner();
            if (owner != null && owner != player) {
                // calculate any rent due
                short rent = property.getDueRent(player);
                if (player.getMoney() < rent) {
                    playerDebtAmount = rent;
                } else {
                    player.payMoney(rent);
                    owner.giveMoney(rent);
                }
            } else {
                if (!property.getGroup().equals("")) {
                    if (player.isPassedGO()) {
                        if ((player.getMoney() - property.getCost()) > 500) { // buy property if over 500 left once brought
                            if (playingBoard.buyProperty(property)) {
                                updateOwner(player, property);
                            }
                        }
                    }
                }
            }
            // find my properties
            List<Property> myProperties = new ArrayList<>();
            Property[] tiles = Board.getTiles();
            for (Property tile : tiles) {
                if (tile.getOwner() == player) {
                    myProperties.add(tile);
                }
            }
            short money = 0;
            if (freeParkingDebtAmount != 0 || bankDebtAmount != 0 || playerDebtAmount != 0) {
                short debt = 0;
                if (debt < freeParkingDebtAmount) {
                    debt = freeParkingDebtAmount;
                } else if (debt < bankDebtAmount) {
                    debt = bankDebtAmount;
                } else if (debt < playerDebtAmount) {
                    debt = playerDebtAmount;
                }
                while (player.getMoney() < debt) {
                    // find site with most houses. if all have 0 houses then it will mortgage or sell the property
                    Property maxHouses;
                    if (myProperties.size() > 0) {
                        maxHouses = myProperties.get(0);
                    } else {
                        player.setBankrupt(true);
                        break;
                    }
                    for (Property tile : myProperties) {
                        if (tile.getNumHotels() == 1 || tile.getNumHouses() > maxHouses.getNumHouses()) {
                            maxHouses = tile;
                        }
                    }
                    money = maxHouses.sellProperty();
                    if (maxHouses.getOwner() == null) {
                        updateOwner(null, maxHouses);
                        //Set other properties in the same group to not all be owned by the same owner
                        for (Property tile : tiles) {
                            if (Objects.equals(maxHouses.getGroup(), tile.getGroup())) {
                                tile.setAllPropertiesOwnedByOwner(false);
                            }
                        }
                        myProperties.remove(maxHouses);
                    } else {
                        updateOwner(player, maxHouses);
                    }
                    player.giveMoney(money);
                    playingBoard.bank.payMoney(money);
                }
                if (freeParkingDebtAmount != 0) {
                    if (!player.isBankrupt()) {
                        //give as much money as possible to the source of debt
                        playingBoard.freeParking.addMoney(money);
                        player.payMoney(money);
                    } else {
                        playingBoard.freeParking.addMoney(player.getMoney());
                        player.payMoney(player.getMoney());
                    }
                    freeParkingDebtAmount = 0;
                }
                if (bankDebtAmount != 0) {
                    if (!player.isBankrupt()) {
                        playingBoard.bank.giveMoney(money);
                        player.payMoney(money);
                    } else {
                        playingBoard.bank.giveMoney(player.getMoney());
                        player.payMoney(player.getMoney());
                    }
                    bankDebtAmount = 0;
                }
                if (playerDebtAmount != 0) {
                    if (!player.isBankrupt()) {
                        owner.giveMoney(money);
                        player.payMoney(money);
                    } else {
                        owner.giveMoney(player.getMoney());
                        player.payMoney(player.getMoney());
                    }
                    playerDebtAmount = 0;
                }
            }
            if (player.getMoney() > 1000 && myProperties.size() != 0) {
                //do some building if we have enough money and some properties build on properties with the smallest number of houses first
                Property minHouses = myProperties.get(0);
                for (Property tile : myProperties) {
                    if (tile.getNumHotels() == 0 || tile.getNumHouses() < minHouses.getNumHouses()) {
                        //only build if you own all properties
                        if (tile.isAllPropertiesOwnedByOwner()) {
                            minHouses = tile;
                        }
                    }
                }
                // put one house/ hotel on each property
                if (minHouses.isAllPropertiesOwnedByOwner()) {
                    for (Property tile : myProperties) {
                        if (minHouses.getGroup().equals(tile.getGroup()) && !tile.getGroup().equals("Station") && !tile.getGroup().equals("Utilities")) {
                            if (tile.build(player, playingBoard.bank)) {
                                updateOwner(player, tile);
                            }
                        }
                    }
                }
            }
            showPlayer(playingBoard.getCurrentPlayer());
        } while (rollDice);
        if (!Board.endTurn()) {
            startGameLabel.setText("Player" + playingBoard.getCurrentPlayer().getPlayerToken() + " has won");
            rollDiceButton.setDisable(true);
            addBuildingButton.setDisable(true);
            playingBoard.endGame();
            startGameButton.setDisable(false);
        } else {
            if (playingBoard.getCurrentPlayer().getInJail()) {
                payOutOfJailButton.setDisable(false);
                if (playingBoard.getCurrentPlayer().getNumJailFree() > 0) {
                    getOutJailFreeButton.setDisable(false);
                }
            } else {
                payOutOfJailButton.setDisable(true);
                getOutJailFreeButton.setDisable(true);
            }
        }
        if (playingBoard.getCurrentPlayer().isPassedGO()) {
            addBuildingButton.setDisable(false);
        }
        pay10Button.setDisable(true);
        opportunityKnockButton.setDisable(true);
        potLuckChoiceLabel.setText("");
        rollDiceLabel.setText("Player" + (playingBoard.getCurrentPlayerNo() + 1));
        rollDiceActionLabel.setText("");
        for (Player showPlayer : Board.getPlayers()) {
            showPlayer(showPlayer);
        }
    }

    /**
     * Performs the player's move, displays what happened and sets buttons ready for the next situation
     */
    @FXML
    protected void onRollDiceButtonClick() {
        Random random = new Random();
        int[] rolls = random.ints(2, 1, 6).toArray();
        int firstRoll = rolls[0];
        int secondRoll = rolls[1];
        startGameLabel.setText("");
        rollDiceActionLabel.setText("");
        rollDiceLabel.setText("");
        buyPropertyLabel.setText("");
        jailLabel.setText("");
        addBuildingLabel.setText("");

        int totalRoll = firstRoll + secondRoll;
        if (playingBoard.rolledDice(totalRoll, firstRoll == secondRoll)) {
            rollDiceButton.setDisable(false);
            endTurnButton.setDisable(true);
            rollDiceActionLabel.setText("Roll Again as Rolled Double");
        } else {
            rollDiceButton.setDisable(true);
            endTurnButton.setDisable(false);
        }

        playingBoard.getCurrentPlayer().setLastRoll((short) totalRoll); // save last roll for utilities rent calculation
        Player player = playingBoard.getCurrentPlayer();
        Property property = player.getCurrentTile();
        String streetName = property.getStreetName();
        Action action = property.getAction();
        rollDiceLabel.setText("Player" + (playingBoard.getCurrentPlayerNo() + 1) + " has rolled a " + firstRoll + " and a " + secondRoll + " and moved to " + streetName + ".");
        Card card = null;
        String actionType = null;
        short actionValue = 0;
        if (action != null) {
            actionType = action.getActionType();
            actionValue = action.getActionValue();
        }
        // if the card is a normal property (or one of the utilities) then don't perform action
        if (actionType != null && !actionType.equals("RollMultiple")) {
            // property action
            switch (actionType) {
                case "PotLuck" -> {
                    card = takeCard("PotLuck", Board.getPlayers(), playingBoard);
                    rollDiceActionLabel.setText("Action: " + actionType + " Picked Card: " + card.getDescription());
                }
                case "Opportunity" -> {
                    card = takeCard("Opportunity", Board.getPlayers(), playingBoard);
                    rollDiceActionLabel.setText("Action: " + actionType + " Picked Card: " + card.getDescription());
                }
                case "PayBank" -> {
                    bankDebtAmount = property.getAction().performAction(Board.getPlayers(), playingBoard);
                    if (bankDebtAmount != 0) {
                        sellPropertyTextField.setDisable(false);
                        sellPropertyButton.setDisable(false);
                        bankruptButton.setDisable(false);
                        rollDiceButton.setDisable(true);
                        endTurnButton.setDisable(true);
                        rollDiceActionLabel.setText("Need to sell to Pay Bank £" + bankDebtAmount);
                    } else {
                        rollDiceActionLabel.setText("Paid Bank £" + actionValue);
                    }
                }
                case "Collect" -> {
                    property.getAction().performAction(Board.getPlayers(), playingBoard);
                    rollDiceActionLabel.setText("Collected £" + actionValue);
                }
                case "FreeParking" -> {
                    property.getAction().performAction(Board.getPlayers(), playingBoard);
                    rollDiceActionLabel.setText("Collected " + actionType + playingBoard.freeParking.toString() + " from FreeParking");
                }
                case "Jail" -> {
                    rollDiceButton.setDisable(true);
                    endTurnButton.setDisable(false);
                    property.getAction().performAction(Board.getPlayers(), playingBoard);
                    rollDiceActionLabel.setText("Sent to Jail");
                }
            }
        }
        if (card != null) {
            if (Objects.equals(card.getAction().getActionType(), "FineOpp")) {
                pay10Button.setDisable(false);
                opportunityKnockButton.setDisable(false);
                rollDiceButton.setDisable(true);
                endTurnButton.setDisable(true);
                buyPropertyButton.setDisable(false);
                potLuckChoiceLabel.setText(" Pay a fine or take a Opportunity");
            }
            if (Objects.equals(card.getAction().getActionType(), "MoveBack")) {
                playingBoard.movePlayerBack(card.getAction().getActionValue());
            }
        }
        Player owner = property.getOwner();
        if (owner != null && owner != player) {
            // calculate any rent due
            short rent = property.getDueRent(player);
            if (player.getMoney() < rent) {
                playerDebtAmount = rent;
                sellPropertyTextField.setDisable(false);
                sellPropertyButton.setDisable(false);
                bankruptButton.setDisable(false);
                rollDiceButton.setDisable(true);
                endTurnButton.setDisable(true);
                rollDiceActionLabel.setText("Need to sell to Pay " + owner.getPlayerToken() + " £" + playerDebtAmount);
            } else {
                player.payMoney(rent);
                owner.giveMoney(rent);
            }
        } else {
            if (!property.getGroup().equals("")) {
                if (player.isPassedGO()) {
                    buyPropertyButton.setDisable(false);
                }
            } else {
                buyPropertyButton.setDisable(true);
            }
        }
        for (Player showPlayer : Board.getPlayers()) {
            showPlayer(showPlayer);
        }
        if (playingBoard.getCurrentPlayer().isPassedGO()) {
            addBuildingButton.setDisable(false);
        }
    }

    /**
     * Takes a card from the relevant queue and performs the action on it
     *
     * @param cardType opportunity knock or potluck
     * @param players  list of players for the birthday card
     * @param board    board
     * @return the card that was picked up
     */
    public Card takeCard(String cardType, List<Player> players, Board board) {
        Card card;
        Player player;
        short debt;
        if (Objects.equals(cardType, "PotLuck")) {
            card = playingBoard.potLuck.poll();
        } else {
            card = playingBoard.opportunity.poll();
        }
        player = board.getCurrentPlayer();
        debt = card.action.performAction(players, board);
        if (debt != 0) {
            if (card.action.getActionType().equals("PayFree")) {
                freeParkingDebtAmount = debt;
                rollDiceActionLabel.setText("Need to sell to Pay FreeParking £" + debt);
            } else {
                bankDebtAmount = debt;
                rollDiceActionLabel.setText("Need to sell to Pay Bank £" + debt);
            }
            sellPropertyTextField.setDisable(false);
            sellPropertyButton.setDisable(false);
            bankruptButton.setDisable(false);
            rollDiceButton.setDisable(true);
            endTurnButton.setDisable(true);
        }
        String cardAction = card.getAction().getActionType();
        if ("JailFree".equals(cardAction)) {  // don't put card to bottom of pack
            player.setNumJailFree((short) (player.getNumJailFree() + 1));
            rollDiceActionLabel.setText("added get out of jail free card");
        } else { // add card to bottom of pack queue
            if (Objects.equals(cardType, "PotLuck")) {
                playingBoard.potLuck.add(card);
            } else {
                playingBoard.opportunity.add(card);
            }
        }
        return card;
    }


    /**
     * Buy the property you're currently on
     */
    @FXML
    public void onBuyPropertyButtonClick() {
        Player player = playingBoard.getCurrentPlayer();
        Property property = player.getCurrentTile();
        if (playingBoard.buyProperty(property)) {
            buyPropertyButton.setDisable(true);
            buyPropertyLabel.setText(property.getStreetName() + " brought");
            updateOwner(player, property);
        } else {
            buyPropertyLabel.setText("Can't Buy " + property.getStreetName());
        }
        showPlayer(playingBoard.getCurrentPlayer());
    }

    /**
     * Pay £10 from when given a choice through potluck card.
     */
    @FXML
    public void onPay10ButtonClick() {
        if (playingBoard.getCurrentPlayer().getMoney() >= 10) {
            playingBoard.getCurrentPlayer().payMoney((short) 10);
            playingBoard.freeParking.addMoney((short) 10);
            pay10Button.setDisable(true);
            opportunityKnockButton.setDisable(true);
            potLuckChoiceLabel.setText(" £10 Paid");
            buyPropertyButton.setDisable(false);
            endTurnButton.setDisable(false);
        }
        showPlayer(playingBoard.getCurrentPlayer());
    }

    /**
     * Draw opportunity knock when given a choice through potluck card.
     */
    @FXML
    public void onOpportunityKnockButtonClick() {
        Card card;
        card = takeCard("Opportunity", Board.getPlayers(), playingBoard); //what if debt
        opportunityKnockButton.setDisable(true);
        pay10Button.setDisable(true);
        potLuckChoiceLabel.setText(card.getDescription() + " picked");
        card.getAction().performAction(Board.getPlayers(), playingBoard);
        showPlayer(playingBoard.getCurrentPlayer());
        buyPropertyButton.setDisable(false);
        endTurnButton.setDisable(false);
    }

    /**
     * Pay £50 to get out of jail
     */
    @FXML
    public void onPayOutOfJailButtonClick() {
        Player player = playingBoard.getCurrentPlayer();
        FreeParking free = playingBoard.freeParking;
        if (player.getMoney() >= 50) {
            player.payMoney((short) 50);
            free.addMoney((short) 50);
            player.setInJail(false);
            player.setJailRolls((short) 0);
            payOutOfJailButton.setDisable(true);
            getOutJailFreeButton.setDisable(true);
            jailLabel.setText(" Paid £50");
        }
        showPlayer(playingBoard.getCurrentPlayer());
    }

    /**
     * Use get out of jail free card.
     */
    @FXML
    public void onGetOutJailFreeButtonClick() {
        Player player = playingBoard.getCurrentPlayer();
        if (player.getNumJailFree() > 1) {
            player.setNumJailFree((short) (player.getNumJailFree() - 1));
            player.setInJail(false);
            player.setJailRolls((short) 0);
            payOutOfJailButton.setDisable(true);
            getOutJailFreeButton.setDisable(true);
            jailLabel.setText(" get Out of Jail card used");
        }
        showPlayer(playingBoard.getCurrentPlayer());
    }

    /**
     * Build on property.
     */
    @FXML
    public void onAddBuildingButtonClick() {
        addBuildingTextField.setDisable(false);
        String text = addBuildingTextField.getText();
        Player player = playingBoard.getCurrentPlayer();
        if (text.length() == 0) {
            addBuildingLabel.setText("No street name entered");
        }
        Property[] tiles = Board.getTiles();
        Property property = null;
        //find the property from the text string
        for (Property tile : tiles) {
            if (tile.getStreetName().equalsIgnoreCase(text)) {
                property = tile;
            }
        }
        addBuildingTextField.setDisable(false);
        if (property == null) {
            addBuildingLabel.setText("Property not found");
        } else if (property.getOwner() != player) {
            addBuildingLabel.setText("You don't own that");
        } else if (property.getGroup().equals("Station")) {
            addBuildingLabel.setText("You cant build houses on a station");
        } else {
            if (!property.build(playingBoard.getCurrentPlayer(), playingBoard.bank)) {
                addBuildingLabel.setText("Build failed");
            } else {
                addBuildingLabel.setText("Build Successful");
                updateOwner(player, property);
            }
            showPlayer(playingBoard.getCurrentPlayer());
        }
    }

    /**
     * Forfeit the game.
     */
    @FXML
    public void onBankruptButtonClick() {
        boolean finished = false;
        Player player = playingBoard.getCurrentPlayer();
        for (Property property : Board.getTiles()) {
            if (property.getOwner() == player) {
                bankruptLabel.setText("Please sell all your properties before forfeiting");
                finished = true;
                break;
            }
        }
        if (!finished) {
            player.setBankrupt(true);
            if (bankDebtAmount > 0) {
                player.payMoney(player.getMoney());
                playingBoard.bank.giveMoney(player.getMoney());
                bankDebtAmount = 0;
            } else if (freeParkingDebtAmount > 0) {
                player.payMoney(player.getMoney());
                playingBoard.freeParking.addMoney(player.getMoney());
                freeParkingDebtAmount = 0;
            } else if (playerDebtAmount > 0) {
                player.payMoney(player.getMoney());
                player.getCurrentTile().getOwner().giveMoney(player.getMoney());
                playerDebtAmount = 0;
            }
            sellPropertyButton.setDisable(true);
            bankruptButton.setDisable(true);
            Board.endTurn();
            rollDiceButton.setDisable(false);
            sellPropertyLabel.setText("");
            showPlayer(player);
        }
    }


    /**
     * Sell property from the entered text
     */
    @FXML
    public void onSellPropertyButtonClick() {
        Player player = playingBoard.getCurrentPlayer();
        String text = sellPropertyTextField.getText();
        if (text.length() == 0) {
            sellPropertyLabel.setText("No property name entered");
        } else {
            Property[] tiles = Board.getTiles();
            Property property = null;
            for (Property tile : tiles) {
                if (tile.getStreetName().equalsIgnoreCase(text)) {
                    property = tile;
                }
            }
            if (property == null) {
                sellPropertyLabel.setText("Property not found");
            } else if (property.getOwner() != playingBoard.getCurrentPlayer()) {
                sellPropertyLabel.setText("You don't own that");
            } else {
                short value;
                if ((value = property.sellProperty()) == 0) {
                    sellPropertyLabel.setText("Sale failed try another property");
                } else {
                    sellPropertyLabel.setText("Sale succeeded");
                    if (property.getOwner() == null) {
                        updateOwner(null, property);
                    } else {
                        updateOwner(player, property);
                    }
                    playingBoard.getCurrentPlayer().giveMoney(value);
                    showPlayer(player);
                    if (bankDebtAmount != 0) {
                        if (bankDebtAmount <= player.getMoney()) {
                            playingBoard.bank.giveMoney(bankDebtAmount);
                            player.payMoney(bankDebtAmount);
                            bankDebtAmount = 0;
                            afterDebt(player);
                        } else {
                            sellPropertyLabel.setText("Need more money to cover the debt £" + bankDebtAmount);
                        }
                    } else {
                        if (freeParkingDebtAmount != 0) {
                            if (freeParkingDebtAmount <= player.getMoney()) {
                                playingBoard.freeParking.addMoney(freeParkingDebtAmount);
                                player.payMoney(freeParkingDebtAmount);
                                freeParkingDebtAmount = 0;
                                afterDebt(player);
                            } else {
                                sellPropertyLabel.setText("Need more money to cover the debt £" + freeParkingDebtAmount);
                            }
                        }
                        if (playerDebtAmount != 0) {
                            if (playerDebtAmount <= player.getMoney()) {
                                Player owner = player.getCurrentTile().getOwner();
                                owner.giveMoney(playerDebtAmount);
                                player.payMoney(playerDebtAmount);
                                playerDebtAmount = 0;
                                afterDebt(player);
                                showPlayer(owner);
                            } else {
                                sellPropertyLabel.setText("Need more money to cover the debt £" + playerDebtAmount);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * enables end turn and sell property buttons
     *
     * @param player player to refresh
     */
    public void afterDebt(Player player) {
        endTurnButton.setDisable(false);
        sellPropertyButton.setDisable(false);
        showPlayer(player);
    }

    /**
     * Fills arrays with board elements, so they can be accessed in loops easier.
     */
    public void fillArrays() {
        //Names
        boardNameLabels[0] = name0;
        boardNameLabels[1] = name1;
        boardNameLabels[2] = name2;
        boardNameLabels[3] = name3;
        boardNameLabels[4] = name4;
        boardNameLabels[5] = name5;
        boardNameLabels[6] = name6;
        boardNameLabels[7] = name7;
        boardNameLabels[8] = name8;
        boardNameLabels[9] = name9;
        boardNameLabels[10] = name10;
        boardNameLabels[11] = name11;
        boardNameLabels[12] = name12;
        boardNameLabels[13] = name13;
        boardNameLabels[14] = name14;
        boardNameLabels[15] = name15;
        boardNameLabels[16] = name16;
        boardNameLabels[17] = name17;
        boardNameLabels[18] = name18;
        boardNameLabels[19] = name19;
        boardNameLabels[20] = name20;
        boardNameLabels[21] = name21;
        boardNameLabels[22] = name22;
        boardNameLabels[23] = name23;
        boardNameLabels[24] = name24;
        boardNameLabels[25] = name25;
        boardNameLabels[26] = name26;
        boardNameLabels[27] = name27;
        boardNameLabels[28] = name28;
        boardNameLabels[29] = name29;
        boardNameLabels[30] = name30;
        boardNameLabels[31] = name31;
        boardNameLabels[32] = name32;
        boardNameLabels[33] = name33;
        boardNameLabels[34] = name34;
        boardNameLabels[35] = name35;
        boardNameLabels[36] = name36;
        boardNameLabels[37] = name37;
        boardNameLabels[38] = name38;
        boardNameLabels[39] = name39;

        //group
        boardGroupLabels[1] = group1;
        boardGroupLabels[3] = group3;
        boardGroupLabels[5] = group5;
        boardGroupLabels[6] = group6;
        boardGroupLabels[8] = group8;
        boardGroupLabels[9] = group9;
        boardGroupLabels[11] = group11;
        boardGroupLabels[12] = group12;
        boardGroupLabels[13] = group13;
        boardGroupLabels[14] = group14;
        boardGroupLabels[15] = group15;
        boardGroupLabels[16] = group16;
        boardGroupLabels[18] = group18;
        boardGroupLabels[19] = group19;
        boardGroupLabels[21] = group21;
        boardGroupLabels[23] = group23;
        boardGroupLabels[24] = group24;
        boardGroupLabels[25] = group25;
        boardGroupLabels[26] = group26;
        boardGroupLabels[27] = group27;
        boardGroupLabels[28] = group28;
        boardGroupLabels[29] = group29;
        boardGroupLabels[31] = group31;
        boardGroupLabels[32] = group32;
        boardGroupLabels[34] = group34;
        boardGroupLabels[35] = group35;
        boardGroupLabels[37] = group37;
        boardGroupLabels[39] = group39;

        //Owners
        boardOwnerLabels[1] = owner1;
        boardOwnerLabels[3] = owner3;
        boardOwnerLabels[5] = owner5;
        boardOwnerLabels[6] = owner6;
        boardOwnerLabels[8] = owner8;
        boardOwnerLabels[9] = owner9;
        boardOwnerLabels[11] = owner11;
        boardOwnerLabels[12] = owner12;
        boardOwnerLabels[13] = owner13;
        boardOwnerLabels[14] = owner14;
        boardOwnerLabels[15] = owner15;
        boardOwnerLabels[16] = owner16;
        boardOwnerLabels[18] = owner18;
        boardOwnerLabels[19] = owner19;
        boardOwnerLabels[21] = owner21;
        boardOwnerLabels[23] = owner23;
        boardOwnerLabels[24] = owner24;
        boardOwnerLabels[25] = owner25;
        boardOwnerLabels[26] = owner26;
        boardOwnerLabels[27] = owner27;
        boardOwnerLabels[28] = owner28;
        boardOwnerLabels[29] = owner29;
        boardOwnerLabels[31] = owner31;
        boardOwnerLabels[32] = owner32;
        boardOwnerLabels[34] = owner34;
        boardOwnerLabels[35] = owner35;
        boardOwnerLabels[37] = owner37;
        boardOwnerLabels[39] = owner39;

        //Costs
        boardCostLabels[1] = cost1;
        boardCostLabels[3] = cost3;
        boardCostLabels[5] = cost5;
        boardCostLabels[6] = cost6;
        boardCostLabels[8] = cost8;
        boardCostLabels[9] = cost9;
        boardCostLabels[11] = cost11;
        boardCostLabels[12] = cost12;
        boardCostLabels[13] = cost13;
        boardCostLabels[14] = cost14;
        boardCostLabels[15] = cost15;
        boardCostLabels[16] = cost16;
        boardCostLabels[18] = cost18;
        boardCostLabels[19] = cost19;
        boardCostLabels[21] = cost21;
        boardCostLabels[23] = cost23;
        boardCostLabels[24] = cost24;
        boardCostLabels[25] = cost25;
        boardCostLabels[26] = cost26;
        boardCostLabels[27] = cost27;
        boardCostLabels[28] = cost28;
        boardCostLabels[29] = cost29;
        boardCostLabels[31] = cost31;
        boardCostLabels[32] = cost32;
        boardCostLabels[34] = cost34;
        boardCostLabels[35] = cost35;
        boardCostLabels[37] = cost37;
        boardCostLabels[39] = cost39;

        //Tiles
        boardStackPanes[0] = tile0;
        boardStackPanes[1] = tile1;
        boardStackPanes[2] = tile2;
        boardStackPanes[3] = tile3;
        boardStackPanes[4] = tile4;
        boardStackPanes[5] = tile5;
        boardStackPanes[6] = tile6;
        boardStackPanes[7] = tile7;
        boardStackPanes[8] = tile8;
        boardStackPanes[9] = tile9;
        boardStackPanes[10] = tile10;
        boardStackPanes[11] = tile11;
        boardStackPanes[12] = tile12;
        boardStackPanes[13] = tile13;
        boardStackPanes[14] = tile14;
        boardStackPanes[15] = tile15;
        boardStackPanes[16] = tile16;
        boardStackPanes[17] = tile17;
        boardStackPanes[18] = tile18;
        boardStackPanes[19] = tile19;
        boardStackPanes[20] = tile20;
        boardStackPanes[21] = tile21;
        boardStackPanes[22] = tile22;
        boardStackPanes[23] = tile23;
        boardStackPanes[24] = tile24;
        boardStackPanes[25] = tile25;
        boardStackPanes[26] = tile26;
        boardStackPanes[27] = tile27;
        boardStackPanes[28] = tile28;
        boardStackPanes[29] = tile29;
        boardStackPanes[30] = tile30;
        boardStackPanes[31] = tile31;
        boardStackPanes[32] = tile32;
        boardStackPanes[33] = tile33;
        boardStackPanes[34] = tile34;
        boardStackPanes[35] = tile35;
        boardStackPanes[36] = tile36;
        boardStackPanes[37] = tile37;
        boardStackPanes[38] = tile38;
        boardStackPanes[39] = tile39;
    }

    /**
     * Fills the board with the data from the tiles property array which was generated from the .json
     */
    public void fillBoard() {
        Property[] tiles = Board.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            Property tile = tiles[i];
            String group = tile.getGroup();
            String name = tile.getStreetName();
            String cost = "£" + tile.getCost();

            //Name
            boardNameLabels[i].setText(name);
            boardNameLabels[i].setFont(new Font(10));

            //Group
            if (boardGroupLabels[i] != null) {
                if (isLabel(boardGroupLabels[i])) {
                    Label groupLabel = (Label) boardGroupLabels[i];
                    groupLabel.setText(group);
                    groupLabel.setFont(new Font(16));

                } else if (isStackPane(boardGroupLabels[i])) {
                    StackPane groupStackPane = (StackPane) boardGroupLabels[i];
                    if (group.equalsIgnoreCase("Deep blue")) {
                        groupStackPane.setStyle("-fx-background-color: Navy");
                    } else {
                        groupStackPane.setStyle("-fx-background-color: " + group);
                    }
                }

            }
            //Owner
            if (boardOwnerLabels[i] != null) {
                boardOwnerLabels[i].setFont(new Font(10));
            }

            //Cost
            if (boardCostLabels[i] != null) {
                boardCostLabels[i].setText(cost);
            }
        }
    }

    /**
     * @param object the object to check
     * @return if the object is a Label
     */
    public boolean isLabel(Object object) {
        return object.getClass().getSimpleName().equals("Label");
    }

    /**
     * @param object the object to check
     * @return if the object is a StackPane
     */
    public boolean isStackPane(Object object) {
        return object.getClass().getSimpleName().equals("StackPane");
    }


    /**
     * Updates the label player info in the bottom right.
     *
     * @param player the player to update
     */
    public void showPlayer(Player player) {
        int playerNo = player.fetchPlayerNum();
        int money = player.getMoney();
        Property tile = player.getCurrentTile();
        String position = tile.getStreetName();
        String inJail = player.getInJail() ? "In Jail" : "";
        String hasLost = player.isBankrupt() ? "Bankrupt" : "";
        String group = tile.getGroup();
        if (group.equalsIgnoreCase("deep blue")) {
            group = "Navy";
        } else if (group.equalsIgnoreCase("") || group.equalsIgnoreCase("Station") || group.equalsIgnoreCase("Utilities")) {
            group = "Black";
        }
        switch (playerNo) {
            case 1 -> {
                player1.setText("Player" + playerNo);
                money1.setText("£" + money);
                pos1.setText(position);
                pos1.setStyle("-fx-text-fill: " + group);
                jail1.setText(inJail);
                lose1.setText(hasLost);
            }
            case 2 -> {
                player2.setText("Player" + playerNo);
                money2.setText("£" + money);
                pos2.setText(position);
                pos2.setStyle("-fx-text-fill: " + group);
                jail2.setText(inJail);
                lose2.setText(hasLost);
            }
            case 3 -> {
                player3.setText("Player" + playerNo);
                money3.setText("£" + money);
                pos3.setText(position);
                pos3.setStyle("-fx-text-fill: " + group);
                jail3.setText(inJail);
                lose3.setText(hasLost);
            }
            case 4 -> {
                player4.setText("Player" + playerNo);
                money4.setText("£" + money);
                pos4.setText(position);
                pos4.setStyle("-fx-text-fill: " + group);
                jail4.setText(inJail);
                lose4.setText(hasLost);
            }
            case 5 -> {
                player5.setText("Player" + playerNo);
                money5.setText("£" + money);
                pos5.setText(position);
                pos5.setStyle("-fx-text-fill: " + group);
                jail5.setText(inJail);
                lose5.setText(hasLost);
            }
        }
    }

    /**
     * Updates the label on the board to show the current owner status including houses, hotels and mortgages.
     *
     * @param player   Owner
     * @param property Property to be updated
     */
    public void updateOwner(Player player, Property property) {
        int i = property.getPropertyNo();
        int houses = property.getNumHouses() + property.getNumHotels();
        String housesText = switch (houses) {
            //House Emoji
            case 0 -> property.isMortgaged() ? " Loaned" : "";
            case 1 -> "\uD83C\uDFE0";
            case 2 -> "\uD83C\uDFE0\uD83C\uDFE0";
            case 3 -> "\uD83C\uDFE0\uD83C\uDFE0\uD83C\uDFE0";
            case 4 -> "\uD83C\uDFE0\uD83C\uDFE0\uD83C\uDFE0\uD83C\uDFE0";
            case 5 -> " - Hotel";
            default -> "";
        };
        String text = player != null ? "Player" + player.fetchPlayerNum() : "";
        boardOwnerLabels[i].setText(text + housesText);
    }

    /**
     * Add a robot to the game
     */
    @FXML
    protected void onAddRobotButtonClick() {
        if (addPlayer(true)) {
            addRobotButton.setDisable(true);
        }
    }

    /**
     * Validates that it's a valid time to add the player
     *
     * @param robot if it's a robot being added
     */
    public boolean addPlayer(boolean robot) {
        Player.Token token = (Player.Token) playerTokenComboBox.getValue();
        if (token == null) {
            addPlayerLabel.setText("Please select a valid token.");
            return false;
        }
        playerInt += 1;
        if (playerInt <= 5) {
            if (Board.addPlayer(playerInt, token, robot)) {
                addPlayerLabel.setText("Player" + playerInt + " has been added to the game with the " + WordUtils.capitalizeFully(String.valueOf(token)) + " token. " + playerInt + "/5");
                return true;
            } else {
                addPlayerLabel.setText("Failed to add player");
            }
        } else {
            addPlayerLabel.setText("Maximum of 5 players added. Please start the game.");
        }
        startGameButton.setDisable(false);
        startGameButton.setVisible(true);
        return false;
    }

    /*public void updateGamePiece(Player player, Property property) {
        int i = property.getPropertyNo();
        String token = WordUtils.capitalizeFully(String.valueOf(player.getPlayerToken()));
        String filepath = token + ".png";
        //boardStackPanes[i].setStyle("-fx-background-image: url("+filepath+")");

        //Image png = new Image("");

        //boardStackPanes[i].setStyle("-fx-background-image: url(" + png + ")");
        //boardStackPanes[i].setBackground(new Background(new BackgroundImage(png, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(40, 40, false, false, false, false))));
    }*/

}


