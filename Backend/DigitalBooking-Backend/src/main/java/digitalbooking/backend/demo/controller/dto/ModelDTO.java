package digitalbooking.backend.demo.controller.dto;

import digitalbooking.backend.demo.entity.Image;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModelDTO {

    @NotBlank
    @Size(min=2 , max=250)
    private String name;

    @NotBlank
    @Size(min=2 , max=250)
    private String description;

    @NotBlank
    @Size(min=2 , max=250)
    private String brand;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double score;

    @NotNull
    @Size(min = 1)
    private List<Image> images;

    @NotNull
    @Size(min = 1)
    private List<Long> characteristics;

    @NotNull
    private Long id_City;

    @NotNull
    private Long id_Categorie;

    @NotNull
    @Size(min=2 , max=250)
    private String rules;

    @NotNull
    @Size(min=2 , max=250)
    private String securityRules;

    @NotNull
    @Size(min=2 , max=250)
    private String cancellation;


}
