package org.launchcode.models.forms;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddMenuItemForm {
    private Menu menu;
    private Iterable<Cheese> cheeses;

    @NotNull
    @Min(value=1, message = "Please select a menu")
    private int menuId;

    @NotNull
    @Min(value=1, message = "Please select a Cheese item")
    private int cheeseId;

    public Menu getMenu() {
        return menu;
    }

    public AddMenuItemForm() {}

    public AddMenuItemForm(Menu menu, Iterable<Cheese> cheeses) {
        this.menu = menu;
        this.cheeses = cheeses;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Iterable<Cheese> getCheeses() {
        return cheeses;
    }

    public void setCheeses(Iterable<Cheese> cheeses) {
        this.cheeses = cheeses;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getCheeseId() {
        return cheeseId;
    }

    public void setCheeseId(int cheeseId) {
        this.cheeseId = cheeseId;
    }
}
