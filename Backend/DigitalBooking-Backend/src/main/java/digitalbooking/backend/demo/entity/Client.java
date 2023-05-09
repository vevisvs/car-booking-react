package digitalbooking.backend.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("Client")
@NoArgsConstructor
public class Client extends Users {


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public Client(String name, String lastName, String email, String password, Role role, City city) {
        super(name, lastName, email, password, role, city);
        this.bookings = new ArrayList<>();
    }

}

