package digitalbooking.backend.demo.repository;

import digitalbooking.backend.demo.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    @Query("select c FROM Categorie c where c.name = ?1")
    public Optional<Categorie> findByName(String name);

    @Query("select c FROM Categorie c where c.description = ?1")
    public Optional<Categorie> findByDescription(String description);
}
