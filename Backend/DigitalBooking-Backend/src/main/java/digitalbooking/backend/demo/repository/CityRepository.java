package digitalbooking.backend.demo.repository;

import digitalbooking.backend.demo.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c FROM City c where c.name = ?1")
    public Optional<City> findByCity(String name);
}
