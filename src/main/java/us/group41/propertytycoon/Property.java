package us.group41.propertytycoon;


import java.util.Objects;

public class Property {
    private String streetName = null;
    private Short cost = null;


    private Action action;
    private String group = null;
    private Short rent = null;
    private Short houseCost = null;
    private Short hotelCost = null;
    private Short rent1 = null;
    private Short rent2 = null;
    private Short rent3 = null;
    private Short rent4 = null;
    private Short rentH = null;
    private Short numHouses = 0;
    private Player owner = null;
    private Short numHotels = 0;
    private boolean allPropertiesOwnedByOwner;

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        this.action = action;
    }


    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Short getNumHotels() {
        return numHotels;
    }

    public void setNumHotels(Short numHotels) {
        this.numHotels = numHotels;
    }

    public boolean isAllPropertiesOwnedByOwner() {
        return allPropertiesOwnedByOwner;
    }

    public void setAllPropertiesOwnedByOwner(boolean allPropertiesOwnedByOwner) {
        this.allPropertiesOwnedByOwner = allPropertiesOwnedByOwner;
    }

    public boolean buyProperty(Player player, Bank bank) {

        if (this.owner != null || Objects.equals(this.group, "")) {
            return false;
        } else {
            if (!player.isPassedGO()) {
                return false;
            } else {
                short cost = this.getCost();
                if (player.getMoney() < cost) {
                    return false;
                } else {
                    player.payMoney(cost);
                    bank.giveMoney(cost);
                    this.setOwner(player);
                    return true;
                }
            }
        }

    }

    public boolean build(Player player, Bank bank) {
        short houseCost;
        if (owner != player || !allPropertiesOwnedByOwner) {
            return false;
        }
        short minHouses = 0;
        Property[] tiles = Board.getTiles();
        for (Property tile : tiles) {
            if (Objects.equals(this.group, tile.group)) {
                if (minHouses < tile.getNumHouses()) {
                    minHouses = tile.getNumHouses();
                }
            }
        }
        if (minHouses == 4) {
            // buying a hotel
            short hotelCost;
            hotelCost = this.getHotelCost();
            if (player.getMoney() < hotelCost) {
                return false;
            } else {
                if (this.getNumHotels() == 1) {
                    return false;
                } else {
                    player.payMoney(hotelCost);
                    bank.giveMoney(hotelCost);
                    this.setNumHotels((short) 1);
                    return true;
                }
            }
        } else {

            // buying a house
            short numHouses = this.getNumHouses();

            if (numHouses > minHouses + 1) {  // you are trying to add a house which will result in a tile with 2 more houses than the rest
                return false;
            } else {
                houseCost = this.getHouseCost();
                if (player.getMoney() < houseCost) {
                    return false;
                } else {
                    player.payMoney(houseCost);
                    bank.giveMoney(houseCost);
                    this.setNumHouses((short) (numHouses + 1));
                    return true;
                }
            }
        }
    }

    public short getDueRent(Player player) {

        String actionType;
        Action action = this.getAction();
        if (action != null) {
            actionType = action.getActionType();
        } else {
            actionType = "";
        }

        String group = this.getGroup();
        Property[] tiles = Board.getTiles();

        if (player.getInJail()) {
            return 0;
        }

        if (group.equals("Station")) {
            int numOwned = 0;
            for (Property tile : tiles) {
                if (tile.getOwner() == this.owner && Objects.equals(tile.getGroup(), "Station")) {
                    numOwned++;

                }
            }
            switch (numOwned) {
                case 1:
                    return rent1;
                case 2:
                    return rent2;
                case 3:
                    return rent3;
                case 4:
                    return rent4;
            }
        }
        if (actionType.equals("RollMultiple")) {
            short lastRoll = player.getLastRoll();
            if (this.isAllPropertiesOwnedByOwner()) {
                return (short) (lastRoll * 10);
            } else {
                return (short) (lastRoll * 4);
            }
        } else {
            if (this.getNumHotels() > 0) {
                return this.getRentH();
            } else if (this.getNumHouses() == 4) {
                return this.getRent4();
            } else if (this.getNumHouses() == 3) {
                return this.getRent3();
            } else if (this.getNumHouses() == 2) {
                return this.getRent2();
            } else if (this.getNumHouses() == 1) {
                return this.getRent1();
            } else {
                if (allPropertiesOwnedByOwner) {
                    return (short) (this.getRent() * 2);
                } else {
                    return this.getRent();
                }
            }
        }

    }

    public Short getCost() {
        return this.cost;
    }

    public void setCost(Short cost) {
        this.cost = cost;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Short getRent() {
        return this.rent;
    }

    public void setRent(Short rent) {
        this.rent = rent;
    }

    public Short getHouseCost() {
        return this.houseCost;
    }

    public void setHouseCost(Short houseCost) {
        this.houseCost = houseCost;
    }

    public Short getHotelCost() {
        return this.hotelCost;
    }

    public void setHotelCost(Short hotelCost) {
        this.hotelCost = hotelCost;
    }

    public Short getRent1() {
        return this.rent1;
    }

    public void setRent1(Short rent1) {
        this.rent1 = rent1;
    }

    public Short getRent2() {
        return this.rent2;
    }

    public void setRent2(Short rent2) {
        this.rent2 = rent2;
    }

    public Short getRent3() {
        return this.rent3;
    }

    public void setRent3(Short rent3) {
        this.rent3 = rent3;
    }

    public Short getRent4() {
        return this.rent4;
    }

    public void setRent4(Short rent4) {
        this.rent4 = rent4;
    }

    public Short getRentH() {
        return this.rentH;
    }

    public void setRentH(Short rentH) {
        this.rentH = rentH;
    }

    public Short getNumHouses() {
        return this.numHouses;
    }

    public void setNumHouses(Short numHouses) {
        this.numHouses = numHouses;
    }

    @Override
    public String toString() {
        return "\nStreet Name: " + streetName + "cost: " + cost + "\n";
    }
}
