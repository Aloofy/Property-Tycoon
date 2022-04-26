package us.group41.propertytycoon;

public class FreeParking {

    private Short money = 0;

    public Short getMoney() {
        Short total = money;
        money = 0;
        return total;
    }

    public void addMoney(Short amount) {
        money = (short) (money + amount);
    }

    @Override
    public String toString() {
        return "\nmoney: " + money + "\n";
    }
}
