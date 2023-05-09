package digitalbooking.backend.demo.repository;

import digitalbooking.backend.demo.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    @Query("select m FROM Model m where m.city.name = ?1")
    public List<Model> getByCityName(String name);

    @Query("select m FROM Model m where m.city.id = ?1")
    public List<Model> findByCity_Id(Long idCity);


    @Query("select m FROM Model m where m.categorie.id = ?1")
    public List<Model> findByCategorie_Id(Long idCategorie);

    @Query("select m FROM Model m where m.name = ?1")
    public List<Model> getByName(String name);

    @Query("select m FROM Model m where m.description = ?1")
    public List<Model> getByDescription(String description);

    @Query("SELECT m FROM Model m WHERE m.categorie.id IN :idsCategories")
    List<Model> findAllByCategorieIds(@Param("idsCategories") List<Long> idsCategories);

    @Query("SELECT m FROM Model m WHERE m.city.id IN :idsCities")
    List<Model> findAllByCityIds(@Param("idsCities") List<Long> idsCities);


}
