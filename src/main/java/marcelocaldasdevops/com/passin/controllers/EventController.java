package marcelocaldasdevops.com.passin.controllers;

import lombok.RequiredArgsConstructor;
import marcelocaldasdevops.com.passin.dto.attendee.AttendeesListReponseDTO;
import marcelocaldasdevops.com.passin.dto.event.EventIdDTO;
import marcelocaldasdevops.com.passin.dto.event.EventRequestDTO;
import marcelocaldasdevops.com.passin.dto.event.EventResponseDTO;
import marcelocaldasdevops.com.passin.services.AttendeesService;
import marcelocaldasdevops.com.passin.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AttendeesService attendeesService;
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id){
        EventResponseDTO event = this.eventService.getEventDetail(id);
        return ResponseEntity.ok(event);
    }
    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }
    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListReponseDTO> getEventAttendees(@PathVariable String id){
        AttendeesListReponseDTO attendeesListReponse = this.attendeesService.getEventsAttendee(id);
        return ResponseEntity.ok(attendeesListReponse);
    }
}
