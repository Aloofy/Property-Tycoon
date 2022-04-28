package us.group41.propertytycoon;

/**
 * This class is used to model the bank which stores and distributes money throughout the game.
 *
 * @author 235288
 * @version 0.5.1
 * @since 29/04/2022
 */
public class Bank {
    private int money;

    /**
     * @param money setter
     */
    public void setMoney(Integer money) {
        this.money = money;
    }

    /**
     * Take money out of bank
     *
     * @param amount of money to take out
     */
    public void payMoney(short amount) {
        money -= amount;
    }

    /**
     * Give money to bank
     *
     * @param amount to give to bank.
     */
    public void giveMoney(short amount) {
        money += amount;
    }
}
