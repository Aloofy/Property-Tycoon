package us.group41.propertytycoon;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import org.apache.commons.text.WordUtils;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class HelloController {
    private final Board playingBoard = new Board();
    public Label startGameLabel;
    public Label rollDiceLabel;
    public Label addPlayerLabel;
    public Button rollDiceButton;
    public Button endTurnButton;
    public ComboBox playerTokenComboBox;
    public Button buyPropertyButton;
    public Button pay10Button;
    public Button startGameButton;
    public Button payOutOfJailButton;
    public Button getOutJailFreeButton;
    public Button addBuildingButton;
    public Label rollDiceActionLabel;
    public Label buyPropertyLabel;
    public Label potLuckChoiceLabel;
    public Label jailLabel;
    public Label addBuildingLabel;
    public Label player1Label;
    public Label player2Label;
    public Label player3Label;
    public Label player4Label;
    public Label player5Label;


    public Button opportunityKnockButton;
    int playerInt = 0;
    boolean tokenFirstTime = true;
    public Label name0;
    //int players = 0;
    @FXML
    private Label welcomeText;


    @FXML
    protected void onPlayerTokenComboBoxClick() {
        if (tokenFirstTime) {
            playerTokenComboBox.getItems().addAll(Player.Token.values());
            tokenFirstTime = false;
        }
    }

    public Label name1;
    public Label name2;
    public Label name3;
    public Label name4;
    public Label name5;
    public Label name6;
    public Label name7;
    public Label name8;
    public Label name9;
    public Label name10;
    public Label name11;
    public Label name12;
    public Label name13;
    public Label name14;
    public Label name15;
    public Label name16;
    public Label name17;
    public Label name18;
    public Label name19;
    public Label name20;
    public Label name21;
    public Label name22;
    public Label name23;
    public Label name24;
    public Label name25;
    public Label name26;
    public Label name27;
    public Label name28;
    public Label name29;
    public Label name30;
    public Label name31;
    public Label name32;
    public Label name33;
    public Label name34;
    public Label name35;
    public Label name36;
    public Label name37;
    public Label name38;
    public Label name39;
    public StackPane group1;
    public StackPane group3;
    public Label group5;
    public StackPane group6;
    public StackPane group8;
    public StackPane group9;
    public StackPane group11;
    public Label group12;
    public StackPane group13;
    public StackPane group14;
    public Label group15;
    public StackPane group16;
    public StackPane group18;
    public StackPane group19;
    public StackPane group21;
    public StackPane group23;
    public StackPane group24;
    public Label group25;
    public StackPane group26;
    public StackPane group27;
    public Label group28;
    public StackPane group29;
    public StackPane group31;
    public StackPane group32;
    public StackPane group34;
    public Label group35;
    public StackPane group37;
    public StackPane group39;
    public Label owner1;
    public Label owner3;
    public Label owner5;
    public Label owner6;
    public Label owner8;
    public Label owner9;
    public Label owner11;
    public Label owner12;
    public Label owner13;
    public Label owner14;
    public Label owner15;
    public Label owner16;
    public Label owner18;
    public Label owner19;
    public Label owner21;
    public Label owner23;
    public Label owner24;
    public Label owner25;
    public Label owner26;
    public Label owner27;
    public Label owner28;
    public Label owner29;
    public Label owner31;
    public Label owner32;
    public Label owner34;
    public Label owner35;
    public Label owner37;
    public Label owner39;
    public Label cost1;
    public Label cost3;
    public Label cost5;
    public Label cost6;
    public Label cost8;
    public Label cost9;
    public Label cost11;
    public Label cost12;
    public Label cost13;
    public Label cost14;
    public Label cost15;
    public Label cost16;
    public Label cost18;
    public Label cost19;
    public Label cost21;
    public Label cost23;
    public Label cost24;
    public Label cost25;
    public Label cost26;
    public Label cost27;
    public Label cost28;
    public Label cost29;
    public Label cost31;
    public Label cost32;
    public Label cost34;
    public Label cost35;
    public Label cost37;
    public Label cost39;
    //some other stuff needed for getting text field input
    @FXML
    TextField addBuildingTextField;
    //Names
    Label[] boardNameLabels = new Label[40];
    //Groups
    Object[] boardGroupLabels = new Object[40];
    //Owners
    Label[] boardOwnerLabels = new Label[40];
    //Costs
    Label[] boardCostLabels = new Label[40];

    @FXML
    protected void onEndTurnButtonClick() {
        endTurnButton.setDisable(true);
        rollDiceButton.setDisable(false);
        addBuildingLabel.setText("");
        addBuildingTextField.setText("");
        buyPropertyLabel.setText("");

        if (!Board.endTurn()) {
            addPlayerLabel.setText("Player" + playingBoard.getCurrentPlayer().getPlayerToken() + " has won");
        } else {
            if (playingBoard.getCurrentPlayer().getInJail()) {
                //payOutOfJailButton.setVisible(true);
                payOutOfJailButton.setDisable(false);
                //getOutJailFreeButton.setVisible(true);
                if (playingBoard.getCurrentPlayer().getNumJailFree() > 0) {
                    getOutJailFreeButton.setDisable(false);
                }

            } else {
                //payOutOfJailButton.setVisible(false);
                payOutOfJailButton.setDisable(true);
                //getOutJailFreeButton.setVisible(false);
                getOutJailFreeButton.setDisable(true);
            }
        }
        addBuildingButton.setDisable(false);
        pay10Button.setDisable(true);
        opportunityKnockButton.setDisable(true);
        potLuckChoiceLabel.setText("");
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
        startGameButton.setDisable(false);
        startGameButton.setVisible(true);

    }

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
        addBuildingButton.setDisable(false);
        addBuildingTextField.setDisable(false);
        startGameButton.setDisable(true);
        for (int i = 0; i < Board.getPlayers().size(); i++) {
            showPlayer(Board.getPlayers().get(i));
        }
    }

    @FXML
    protected void rollDice() throws InterruptedException {
        Random random = new Random();
        int[] rolls = random.ints(2, 1, 6).toArray();
        int firstRoll = rolls[0];
        int secondRoll = rolls[1];
        rollDiceActionLabel.setText("");
        rollDiceLabel.setText("");
        buyPropertyLabel.setText("");
        jailLabel.setText("");
        addBuildingLabel.setText("");

        int totalRoll = firstRoll + secondRoll;
        if (playingBoard.rolledDice(totalRoll, firstRoll == secondRoll)) {
            rollDiceButton.setDisable(false);
            endTurnButton.setDisable(true);
            //endTurnButton.setVisible(false);
        } else {
            rollDiceButton.setDisable(true);
            endTurnButton.setDisable(false);
            //endTurnButton.setVisible(true);
        }

        playingBoard.getCurrentPlayer().setLastRoll((short) totalRoll); // save last roll for utilities rent calculation

        Player player = playingBoard.getCurrentPlayer();
        Property property = player.getCurrentTile(playingBoard);
        String streetName = property.getStreetName();
        Action action = property.getAction();
        rollDiceLabel.setText("Player" + (playingBoard.getCurrentPlayerNo() + 1) + " has rolled a " + firstRoll + " and a " + secondRoll + " and moved to " + streetName + ".");

        Card card = null;
        String actionType = null;
        if (action != null) {
            actionType = action.getActionType();

        }

        // if the card is a normal property (or one of the utilities, then don't perform action
        if (actionType != null && !actionType.equals("RollMultiple")) {
            // property action
            switch (actionType) {
                case "PotLuck":
                    card = takeCard("PotLuck", Board.getPlayers(), playingBoard);
                    rollDiceActionLabel.setText("Action: " + actionType + " Picked Card: " + card.getDescription());
                    break;
                case "Opportunity":
                    card = takeCard("Opportunity", Board.getPlayers(), playingBoard);
                    rollDiceActionLabel.setText("Action: " + actionType + " Picked Card: " + card.getDescription());
                    break;
                case "PayBank":
                case "Collect":
                case "FreeParking":
                    property.getAction().performAction(Board.getPlayers(), playingBoard);
                    rollDiceActionLabel.setText("Performed Action: " + actionType);
                    break;
                case "Jail":
                    rollDiceButton.setDisable(true);
                    endTurnButton.setDisable(false);
                    //endTurnButton.setVisible(true);
                    property.getAction().performAction(Board.getPlayers(), playingBoard);
                    rollDiceActionLabel.setText("Performed Action: " + actionType);
                    break;
            }
        }
        if (card != null) {
            if (Objects.equals(card.getAction().getActionType(), "FineOpp")) {
                pay10Button.setDisable(false);
                opportunityKnockButton.setDisable(false);
                rollDiceButton.setDisable(true);
                endTurnButton.setDisable(true);
                potLuckChoiceLabel.setText(" Pay a fine or take a Opportunity");
            }
            if (Objects.equals(card.getAction().getActionType(), "MoveBack")) {
                playingBoard.movePlayerBack(card.getAction().getActionValue());
            }
        }
        Player owner = property.getOwner();
        if (owner != null) {
            // calculate any rent due
            short rent = property.getDueRent(player);
            if (player.getMoney() < rent) {
                player.setBankrupt(true);
            } else {
                player.payMoney(rent);
                owner.giveMoney(rent);
            }
        } else {
            if (!property.getGroup().equals("")) {
                if (player.isPassedGO()) {
                    buyPropertyButton.setDisable(false);
                    //buyPropertyButton.setVisible(true);
                }
            } else {
                buyPropertyButton.setDisable(true);
                //buyPropertyButton.setVisible(false);
            }


        }
        showPlayer(playingBoard.getCurrentPlayer());
        //rollDiceLabel.setText(player.status(playingBoard));

    }

    public Card takeCard(String cardType, List<Player> players, Board board) {
        Card card;
        Player player;
        if (Objects.equals(cardType, "PotLuck")) {
            card = playingBoard.potLuck.poll();
        } else {
            card = playingBoard.opportunity.poll();
        }
        player = board.getCurrentPlayer();
        String cardAction = "";
        if (card.action != null) {
            card.action.performAction(players, board);
            cardAction = card.getAction().getActionType();
        }
        if ("JailFree".equals(cardAction)) {  // don't put card to bottom of pack
            player.setNumJailFree((short) (player.getNumJailFree() + 1));
        } else { // ad card to bottom of pack queue
            if (Objects.equals(cardType, "PotLuck")) {
                playingBoard.potLuck.add(card);
            } else {
                playingBoard.opportunity.add(card);
            }
        }

        return card;
    }

    @FXML
    public void onBuyPropertyButtonClick() {
        Player player = playingBoard.getCurrentPlayer();
        Property property = player.getCurrentTile(playingBoard);
        if (playingBoard.buyProperty(property)) {
            buyPropertyButton.setDisable(true);
            buyPropertyLabel.setText(property.getStreetName() + " brought");
        } else {
            buyPropertyLabel.setText("Can't Buy " + property.getStreetName());
        }
        showPlayer(playingBoard.getCurrentPlayer());
    }

    @FXML
    public void onPay10ButtonClick() {
        if (playingBoard.getCurrentPlayer().getMoney() >= 10) {
            playingBoard.getCurrentPlayer().payMoney((short) 10);
            playingBoard.bank.giveMoney((short) 10);
            pay10Button.setDisable(true);
            opportunityKnockButton.setDisable(true);
            potLuckChoiceLabel.setText(" £10 Paid");
            playingBoard.getCurrentPlayer().payMoney((short) 10);
            playingBoard.bank.giveMoney((short) 10);

        }
        showPlayer(playingBoard.getCurrentPlayer());

        endTurnButton.setDisable(false);


    }

    @FXML
    public void onOpportunityKnockButtonClick() {
        Card card;
        card = takeCard("Opportunity", Board.getPlayers(), playingBoard);
        opportunityKnockButton.setDisable(true);
        pay10Button.setDisable(true);
        potLuckChoiceLabel.setText(card.getDescription() + " picked");
        card.getAction().performAction(Board.getPlayers(), playingBoard);
        showPlayer(playingBoard.getCurrentPlayer());
        endTurnButton.setDisable(false);
    }

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

    @FXML
    public void onAddBuildingButtonClick() {
        addBuildingTextField.setDisable(false);
        String text = addBuildingTextField.getText();

        if (text.length() == 0) {
            addBuildingLabel.setText("No street name Entered");
        }

        Property[] tiles = Board.getTiles();
        Property property = null;
        for (Property tile : tiles) {
            if (tile.getStreetName().equalsIgnoreCase(text)) {
                property = tile;
            }
        }
        addBuildingTextField.setDisable(false);
        if (property == null) {
            addBuildingLabel.setText("Property not found");
            return;
        }
        if (property.getOwner() != playingBoard.getCurrentPlayer()) {
            addBuildingLabel.setText("you don't own that");
            return;
        }
        if (!property.build(playingBoard.getCurrentPlayer(), playingBoard.bank)) {
            addBuildingLabel.setText("build failed");
        } else {
            addBuildingLabel.setText(" Build Successful");
        }
        addBuildingButton.setDisable(true);
        showPlayer(playingBoard.getCurrentPlayer());
    }

    public void showPlayer(Player player) {
        int i = player.fetchPlayerNum();
        switch (i) {
            case 1:
                player1Label.setText(player.status(playingBoard));
                break;
            case 2:
                player2Label.setText(player.status(playingBoard));
                break;
            case 3:
                player3Label.setText(player.status(playingBoard));
                break;
            case 4:
                player4Label.setText(player.status(playingBoard));
                break;
            case 5:
                player5Label.setText(player.status(playingBoard));
                break;
        }
    }

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
    }

    public void fillBoard() {
        Property[] tiles = Board.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            Property tile = tiles[i];
            String group = tile.getGroup();
            String name = tile.getStreetName();
            String cost = "£" + tile.getCost();

            //Name
            boardNameLabels[i].setText(name);
            boardNameLabels[i].setFont(new Font(11));

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

            //Owner - todo if needed here
            //if (tile.getOwner() != null) {
            //    boardOwnerLabels[i].setText("Player" + tile.getOwner().fetchPlayerNum());
            //}

            //Cost
            if (boardCostLabels[i] != null) {
                boardCostLabels[i].setText(cost);
            }
        }
    }

    public boolean isLabel(Object object) {
        return object.getClass().getSimpleName().equals("Label");
    }

    public boolean isStackPane(Object object) {
        return object.getClass().getSimpleName().equals("StackPane");
    }

    @FXML
    public void onSellPropertyButtonClick() {

    }
}


