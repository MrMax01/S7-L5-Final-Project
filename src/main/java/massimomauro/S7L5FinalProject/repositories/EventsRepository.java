package massimomauro.S7L5FinalProject.repositories;

import massimomauro.S7L5FinalProject.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Event, Integer> {
}
