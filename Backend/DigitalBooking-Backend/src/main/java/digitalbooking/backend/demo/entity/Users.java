package digitalbooking.backend.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2 , max=45)
    @NotBlank
    @Column(name="Name")
    private String name;

    @Size(min=2 , max=45)
    @NotBlank
    @Column(name="Lastname")
    private String lastName;

    @Size(min=2 , max=45)
    @NotBlank
    @Email
    @Column(name="Email")
    private String email;

    @Size(min=2 , max=200)
    @NotBlank
    @Column(name="password")
    private String password;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "city_id")
    private City city;

    public Users(String name, String lastName, String email, String password, Role role, City city) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.city = city;
    }
}
