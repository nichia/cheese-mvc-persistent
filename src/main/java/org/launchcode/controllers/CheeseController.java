package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private MenuDao menuDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, @RequestParam int categoryId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/add";
        }

        Category cate = categoryDao.findOne(categoryId);
        newCheese.setCategory(cate);
        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "category", method = RequestMethod.GET)
    public String category(Model model, @RequestParam int id) {
        Category cate = categoryDao.findOne(id);
        List<Cheese> cheeses = cate.getCheeses();
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "Cheeses in Category: " + cate.getName());
        return "cheese/index";
    }

    @RequestMapping(value = "menu", method = RequestMethod.GET)
    public String menu(Model model, @RequestParam int id) {
        Menu menuItem = menuDao.findOne(id);
        List<Cheese> cheeses = menuItem.getCheeses();
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "Cheeses in Menu: " + menuItem.getName());
        return "cheese/index";
    }

    @RequestMapping(value="edit/{id}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int id) {
        // model.addAttribute("cheese", CheeseData.getById(cheeseId));
        model.addAttribute("cheese", cheeseDao.findOne(id));
        model.addAttribute("title", "Edit Cheese");
        model.addAttribute("categories", categoryDao.findAll());

        return "cheese/edit";
    }

    @RequestMapping(value="edit/{id}", method = RequestMethod.POST)
    public String processEditForm(@ModelAttribute @Valid Cheese editedCheese, Errors errors, @RequestParam int categoryId, @RequestParam int cheeseId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/edit";
        }

        // Update an instance of cheese

        Cheese theCheese = cheeseDao.findOne(cheeseId);
        theCheese.setName(editedCheese.getName());
        theCheese.setDescription(editedCheese.getDescription());

        Category cate = categoryDao.findOne(categoryId);
        theCheese.setCategory(cate);

        cheeseDao.save(theCheese);

        return "redirect:/cheese";
    }

}
