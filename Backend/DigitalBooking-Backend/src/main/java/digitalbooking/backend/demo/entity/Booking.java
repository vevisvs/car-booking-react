package digitalbooking.backend.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalDate initialDate;

    @NotNull
    private LocalDate finalDate;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "model_id")
    private Model model;

    @NotNull
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "client_id")
    private Client client;
}
