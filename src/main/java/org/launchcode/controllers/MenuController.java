package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    // Request path: /menu
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "Menus");
        model.addAttribute("menus", menuDao.findAll());

        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Menu());
        model.addAttribute("title", "Add Menu");

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Valid Menu newMenu,
                      Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

//        for (Cheese aCheese : newMenu.getCheeses()) {
//            Cheese theCheese = cheeseDao.findOne(aCheese.getId());
//            newMenu.addItem(theCheese);
//        }

        menuDao.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id) {

        Menu menu = menuDao.findOne(id);
        model.addAttribute("title", menu.getName());
        model.addAttribute("menu", menu);

        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int id) {
        Menu menu = menuDao.findOne(id);
        AddMenuItemForm instance = new AddMenuItemForm(menu, cheeseDao.findAll());
        model.addAttribute("form", instance);
        model.addAttribute("title", "Add item to menu: " + menu.getName());

        return "menu/add-item";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid AddMenuItemForm menuItemForm,
                      Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add item to menu: " + menuItemForm.getMenu().getName());
            return "menu/add";
        }

        // Add the item to the menu
        Menu theMenu = menuDao.findOne(menuItemForm.getMenuId());
        Cheese theCheese = cheeseDao.findOne(menuItemForm.getCheeseId());
        theMenu.addItem(theCheese);
        menuDao.save(theMenu);
        return "redirect:/menu/view/" + theMenu.getId();
    }
}
