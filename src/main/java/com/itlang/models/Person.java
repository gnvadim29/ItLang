package com.itlang.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Vadym Hnatiuk
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters")
    private String name;

    @NotEmpty(message = "Surname should be not empty")
    @Size(min = 1, max = 30, message = "Surname should be between 1 and 30 characters")
    private String surname;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(columnDefinition = "text")
    private String email;

    @NotEmpty(message = "Group should not be empty")
    @Size(min = 4, max = 7, message = "Surname should be between 4 and 30 characters")
    @Column(name = "person_group")
    private String group;

    @Column(columnDefinition = "text")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    private boolean enabled;

    private String role;

    @OneToOne(mappedBy = "person")
    private UserProgress userProgress;
    private Long userIconId;
}
