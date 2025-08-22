package com.vinceflores.event_booking.controllers;

import com.vinceflores.event_booking.model.events.CreateEventDTO;
import com.vinceflores.event_booking.model.events.Event;
import com.vinceflores.event_booking.model.events.EventRepository;
import com.vinceflores.event_booking.model.events.EventService;
import com.vinceflores.event_booking.model.users.User;
import com.vinceflores.event_booking.model.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Event>>> getEvents() {
        List<Event> events = eventRepository.findAll();

        List<EntityModel<Event>> eventModels = events.stream()
                .map(event -> EntityModel.of(event)
                        .add(linkTo(methodOn(EventController.class).getEvent(event.getId())).withSelfRel())
                        .add(linkTo(methodOn(EventController.class).updateEvent(event.getId(), event)).withRel("update"))
                        .add(linkTo(methodOn(EventController.class).deleteEvent(event.getId())).withRel("delete")))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Event>> collectionModel = CollectionModel.of(eventModels)
                .add(linkTo(methodOn(EventController.class).getEvents()).withSelfRel());
//                .add(linkTo(methodOn(EventController.class).createEvent(null, null)).withRel("create"));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Event>> getEvent(@PathVariable Integer id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            EntityModel<Event> eventModel = EntityModel.of(event.get())
                    .add(linkTo(methodOn(EventController.class).getEvent(id)).withSelfRel())
                    .add(linkTo(methodOn(EventController.class).getEvents()).withRel("all-events"))
                    .add(linkTo(methodOn(EventController.class).updateEvent(event.get().getId(), event.get())).withRel("update"))
                    .add(linkTo(methodOn(EventController.class).deleteEvent(id)).withRel("delete"));

            return ResponseEntity.ok(eventModel);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<Event>> createEvent(@RequestBody CreateEventDTO event, Principal principal) {
        Optional<User> u = userRepository.findByEmail(principal.getName());
        if (u.isPresent()) {
            Event e = Event.builder()
                    .title(event.getTitle())
                    .description(event.getDescription())
                    .location(event.getLocation())
                    .eventStatus(event.getEventStatus())
                    .price(event.getPrice())
                    .eventDelivery(event.getEventDelivery())
                    .startTime(event.getStartTime())
                    .endTime(event.getEndTime())
                    .capacity(event.getCapacity())
                    .user(u.get())
                    .build();
            Event savedEvent = eventRepository.save(e);

            EntityModel<Event> eventModel = EntityModel.of(savedEvent)
                    .add(linkTo(methodOn(EventController.class).getEvent(savedEvent.getId())).withSelfRel())
                    .add(linkTo(methodOn(EventController.class).getEvents()).withRel("all-events"))
                    .add(linkTo(methodOn(EventController.class).updateEvent(savedEvent.getId(), savedEvent)).withRel("update"))
                    .add(linkTo(methodOn(EventController.class).deleteEvent(savedEvent.getId())).withRel("delete"));

            return ResponseEntity.created(
                            linkTo(methodOn(EventController.class).getEvent(savedEvent.getId())).toUri())
                    .body(eventModel);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Event>> updateEvent(@PathVariable int id, @RequestBody Event event) {
        if (eventRepository.existsById(id)) {
            event.setId(id);
            Event updatedEvent = eventRepository.save(event);

            EntityModel<Event> eventModel = EntityModel.of(updatedEvent)
                    .add(linkTo(methodOn(EventController.class).getEvent(updatedEvent.getId())).withSelfRel())
                    .add(linkTo(methodOn(EventController.class).getEvents()).withRel("all_events"))
                    .add(linkTo(methodOn(EventController.class).deleteEvent(updatedEvent.getId())).withRel("delete"));

            return ResponseEntity.ok(eventModel);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Integer id) {
//            eventRepository.deleteById(id);
            eventService.deleteEventKeepBookings(id);
            return ResponseEntity.ok("Deleted");
    }
}