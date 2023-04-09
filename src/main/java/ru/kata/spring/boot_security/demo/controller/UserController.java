package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@RequestMapping("/main")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String viewUsers(Model model) {
        model.addAttribute("usersList", userService.getUsersList());
        return "main";
    }

    @GetMapping("/addUserPage")
    public String addUserPage(@ModelAttribute("user") User user) {
        return "addUserPage";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/main";
    }

    @GetMapping("/updateUserPage")
    public String updateUserPage(@ModelAttribute("user") User user) {
        return "updateUserPage";
    }

    @PatchMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/main";
    }

    @GetMapping("/deleteUserPage")
    public String deleteUserPage() {
        return "deleteUserPage";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(Long id) {
        userService.delete(id);
        return "redirect:/main";
    }
}