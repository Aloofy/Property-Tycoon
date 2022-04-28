package us.group41.propertytycoon;
/**
 * This class is used to model free parking, and it's ability to store money.
 *
 * @author 235288
 * @version 0.5.1
 * @since 29/04/2022
 */
public class FreeParking {
    private short money = 0;

    /**
     * Take all money from free parking.
     *
     * @return amount of money taken.
     */
    public short getMoney() {
        short total = money;
        money = 0;
        return total;
    }

    /**
     * add an amount of money to free parking.
     *
     * @param amount of money to be added.
     */
    public void addMoney(short amount) {
        money += amount;
    }

    @Override
    public String toString() {
        return "£" + money;
    }
}
