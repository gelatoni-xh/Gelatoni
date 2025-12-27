package com.csxuhuan.gelatoni.interfaces.web.user;

import com.csxuhuan.gelatoni.application.service.user.UserService;
import com.csxuhuan.gelatoni.domain.model.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 * 提供用户相关的REST API接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> list() {
        return service.getAllUsers();
    }

    @PostMapping
    public void add(@RequestBody User user) {
        service.addUser(user);
    }

}

