package hu.personal.caradvertiser.repository;

import hu.personal.caradvertiser.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
