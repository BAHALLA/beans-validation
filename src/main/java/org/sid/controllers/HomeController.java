package org.sid.controllers;

import org.sid.dao.UserRepository;
import org.sid.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    private UserRepository userRepository;
    public HomeController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> save(@Valid @RequestBody User user){
        userRepository.save(user);
        return ResponseEntity.ok("user persisted successfully");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleUserValidationException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error-> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        return errors;
    }
}
