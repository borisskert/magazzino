package de.borisskert.magazzino.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final CurrentUserService userService;

    @Autowired
    public UserController(CurrentUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public User me() {
        return userService.getCurrentUser();
    }
}
