package us.group41.propertytycoon;


public class Property {
    private String streetName = null;
    private Short cost = null;
    private Short actionValue;
    private String group = null;
    private String action = null;
    private Short rent = null;
    private Short houseCost = null;
    private Short hotelCost = null;
    private Short rent1 = null;
    private Short rent2 = null;
    private Short rent3 = null;
    private Short rent4 = null;
    private Short rentH = null;
    private Short numHouses = null;
    private Player owner = null;
    private Short numHotels = null;
    private boolean allPropertiesOwnedByOwner;

    public Short getActionValue() {
        return actionValue;
    }

    public void setActionValue(Short actionValue) {
        this.actionValue = actionValue;
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

        if (owner != null) {
            return false;
        } else {

            if (player.getMoney() < this.cost) {
                return false;
            } else {
                player.PayMoney(this.cost);
                bank.GiveMoney(this.cost);
                this.setOwner(player);
                return true;
            }


        }

    }

    public boolean buyHouse(Player player, Bank bank) {
        short houseCost;
        if (owner != player) {
            return false;
        } else {
            houseCost = this.getHouseCost();
            if (player.getMoney() < houseCost) {
                return false;
            } else {
                short numHouses = this.getNumHouses();
                if (numHouses == 4) {
                    return false;
                } else {
                    player.PayMoney(houseCost);
                    bank.GiveMoney(houseCost);
                    this.setNumHouses((short) (numHouses + 1));
                    return true;
                }
            }
        }
    }

    public boolean buyHotel(Player player, Bank bank) {
        short hotelCost;
        if (this.owner != player) {
            return false;
        } else {
            hotelCost = this.hotelCost;
            if (player.getMoney() < hotelCost) {
                return false;
            } else {
                short numHouses = this.getNumHouses();
                if (numHouses != 4) {
                    return false;
                } else {
                    if (this.getNumHotels() == 1) {
                        return false;
                    }

                    player.PayMoney(hotelCost);
                    bank.GiveMoney(hotelCost);
                    this.setNumHotels((short) 1);
                    return true;
                }
            }
        }
    }

    public short getDueRent() {
        short numHouses = this.numHouses;
        short numHotels = this.numHotels;
        if (this.numHotels > 0) {
            return this.rentH;
        } else if (this.numHouses == 4) {
            return this.rent4;
        } else if (this.numHouses == 3) {
            return this.rent3;
        } else if (this.numHouses == 2) {
            return this.rent2;
        } else if (this.numHouses == 1) {
            return this.rent1;
        } else {
            if (allPropertiesOwnedByOwner) {
                return (short) (this.rent * 2);
            } else {
                return this.rent;
            }

        }
    }

    public Short getCost() {
        return this.cost;
    }

    public void setCost(Short cost) {
        this.cost = cost;
    }

    public void setCost(short cost) {
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

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
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
