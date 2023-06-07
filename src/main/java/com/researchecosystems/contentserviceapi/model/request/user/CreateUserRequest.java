package com.researchecosystems.contentserviceapi.model.request.user;

import com.researchecosystems.contentserviceapi.entity.UserRole;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class CreateUserRequest {

    @Email(message = "Email must be appoprite to the format.")
    @NotEmpty(message = "Email must be filled!")
    private String email;

    @NotEmpty(message = "Name must be filled!")
    private String name;

    @NotEmpty(message = "Surname must be filled!")
    private String surname;

    @NotNull(message = "User role must be filled!")
    private UserRole userRole;

    @NotNull(message = "Password must be filled!")
    private String password ;


    


}
