package us.group41.propertytycoon;

import java.util.List;

/**
 * This class is used for game instructions either from a card or from a property.
 *
 * @author 235288
 * @version 0.5.1
 * @since 29/04/2022
 */
public class Action {
    String actionType;
    short actionValue;

    /**
     * @return actionType
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * @param actionType setter
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }


    /**
     * @return actionValue
     */
    public short getActionValue() {
        return actionValue;
    }

    /**
     * @param actionValue setter
     */
    public void setActionValue(short actionValue) {
        this.actionValue = actionValue;
    }

    /**
     * Perform the game action.
     *
     * @param players Some actions like get £10 from each player requires access to the list of players.
     * @param board   Some actions like rent or houses require access to the board.
     * @return If there's a debt, it returns that as a short.
     */
    public short performAction(List<Player> players, Board board) {
        short result = 0;
        Player player;
        player = board.getCurrentPlayer();
        short houses;
        short hotels;
        int rent;
        switch (this.actionType) {
            case "Collect":
                board.bank.payMoney(this.actionValue);
                player.giveMoney(this.actionValue);
                break;
            case "GoBack":
                player.setCurrentPos(this.actionValue);
                break;
            case "PayBank":
                if (player.getMoney() < this.actionValue) {
                    result = this.actionValue;
                    break;
                } else {
                    player.payMoney(this.actionValue);
                    board.bank.giveMoney(this.actionValue);
                }
                break;
            case "GoForward":
                short curPos = (short) player.getCurrentPos();
                player.setCurrentPos(this.actionValue);
                if (curPos > this.actionValue) {
                    board.bank.payMoney((short) 200);
                    player.giveMoney((short) 200);  // player passed GO
                    player.setPassedGO(true);
                }
                if (player.getCurrentPos() == 0) {  //only case where a go forward goes to a square with an action at the moment.
                    board.bank.payMoney((short) 200);
                    player.giveMoney((short) 200);
                }
                break;
            case "PayFree": //pay free parking
                if (player.getMoney() < this.actionValue) {
                    result = this.actionValue;
                    break;
                } else {
                    player.payMoney(this.actionValue);
                    board.freeParking.addMoney(this.actionValue);
                }
                break;
            case "Jail":
                player.setCurrentPos(10);
                player.setInJail(true);
                player.setRollDoubleTimes((short) (0));  // reset number of doubles
                break;
            case "CollectPlayer":
                int currentPlayerNo = board.getCurrentPlayerNo();
                for (int i = 0; i < board.getNumPlayers(); i++) {
                    if (i != currentPlayerNo) {
                        if (players.get(i).getMoney() >= this.actionValue && !players.get(i).isBankrupt()) {  //decided not to make bankrupt on birthday present
                            players.get(i).payMoney(this.actionValue);
                            players.get(currentPlayerNo).giveMoney(this.actionValue);
                        }
                    }
                }
            case "FreeParking":
                player.giveMoney(board.freeParking.getMoney());
                break;
            case "HouseRepair1":
                houses = player.getHouses(player);
                hotels = player.getHotels(player);
                rent = ((houses * 40) + (hotels * 115));
                if (player.getMoney() < rent) {
                    result = (short) rent;
                    break;
                } else {
                    player.payMoney((short) rent);
                    board.bank.giveMoney((short) rent);
                }
                break;
            case "HouseRepair2":
                houses = player.getHouses(player);
                hotels = player.getHotels(player);
                rent = ((houses * 25) + (hotels * 100));
                if (player.getMoney() < rent) {
                    result = (short) rent;
                    break;
                } else {
                    player.payMoney((short) rent);
                    board.bank.giveMoney((short) rent);
                }
                break;
            case "FineOpp": //used for taking fines or opportunity knocks, handled in controller
            case "JailFree": //add +1 to players num jail cards. do not put card on back of pack. Handled in controller
            case "Opportunity": //cards handled in take card
            case "PotLuck": //cards handled in take card
            case "VisitJail": //just visiting so no action
            case "RollMultiple": //just used to handle utilities rental calculation
            case "MoveBack": //Move back a number of spaces, handled in controller
                break;
            default:
                System.err.println("unknown action " + this.actionType);
                break;
        }
        return result;
    }
}