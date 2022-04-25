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
        Short houses;
        Short hotels;
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
                player.payMoney(this.actionValue);
                board.bank.giveMoney(this.actionValue);
                break;
            case "GoForward":
                short curPos = (short) player.getCurrentPos();
                player.setCurrentPos(this.actionValue);
                if (curPos > this.actionValue) {
                    player.giveMoney((short) 200);  // player passed GO
                }
                break;
            case "FineOpp":
                // to do
                return (1);
            case "PayFree":
                player.payMoney(this.actionValue);
                board.freeParking.addMoney(this.actionValue);
                break;
            case "Jail":
                player.setCurrentPos(10);
                player.setInJail(true);
                break;
            case "CollectPlayer":
                int currentPlayerNo = board.getCurrentPlayerNo();
                for (int i = 0; i < board.getNumPlayers(); i++) {
                    if (i != currentPlayerNo) {
                        players.get(i).payMoney(this.actionValue);
                        players.get(currentPlayerNo).giveMoney(this.actionValue);
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
                houses = player.getHouses(board, player);
                hotels = player.getHotels(board, player);
                rent = ((houses * 40) + (hotels * 115));
                player.payMoney((short) rent);
                board.bank.giveMoney((short) rent);
                break;
            case "HouseRepair2":
                houses = player.getHouses(board, player);
                hotels = player.getHotels(board, player);
                rent = ((houses * 25) + (hotels * 100));
                player.payMoney((short) rent);
                board.bank.giveMoney((short) rent);
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
