package us.group41.propertytycoon;


import java.io.IOException;

public class Property {
    private String streetname = null;
    private Short cost = null;

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
    private Short owner = 0;

    public String getStreetname() {
        return this.streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public Short getcost() {
        return this.cost;
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

    public Short getOwner() {
        return this.owner;
    }

    public void setOwner(Short owner) {
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
        return "\nStreetname: " + streetname + "cost: " + cost + "\n";
    }


}
