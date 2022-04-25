package us.group41.propertytycoon;

public class Card {
    String description;
    Action action;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Action getAction() {
        return this.action;
    }


    public void setAction(Action action) {

        this.action = action;
    }
}
