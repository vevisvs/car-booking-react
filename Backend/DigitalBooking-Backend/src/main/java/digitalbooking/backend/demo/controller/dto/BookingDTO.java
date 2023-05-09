package digitalbooking.backend.demo.controller.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookingDTO {

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalDate initialDate;

    @NotNull
    private LocalDate finalDate;

    @NotNull
    private Long idModel;

    @NotNull
    private Long idClient;
}

