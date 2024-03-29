package com.tv.apiproj.implementation;

import com.tv.apiproj.configuration.ExternalApiSource;
import com.tv.apiproj.customs.CustomMethods;
import com.tv.apiproj.dao.PersonDataDAO;
import com.tv.apiproj.interfacePak.InterfaceClass;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ImplementationClass implements InterfaceClass {

    ExternalApiSource externalApiSource = new ExternalApiSource(new RestTemplate());

    static List<PersonDataDAO> personDataDAOList = new ArrayList<>();
    static int idCount = 0;

    @Override
    public Object getAllUsers(int pageNo) {
        return externalApiSource.send("https://reqres.in/api/users?page=" + pageNo);
    }
    @Override
    public Object getUser(int userId) {
        Object obj = externalApiSource.send("https://reqres.in/api/users/"+userId);
        System.out.println(obj);
        return  obj;
    }
    @Override
    public PersonDataDAO addUser(PersonDataDAO personDataDAO) {
        personDataDAO.setId(++idCount);
        personDataDAOList.add(personDataDAO);
        System.out.println(personDataDAO);
        return personDataDAO;
    }

    @Override
    public int deleteUser(int id) {
        Predicate<? super PersonDataDAO> predicate = personDataDAO -> personDataDAO.getId() == id;
        personDataDAOList.removeIf(predicate);
        return id;
    }

    @Override
    public List<PersonDataDAO> getAllPersonData() {
        return personDataDAOList;
    }

    @Override
    public ResponseEntity<String> editPerson(PersonDataDAO personDataDAO) {
        Optional<PersonDataDAO> personOptional = personDataDAOList.stream()
                .filter(person -> person.getId() == personDataDAO.getId())
                .findFirst();

        if (personOptional.isPresent()) {
            PersonDataDAO personToUpdate = personOptional.get();
            personToUpdate.setFullName(personDataDAO.getFullName());
            personToUpdate.setAge(personDataDAO.getAge());
            return ResponseEntity.ok("Person with ID " +  personDataDAO.getId() + " updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> generatePDF(int i, HttpServletResponse response) {
        try {
            if (i == 0) {
                return CustomMethods.generateAllPDF(response, personDataDAOList);
            } else {
                Optional<PersonDataDAO> personOptional = personDataDAOList.stream()
                        .filter(person -> person.getId() == i)
                        .findFirst();
                return personOptional.map(personDataDAO -> CustomMethods.generatePerPersonPDF(response, List.of(personDataDAO))).orElseGet(() -> ResponseEntity.notFound().build());
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
}
