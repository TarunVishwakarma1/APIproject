package com.tv.apiproj.controller;

import com.tv.apiproj.dao.PersonDataDAO;
import com.tv.apiproj.service.ServiceClass;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ControllerClass {

    private ControllerClass(){}
    @Autowired
    ServiceClass serviceClass;
    @GetMapping("/")
    public String helloWorld(){
        return "<h1>Welcome User!</h1><p>Hello! Welcome to API proj Made by Tarun Vishwakarma.</p> <p>This is the demo line to check if the api is running or not.</p><p>your Application is running.</p>";
    }
    @GetMapping("/users/{pageNo}")
    public Object getUsers(@PathVariable int pageNo) {
        return serviceClass.getAllUsers(pageNo);
    }
    @GetMapping("/user/{userId}")
    public Object getUser(@PathVariable int userId){
        return serviceClass.getUser(userId);
    }
    @PostMapping("/addUser")
    public ResponseEntity<PersonDataDAO> addUser(@RequestBody PersonDataDAO personDataDAO){
        PersonDataDAO savedPerson = serviceClass.addUser(personDataDAO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPerson.getId()).toUri();
        return ResponseEntity.created(location).body(personDataDAO);
    }
    @DeleteMapping("/deleteUsers/{id}")
    public HttpStatusCode deleteUser(@PathVariable int id){
        int deletedId = serviceClass.deleteUser(id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(deletedId).toUri();
        return ResponseEntity.ok(location).getStatusCode();
    }
    @GetMapping("/allPerson")
    public List<PersonDataDAO> allPersonData(){
        return serviceClass.getAllPersonData();
    }
    @PutMapping("/editPerson")
    public ResponseEntity<String> putPerson(@RequestBody PersonDataDAO personDataDAO){
        return serviceClass.editPerson(personDataDAO);
    }
    @GetMapping("/generatePersonPDF/{id}")
    public ResponseEntity<String> generateAllPersonPDF(@PathVariable int id, HttpServletResponse response){
        return serviceClass.generateAllPersonPDF(id,response);
    }

}
