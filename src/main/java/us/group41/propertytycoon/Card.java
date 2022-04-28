package us.group41.propertytycoon;
/**
 * This class is used to model the cards and their actions.
 *
 * @author 235288
 * @version 0.5.1
 * @since 29/04/2022
 */
public class Card {
    String description;
    Action action;

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description setter
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return action
     */
    public Action getAction() {
        return this.action;
    }

    /**
     * @param action setter
     */
    public void setAction(Action action) {
        this.action = action;
    }
}
