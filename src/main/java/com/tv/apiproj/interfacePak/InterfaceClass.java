package com.tv.apiproj.interfacePak;

import com.tv.apiproj.dao.PersonDataDAO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InterfaceClass {
    Object getAllUsers(int pageNo) ;

    Object getUser(int userId);

    PersonDataDAO addUser(PersonDataDAO personDataDAO);

    int deleteUser(int id);

    List<PersonDataDAO> getAllPersonData();

    ResponseEntity<String> editPerson(PersonDataDAO personDataDAO);

    ResponseEntity<String> generatePDF(int i, HttpServletResponse response);
}
