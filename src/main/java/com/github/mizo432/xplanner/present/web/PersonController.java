package com.github.mizo432.xplanner.present.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.mizo432.xplanner.buisiness.model.Person;
import com.github.mizo432.xplanner.buisiness.model.repository.PersonRepository;

import java.util.List;

/**
 * 人物（ユーザー）関連のコントローラー。
 */
@Controller
@RequestMapping("/people")
public class PersonController extends AbstractController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public String listPeople(Model model) {
        model.addAttribute("people", personRepository.selectOrderByName());
        return "view/people";
    }

    @GetMapping("/{id}")
    public String viewPerson(@PathVariable Long id, Model model) {
        Person person = personRepository.findById(id).orElse(null);
        model.addAttribute("person", person);
        return "view/person";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        return "edit/editPerson";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Person person = personRepository.findById(id).orElse(null);
        model.addAttribute("person", person);
        return "edit/editPerson";
    }

    @PostMapping
    public String savePerson(@ModelAttribute Person person) {
        personRepository.save(person);
        return "redirect:/people";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
        return "redirect:/people";
    }
}
