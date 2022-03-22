package us.group41.propertytycoon;

public class FreeParking {

    private Integer money = 0;

    public Integer getMoney() {
        Integer total = money;
        money = 0;
        return total;
    }

    public void addMoney(Short amount) {
        money = money + amount;
    }

    @Override
    public String toString() {
        return "\nmoney: " + money + "\n";
    }

}
