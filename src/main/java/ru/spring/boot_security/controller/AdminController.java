package ru.spring.boot_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spring.boot_security.model.User;
import ru.spring.boot_security.service.UserService;

import java.util.Arrays;


@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
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
    public String createUser(@ModelAttribute("user") User user, @RequestParam("role[]") String[] roleNameList) {
        userService.add(user, Arrays.asList(roleNameList));
        return "redirect:/admin";
    }

    @GetMapping("/updateUserPage")
    public String updateUserPage(@ModelAttribute("user") User user) {
        return "updateUserPage";
    }

    @PatchMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("role[]") String[] roleNameList) {
        userService.update(user, Arrays.asList(roleNameList));
        return "redirect:/admin";
    }

    @GetMapping("/deleteUserPage")
    public String deleteUserPage() {
        return "deleteUserPage";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}