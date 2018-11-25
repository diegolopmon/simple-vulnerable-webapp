package com.diegolopmon.simplevulnerablewebapp.controller;

import com.diegolopmon.simplevulnerablewebapp.domain.User;
import com.diegolopmon.simplevulnerablewebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Controller
public class MainController {

    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/search")
    public String searchUser(@RequestParam(value = "firstname") String firstName, @RequestParam(value = "lastname") String lastName, Model model) throws SQLException, ClassNotFoundException {

        List<User> userList = userService.search(firstName, lastName);

        model.addAttribute("userList", userList);
        return "search";
    }

}