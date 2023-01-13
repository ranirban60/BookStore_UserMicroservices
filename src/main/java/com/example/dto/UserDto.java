package com.example.dto;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Valid
public class UserDto {

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "Invalid First Name(First Letter Should be in Upper Case and min 3 Characters.)")
    String firstName;
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{1,}$", message = "Invalid Last Name(First Letter Should be in Upper Case")
    String lastName;
    @NotEmpty(message = "Address Cannot be Empty")
    String address;
    @NotNull(message = "Email Address cannot be Null")
    String email_address;
    @JsonFormat(pattern = "yyyy MM dd")
    @NotNull(message = "Start Date cannot be Empty")
    @Past(message = "Start Date should be past or present date")
    LocalDate DOB;
    @NotEmpty(message = "Password Cannot be Empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&*()-+=])([a-zA-Z0-9@._-]).{8,}$", message = "invalid password")
    String password;
}
