package massimomauro.S7L5FinalProject.controllers;

import massimomauro.S7L5FinalProject.entities.Event;
import massimomauro.S7L5FinalProject.entities.User;
import massimomauro.S7L5FinalProject.payloads.events.NewEventDTO;
import massimomauro.S7L5FinalProject.services.EventsService;
import massimomauro.S7L5FinalProject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventsController {
    @Autowired
    private EventsService eventsService;

    // 1. GET http://localhost:3001/events (+ query params opzionali)
    @GetMapping("")
    public Page<Event> getEvent(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy){
        return eventsService.getEvents(page, size, orderBy);
    }
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('EVENT_ORGANIZER')")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Event saveEvent(@RequestBody NewEventDTO body) {
        return eventsService.save(body);
    }



    // 3. GET http://localhost:3001/events/:id
    @GetMapping("/{id}")
    public Event findById(@PathVariable int id){
        return eventsService.findById(id);
    }

    // 4. PUT http://localhost:3001/events/:id (+ body)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')")
    public Event findByIdAndUpdate(@PathVariable int id, @RequestBody Event body){
        return eventsService.findByIdAndUpdate(id, body);
    }

    // 5. DELETE http://localhost:3001/events/:id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id){
        eventsService.findByIdAndDelete(id);
    }

    @PutMapping("/me/{id}")
    public Event findIdAndPartecipate(@PathVariable int id, @AuthenticationPrincipal User currentUser){
        System.out.println(currentUser.getId());
        return eventsService.findByIdAndPartecipate(id, currentUser.getId());
    }

}
