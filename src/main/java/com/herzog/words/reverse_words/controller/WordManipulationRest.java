package com.herzog.words.reverse_words.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.herzog.words.reverse_words.model.ManipulatedString;
import com.herzog.words.reverse_words.service.WordManipulationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class WordManipulationRest {

    @Autowired
    private WordManipulationService srv;

    @PostMapping("/reverseWords")
    public List<ManipulatedString> reverseString(HttpServletRequest request, @RequestBody ManipulatedString strToBeReversed){
            System.out.println("Processing a POST in /reverseWords");
            strToBeReversed.setApiUsed(request.getRequestURL().toString());
           strToBeReversed = srv.reverseString(strToBeReversed);
  
        return srv.getLastTenReversedStrings();//new ResponseEntity<>(srv.getLastTenReversedStrings(), HttpStatus.CREATED);
    }

    //watch -n 0.1 http GET http://localhost:8765/reverseString
    @GetMapping("/reverseWords")
    public List<ManipulatedString> getLastTenReversedStrings(){
        System.out.println("Processing a GET in /reverseWords");
        return srv.getLastTenReversedStrings();
    } 

    //DELETE
    @DeleteMapping("/reverseWords")
    public List<ManipulatedString> deleteAllManipulatedStrings(){
        System.out.println("Processing a DELETE in /reverseWords");

        srv.deleteAllManipulatedStrings();

        return srv.getLastTenReversedStrings();

    }
}
