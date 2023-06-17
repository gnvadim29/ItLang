package com.itlang.controllers;

import com.itlang.models.Person;
import com.itlang.repositories.PeopleRepository;
import com.itlang.services.CreateCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CertificateController {
    private final CreateCertificateService certificateService;
    private final PeopleRepository peopleRepository;

    @GetMapping("/createCertificate")
    public ResponseEntity<byte[]> getCertificate(@RequestParam(name = "course") String course){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = peopleRepository.findPersonByEmail(authentication.getName());
        String name = person.getName() + " " + person.getSurname();

        if (course.equals("itenglish")){
            course = "IT ENGLISH";
            if(person.getUserProgress().getItEnglish()==100){
                byte[] certificate = certificateService.createCertificate(name, course);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "certificate.pdf");
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(certificate);
            }
        }
        else if (course.equals("znoenglish")) {
            course = "ZNO ENGLISH";
            if(person.getUserProgress().getZnoEnglish()==100){
                byte[] certificate = certificateService.createCertificate(name, course);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "certificate.pdf");
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(certificate);
            }
        }
        return null;
    }
}
