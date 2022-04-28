package us.group41.propertytycoon;


import java.util.Objects;
/**
 * This class is used to model the property tiles.
 *
 * @author 235288
 * @version 0.5.1
 * @since 29/04/2022
 */
public class Property {
    private String streetName = null;
    //the money needed to buy the property
    private short cost;
    private boolean mortgaged = false;
    private Action action;
    private String group = null;
    private short rent;
    private short houseCost;
    private short hotelCost;
    private short rent1;
    private short rent2;
    private short rent3;
    private short rent4;
    private short rentH;
    private short numHouses = 0;
    private Player owner = null;
    private short numHotels = 0;
    private boolean allPropertiesOwnedByOwner = false;
    private short propertyNo;

    /**
     * @return property numeric location on the board
     */
    public short getPropertyNo() {
        return propertyNo;
    }

    /**
     * @param propertyNo sets the property's numeric location on the board
     */
    public void setPropertyNo(short propertyNo) {
        this.propertyNo = propertyNo;
    }


    /**
     * @return the property's action
     */
    public Action getAction() {
        return this.action;
    }

    /**
     * @param action sets the property's action
     */
    public void setAction(Action action) {
        this.action = action;
    }


    /**
     * @return the property's street name
     */
    public String getStreetName() {
        return this.streetName;
    }

    /**
     * @param streetName sets the property's street name
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * @return the number of hotels on the property
     */
    public short getNumHotels() {
        return numHotels;
    }

    /**
     * @param numHotels sets the number of hotels on the property
     */
    public void setNumHotels(short numHotels) {
        this.numHotels = numHotels;
    }

    /**
     * @return true if all the properties in its group are owned by the same player
     */
    public boolean isAllPropertiesOwnedByOwner() {
        return allPropertiesOwnedByOwner;
    }

    /**
     * @param allPropertiesOwnedByOwner sets to true if ll properties in the group are owned by the same player
     */
    public void setAllPropertiesOwnedByOwner(boolean allPropertiesOwnedByOwner) {
        this.allPropertiesOwnedByOwner = allPropertiesOwnedByOwner;
    }

