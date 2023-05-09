package digitalbooking.backend.demo.repository;

import digitalbooking.backend.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.email = :email AND TYPE(u) <> Client")
    public Optional<Users> findByEmail(String email);

    public Optional<List<Users>> findByName(String name);

    public Optional<List<Users>> findByLastName(String lastName);

    @Query("select u FROM Users u where u.role.id = ?1")
    public List<Users> findByRole_Id(Long idRole);

    @Query("select u FROM Users u where u.role.name = ?1")
    public List<Users> findByRole_Name(String roleName);

    @Query("select u FROM Users u where u.city.id = ?1")
    public List<Users> findByCity_Id(Long idCity);

    @Query("select u FROM Users u where u.city.name = ?1")
    public List<Users> findByCity_Name(String cityName);
}
