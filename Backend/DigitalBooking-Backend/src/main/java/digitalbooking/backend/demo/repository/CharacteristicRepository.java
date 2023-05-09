package digitalbooking.backend.demo.repository;


import digitalbooking.backend.demo.entity.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    @Query("select c FROM Characteristic c where c.name = ?1")
    public Optional<Characteristic> findByName(String name);
}
