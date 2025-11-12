package org.example.webkachkiserver.controllers;

import org.example.webkachkiserver.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/coach")
public class CoachController {
    @Autowired
    private CoachService coachService;
    @GetMapping("/index")
    public ResponseEntity<?> getCoaches() {
        return coachService.getCoachesForIndex();
    }
}
