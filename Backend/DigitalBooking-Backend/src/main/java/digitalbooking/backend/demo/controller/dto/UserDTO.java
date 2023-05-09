package digitalbooking.backend.demo.controller.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @Size(min=2 , max=45)
    @NotBlank
    @Column(name="Name")
    private String name;

    @Size(min=2 , max=45)
    @NotBlank
    @Column(name="LastName")
    private String lastName;

    @Size(min=2 , max=45)
    @NotBlank
    @Email
    @Column(name="Email", unique = true)
    private String email;

    @Size(min=2 , max=200)
    @NotBlank
    @Column(name="password")
    private String password;

    @NotNull
    private Long roleId;

    @NotNull
    private Long id_City;
}
