package digitalbooking.backend.demo.repository;

import digitalbooking.backend.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select i FROM Image i where i.name = ?1")
    public List<Image> findByName(String name);

    public void deleteByIdIn(List<Long> ids);
}
