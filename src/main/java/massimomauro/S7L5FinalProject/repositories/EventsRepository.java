package massimomauro.S7L5FinalProject.repositories;

import massimomauro.S7L5FinalProject.entities.Event;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EventsRepository extends JpaRepository<Event, Integer> {
}
