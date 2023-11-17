package massimomauro.S7L5FinalProject.services;

import massimomauro.S7L5FinalProject.entities.Event;
import massimomauro.S7L5FinalProject.entities.User;
import massimomauro.S7L5FinalProject.enums.EventStatus;
import massimomauro.S7L5FinalProject.exceptions.BadRequestException;
import massimomauro.S7L5FinalProject.exceptions.NotFoundException;
import massimomauro.S7L5FinalProject.payloads.events.NewEventDTO;
import massimomauro.S7L5FinalProject.repositories.EventsRepository;
import massimomauro.S7L5FinalProject.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;

    public Event save(NewEventDTO body) {


        Event newEvent = new Event();
        newEvent.setTitle(body.title());
        newEvent.setDescription(body.description());
        newEvent.setLocation(body.location());
        newEvent.setEventStartDate(body.eventStartDate());
        newEvent.setTotalTickets(body.totalTickets());
        newEvent.setEventStatus(EventStatus.LIBERO);
        //newEvent.setCover("http://picsum.photos/200/300");
        return eventsRepository.save(newEvent);


    }
    public Page<Event> getEvents(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return eventsRepository.findAll(pageable);
    }

    public Event findById(int id) throws NotFoundException {
        return eventsRepository.findById(id).orElseThrow( ()  -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws NotFoundException{
        Event found = this.findById(id);
        eventsRepository.delete(found);
    }

    public Event findByIdAndUpdate(int id, Event body) throws NotFoundException{
        Event found = this.findById(id);
        found.setTitle(body.getTitle());
        found.setLocation(body.getLocation());
        found.setDescription(body.getDescription());
        found.setEventStartDate(body.getEventStartDate());
        found.setTotalTickets(body.getTotalTickets());
        return eventsRepository.save(found);
    }

    public Event findByIdAndPartecipate(int eventId, int userId )throws NotFoundException{
        User user = usersService.findById(userId);

        Event found = this.findById(eventId);
        user.setEvent(found);

        List<User> UsersPartecipant = new ArrayList<>(found.getListPartecipants());
        UsersPartecipant.add(user);
        found.setListPartecipants(UsersPartecipant);


        return eventsRepository.save(found);

    }

}
