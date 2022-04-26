package us.group41.propertytycoon;

import java.util.List;

public class Action {

    String actionType;
    Short actionValue;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Short getActionValue() {
        return actionValue;
    }

    public void setActionValue(Short actionValue) {
        this.actionValue = actionValue;
    }

    public short performAction(List<Player> players, Board board) {
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
                    player.setBankrupt(true);
                } else {
                    player.payMoney(this.actionValue);
                    board.bank.giveMoney(this.actionValue);
                }

                break;
            case "GoForward":
                short curPos = (short) player.getCurrentPos();
                player.setCurrentPos(this.actionValue);
                if (curPos > this.actionValue) {
                    player.giveMoney((short) 200);  // player passed GO
                    player.setPassedGO(true);
                }
                break;
            case "FineOpp":
                // handled in controller
                break;
            case "PayFree":
                if (player.getMoney() < this.actionValue) {
                    player.setBankrupt(true);
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
                        if (players.get(i).getMoney() < this.actionValue) {
                            players.get(i).setBankrupt(true);
                        } else {
                            players.get(i).payMoney(this.actionValue);
                            players.get(currentPlayerNo).giveMoney(this.actionValue);
                        }
                    }
                }
            case "JailFree":
                // add +1 to players num jail cards.
                // do not put card on back of pack
                break;
            case "Opportunity":
            case "PotLuck":
            case "VisitJail":
                break;
            //break;
            case "RollMultiple":
                break;
            case "FreeParking":
                player.giveMoney(board.freeParking.getMoney());
                break;
            case "HouseRepair1":
                houses = player.getHouses(player);
                hotels = player.getHotels(player);
                rent = ((houses * 40) + (hotels * 115));
                if (player.getMoney() < this.actionValue) {
                    player.setBankrupt(true);
                } else {
                    player.payMoney((short) rent);
                    board.bank.giveMoney((short) rent);
                }
                break;
            case "HouseRepair2":
                houses = player.getHouses(player);
                hotels = player.getHotels(player);
                rent = ((houses * 25) + (hotels * 100));
                if (player.getMoney() < this.actionValue) {
                    player.setBankrupt(true);
                } else {
                    player.payMoney((short) rent);
                    board.bank.giveMoney((short) rent);
                }
                break;
            case "MoveBack":
                // to do
                break;
            default:
                System.err.println("unknown action " + this.actionType);
                break;
        }

        return 0;
    }
}
