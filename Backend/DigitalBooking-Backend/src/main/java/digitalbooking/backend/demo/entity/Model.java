package digitalbooking.backend.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Name")
    private String name;

    @Column(name="Description")
    private String description;

    @Column(name="Brand")
    private String brand;

    @Column(name="Score")
    private Double score;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private List<Image> images;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @Column(name="Rules")
    private String rules;

    @Column(name = "security_rules")
    private String securityRules;

    @Column(name = "cancellation")
    private String cancellation;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "model_characteristic",
            joinColumns = @JoinColumn(name = "model_id"),
            inverseJoinColumns = @JoinColumn(name = "characteristic_id"))
    private List<Characteristic> characteristics = new ArrayList<>();

}
