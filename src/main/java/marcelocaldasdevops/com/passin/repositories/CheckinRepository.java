package marcelocaldasdevops.com.passin.repositories;

import marcelocaldasdevops.com.passin.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<CheckIn, Integer> {
}
