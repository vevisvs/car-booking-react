package digitalbooking.backend.demo.repository;

import digitalbooking.backend.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r FROM Role r where r.name = ?1")
    Optional<Role> getByName(String name);
}
