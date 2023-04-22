package com.itlang.services;

import com.itlang.models.Image;
import com.itlang.models.Person;
import com.itlang.repositories.BlogPostImageRepository;
import com.itlang.repositories.PeopleRepository;
//import com.itlang.repositories.UserProgressRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final PeopleRepository peopleRepository;
    private final BlogPostImageRepository imageRepository;

    public Long getUserId(String email){
        Person person = peopleRepository.findPersonByEmail(email);
        return person.getId();
    }

    public void changeIcon(MultipartFile userIcon) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Person person = peopleRepository.findPersonByEmail(email);

        if (userIcon.getSize()!=0){
            if(person.getUserIconId()!= null){
                Image oldimage = imageRepository.findImageById(person.getId());
                Image image;
                image = toImageEntity(userIcon);

                oldimage.setName(image.getName());
                oldimage.setSize(image.getSize());
                oldimage.setOriginalFileName(image.getOriginalFileName());
                oldimage.setContentType(image.getContentType());
                oldimage.setBytes(image.getBytes());

                imageRepository.save(oldimage);
                System.out.println("old image");
            }
            else {
                Image image = new Image();
                image = toImageEntity(userIcon);
                imageRepository.save(image);
                person.setUserIconId(image.getId());
                peopleRepository.save(person);
                System.out.println("new image");
            }
        }
//
//        Image image = new Image();
//        if (userIcon.getSize()!= 0){
//            image = toImageEntity(userIcon);
//        }
//        if (person.getUserIconId()==0){
//            imageRepository.save(image);
//            person.setUserIconId(image.getId());
//            peopleRepository.save(person);
//        }
//        else {
//           image = imageRepository.findImageById(person.getUserIconId());
//
//        }
    }
    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());

        return image;
    }
}
