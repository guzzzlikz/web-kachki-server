package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("{userId}/info")
    public ResponseEntity<?> info(@PathVariable long userId) {
        return accountService.getUser(userId);
    }
    @PostMapping("{userId}/description")
    public ResponseEntity<?> changeDescription(@PathVariable long userId, @RequestParam String newDescription) {
        return accountService.changeDescription(userId, newDescription);
    }
    @PostMapping("{userId}/name")
    public ResponseEntity<?> changeName(@PathVariable long userId, @RequestParam String newName) {
        return accountService.changeName(userId, newName);
    }
    @GetMapping("{userId}/courses")
    public ResponseEntity<?> getCourses(@PathVariable long userId) {
        return accountService.getCourses(userId);
    }
    @PostMapping("{userId}/inst")
    public ResponseEntity<?> changeInstagram(@PathVariable long userId, @RequestParam String instUrl) {
        return accountService.changeInstagram(userId, instUrl);
    }
    @PostMapping("{userId}/facebook")
    public ResponseEntity<?> changeFacebook(@PathVariable long userId, @RequestParam String facebookUrl) {
        return accountService.changeFacebook(userId, facebookUrl);
    }
    @PostMapping("{userId}/linked")
    public ResponseEntity<?> changedLinkedIn(@PathVariable long userId, @RequestParam String linkedInUrl) {
        return accountService.changeLinkedIn(userId, linkedInUrl);
    }
    @PostMapping("{userId}/telegram")
    public ResponseEntity<?> changeTelegram(@PathVariable long userId, @RequestParam String telegramUrl) {
        return accountService.changeTelegram(userId, telegramUrl);
    }
    @PostMapping("{userId}/weight")
    public ResponseEntity<?> changeWeight(@PathVariable long userId, @RequestParam double weight) {
        return accountService.changeWeight(userId, weight);
    }
    @PostMapping("{userId}/age")
    public ResponseEntity<?> changeAge(@PathVariable long userId, @RequestParam int age) {
        return accountService.changeAge(userId, age);
    }
    @PostMapping("{userId}/height")
    public ResponseEntity<?> changeHeight(@PathVariable long userId, @RequestParam double height) {
        return accountService.changeHeight(userId, height);
    }
}
