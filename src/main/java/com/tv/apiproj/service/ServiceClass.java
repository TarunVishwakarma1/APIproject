package com.tv.apiproj.service;

import com.tv.apiproj.dao.PersonDataDAO;
import com.tv.apiproj.implementation.ImplementationClass;
import com.tv.apiproj.interfacePak.InterfaceClass;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceClass {
    private ServiceClass(){}
  InterfaceClass interfaceClass = new ImplementationClass();
    public Object getAllUsers(int pageNo) {


        return interfaceClass.getAllUsers(pageNo);
    }

    public Object getUser(int userId) {
        return interfaceClass.getUser(userId);
    }

    public PersonDataDAO addUser(PersonDataDAO personDataDAO) {
        return interfaceClass.addUser(personDataDAO);
    }

    public int deleteUser(int id) {
        return interfaceClass.deleteUser(id);
    }

    public List<PersonDataDAO> getAllPersonData() {
        return interfaceClass.getAllPersonData();
    }

    public ResponseEntity<String> editPerson(PersonDataDAO personDataDAO) {
        return interfaceClass.editPerson(personDataDAO);
    }

    public ResponseEntity<String> generateAllPersonPDF(int id,HttpServletResponse response) {
        return interfaceClass.generatePDF(id,response);
    }
}
