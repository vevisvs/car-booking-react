package digitalbooking.backend.demo.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "characteristics")
@AllArgsConstructor
@NoArgsConstructor
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min=2 , max=45)
    @NotBlank
    @Column(name="Name", unique = true)
    private String name;

    @Size(min=2 , max=250)
    @NotBlank
    @Column(name="Icon", unique = true)
    private String icon;

}
