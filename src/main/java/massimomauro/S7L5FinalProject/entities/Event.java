package massimomauro.S7L5FinalProject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import massimomauro.S7L5FinalProject.enums.EventStatus;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String location;
    private String description;
    private LocalDate eventStartDate;
    private int totalTickets;
    @OneToMany(mappedBy = "event")
    @JsonIgnoreProperties("event")
    private List<User> listPartecipants ;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;


}
