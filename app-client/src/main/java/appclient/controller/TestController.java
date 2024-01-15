package appclient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("You are authorized to access this endpoint. Data: Working as expected!");
    }
}