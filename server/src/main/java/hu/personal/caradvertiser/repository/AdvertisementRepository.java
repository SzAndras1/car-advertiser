package hu.personal.caradvertiser.repository;

import hu.personal.caradvertiser.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Optional<Advertisement> findById(Long id);

    @Query("SELECT a FROM Advertisement a WHERE (:brand IS NULL OR a.brand LIKE %:brand%)" +
            "AND (:type IS NULL OR a.type LIKE %:type%) " +
            "AND (:price IS NULL OR a.price = :price) ")
    Page<Advertisement> search(
            @Param("brand") String brand,
            @Param("type") String type,
            @Param("price") Long price,
            Pageable pageable);
}