    /**
     * Buys a property on the board.
     *
     * @param player the player buying the property
     * @param bank   the bank the money goes to
     * @return if it was successfully brought
     */
    public boolean buyProperty(Player player, Bank bank) {
        boolean result = false;
        if (this.owner == null && !Objects.equals(this.group, "")) {
            if (player.isPassedGO()) {
                short cost = this.getCost();
                if (player.getMoney() >= cost) {
                    player.payMoney(cost);
                    bank.giveMoney(cost);
                    this.setOwner(player);
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * builds a house or hotel on the property
     *
     * @param player the player trying to build on the property
     * @param bank   the bank to pay money to for building
     * @return true if success
     */
    public boolean build(Player player, Bank bank) {
        boolean result = false;
        short houseCost;
        if (this.owner == player && this.allPropertiesOwnedByOwner) {
            short minHouses = 0;
            //look for the minimum number of houses on the other properties in the same group
            Property[] tiles = Board.getTiles();
            for (Property tile : tiles) {
                if (Objects.equals(this.group, tile.group) && tile != this) {
                    if (minHouses < tile.getNumHouses()) {
                        minHouses = tile.getNumHouses();
                    }
                }
            }
            if (this.numHouses == 4) {
                // buying a hotel
                short hotelCost;
                hotelCost = this.getHotelCost();
                if (player.getMoney() >= hotelCost) {
                    if (this.getNumHotels() != 1) {
                        player.payMoney(hotelCost);
                        bank.giveMoney(hotelCost);
                        this.setNumHotels((short) 1);
                        result = true;
                    }
                }
            } else {
                // buying a house
                short numHouses = this.getNumHouses();
                // you are trying to add a house which will result in a tile with 2 more houses than the rest
                if (numHouses <= minHouses) {
                    houseCost = this.getHouseCost();
                    if (player.getMoney() >= houseCost) {
                        player.payMoney(houseCost);
                        bank.giveMoney(houseCost);
                        this.setNumHouses((short) (numHouses + 1));
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Sells the highest value thing on the property, so hotel > 4 houses > 3 houses > 2 houses > 1 house > regular property > mortgaged > sold property
     *
     * @return money made from the sale of assets
     */
    public short sellProperty() {
        short result;
        short minHouses = 0;
        short noHouses;
        short noHotels;
        noHouses = this.getNumHouses();
        noHotels = this.getNumHotels();
        if (noHotels > 0) {
            this.setNumHotels((short) 0);
            result = (this.hotelCost);
        } else {
            Property[] tiles = Board.getTiles();// find minimum no houses in set
            for (Property tile : tiles) {
                if (Objects.equals(this.group, tile.group) && tile != this) {
                    if (minHouses < tile.getNumHouses()) {
                        minHouses = tile.getNumHouses();
                    }
                }
            }
            if (noHouses > 0) {
                // you can't sell a house to make the number of houses of this property more than one less than the min houses
                if (noHouses - 1 < minHouses - 1) {
                    result = (short) 0;
                } else {
                    this.setNumHouses((short) (this.getNumHouses() - 1));
                    result = this.getHouseCost();
                }
            } else if (minHouses > 0) {
                // you can't mortgage the house. sell the other houses first on the other properties in the group
                result = ((short) 0);
            } else {
                if (this.isMortgaged()) {
                    // sell it
                    this.setOwner(null);
                    this.setMortgaged(false);
                } else {
                    // mortgage it
                    this.setMortgaged(true);
                }
                result = (short) (this.cost / 2);
            }
        }
        return result;
    }

    /**
     * gets the rent due when a player lands on a square
     *
     * @param player the player who landed on the property
     * @return rent due
     */
    public short getDueRent(Player player) {
        short result = 0;
        boolean finished = false;
        String actionType;
        //find if the property has the RollMultiple action type
        Action action = this.getAction();
        if (action != null) {
            actionType = action.getActionType();
        } else {
            actionType = "";
        }
        String group = this.getGroup();
        Property[] tiles = Board.getTiles();
        if (!player.getInJail()) {
            if (!this.isMortgaged()) {
                if (group.equals("Station")) {
                    int numOwned = 0;
                    for (Property tile : tiles) {
                        if (tile.getOwner() == this.owner && Objects.equals(tile.getGroup(), "Station")) {
                            numOwned++;
                        }
                    }
                    switch (numOwned) {
                        case 1 -> {
                            result = rent1;
                            finished = true;
                        }
                        case 2 -> {
                            result = rent2;
                            finished = true;
                        }
                        case 3 -> {
                            result = rent3;
                            finished = true;
                        }
                        case 4 -> {
                            result = rent4;
                            finished = true;
                        }
                    }
                }
                if (!finished) {
                    //these are the properties from the utilities group
                    if (actionType.equals("RollMultiple")) {
                        short lastRoll = player.getLastRoll();
                        if (this.isAllPropertiesOwnedByOwner()) {
                            result = (short) (lastRoll * 10);
                        } else {
                            result = (short) (lastRoll * 4);
                        }
                    } else if (this.getNumHotels() > 0) {
                        result = this.getRentH();
                    } else if (this.getNumHouses() == 4) {
                        result = this.getRent4();
                    } else if (this.getNumHouses() == 3) {
                        result = this.getRent3();
                    } else if (this.getNumHouses() == 2) {
                        result = this.getRent2();
                    } else if (this.getNumHouses() == 1) {
                        result = this.getRent1();
                    } else if (allPropertiesOwnedByOwner) {
                        result = (short) (this.getRent() * 2);
                    } else {
                        result = this.getRent();
                    }
                }
            }
        }
        return result;
    }

    /**
     * @return property cost
     */
    public short getCost() {
        return this.cost;
    }

    /**
     * @param cost sets the property cost
     */
    public void setCost(short cost) {
        this.cost = cost;
    }

    /**
     * @return property's group
     */
    public String getGroup() {
        return this.group;
    }

    /**
     * @param group sets the property's group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return gets the properties owner
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * @param owner sets the properties owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * @return gets the property's rent with no houses or hotels
     */
    public short getRent() {
        return this.rent;
    }

    /**
     * @param rent sets the property's rent with no houses
     */
    public void setRent(short rent) {
        this.rent = rent;
    }


    /**
     * @return the cost to buy a house on the property
     */
    public short getHouseCost() {
        return this.houseCost;
    }

    /**
     * @param houseCost sets the cost to buy a house on the property
     */
    public void setHouseCost(short houseCost) {
        this.houseCost = houseCost;
    }

    /**
     * @return the cost to buy a hotel on the property
     */
    public short getHotelCost() {
        return this.hotelCost;
    }

    /**
     * @param hotelCost sets the cost to buy a hotel on the property
     */
    public void setHotelCost(short hotelCost) {
        this.hotelCost = hotelCost;
    }


    /**
     * @return gets the rent with one house
     */
    public short getRent1() {
        return this.rent1;
    }

    /**
     * @param rent1 sets the rent with one house
     */
    public void setRent1(short rent1) {
        this.rent1 = rent1;
    }

    /**
     * @return the rent with two houses
     */
    public short getRent2() {
        return this.rent2;
    }

    /**
     * @param rent2 sets the rent with two houses
     */
    public void setRent2(short rent2) {
        this.rent2 = rent2;
    }

    /**
     * @return the rent with 3 houses
     */
    public short getRent3() {
        return this.rent3;
    }

    /**
     * @param rent3 sets the rent with 3 houses
     */
    public void setRent3(short rent3) {
        this.rent3 = rent3;
    }

    /**
     * @return the rent with 4 houses
     */
    public short getRent4() {
        return this.rent4;
    }

    /**
     * @param rent4 sets the rent with 4 houses
     */
    public void setRent4(short rent4) {
        this.rent4 = rent4;
    }

    /**
     * @return the rent with a hotel
     */
    public short getRentH() {
        return this.rentH;
    }

    /**
     * @param rentH sets the rent with a hotel
     */
    public void setRentH(short rentH) {
        this.rentH = rentH;
    }

    /**
     * @return gets the number of houses on the property
     */
    public short getNumHouses() {
        return this.numHouses;
    }

    /**
     * @param numHouses sets the number of houses on the property
     */
    public void setNumHouses(short numHouses) {
        this.numHouses = numHouses;
    }

    /**
     * @return if the property is mortgaged
     */
    public boolean isMortgaged() {
        return mortgaged;
    }

    /**
     * @param mortgaged sets if the property is mortgaged
     */
    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    /**
     * @return toString
     */
    @Override
    public String toString() {
        return "\nStreet Name: " + streetName + "cost: " + cost + "\n";
    }
}
