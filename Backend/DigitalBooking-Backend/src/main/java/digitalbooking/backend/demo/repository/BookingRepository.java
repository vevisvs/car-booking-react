package digitalbooking.backend.demo.repository;

import digitalbooking.backend.demo.entity.Booking;
import digitalbooking.backend.demo.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b FROM Booking b where b.client.id = ?1")
    public List<Booking> getByClientId(Long idClient);
    @Query("select b FROM Booking b where b.model.id = ?1")
    public List<Booking> getByModelId(Long idModel);

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.model = :model AND b.finalDate >= :initialDate AND b.initialDate <= :finalDate")
    boolean isModelAvaliable(@Param("model") Model model, @Param("initialDate") LocalDate initalDate,
                                   @Param("finalDate") LocalDate finalDate);

    @Query("SELECT DISTINCT m\n" +
            "FROM Model m\n" +
            "WHERE m.id NOT IN (\n" +
            "    SELECT b.model.id\n" +
            "    FROM Booking b\n" +
            "    WHERE (\n" +
            "        (b.initialDate BETWEEN :startDate AND :endDate)\n" +
            "        OR (b.finalDate BETWEEN :startDate AND :endDate)\n" +
            "        OR (:startDate BETWEEN b.initialDate AND b.finalDate)\n" +
            "        OR (:endDate BETWEEN b.initialDate AND b.finalDate)\n" +
            "    )\n" +
            ")")
    List<Model> findAvailableModelsByDate(@Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);
    @Query("SELECT DISTINCT m\n" +
            "FROM Model m\n" +
            "WHERE m.city.name = :cityName\n" +
            "AND m.id NOT IN (\n" +
            "    SELECT b.model.id\n" +
            "    FROM Booking b\n" +
            "    WHERE b.model.city.name = :cityName\n" +
            "    AND (\n" +
            "        (b.initialDate BETWEEN :startDate AND :endDate)\n" +
            "        OR (b.finalDate BETWEEN :startDate AND :endDate)\n" +
            "        OR (:startDate BETWEEN b.initialDate AND b.finalDate)\n" +
            "        OR (:endDate BETWEEN b.initialDate AND b.finalDate)\n" +
            "    )\n" +
            ")")
    List<Model> findModelByCityAndBookingDate(@Param("cityName") String cityName,
                                                             @Param("startDate") LocalDate startDate,
                                                             @Param("endDate") LocalDate endDate);
}
