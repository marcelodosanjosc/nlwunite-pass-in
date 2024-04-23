package marcelocaldasdevops.com.passin.services;

import lombok.RequiredArgsConstructor;
import marcelocaldasdevops.com.passin.domain.attendee.Attendee;
import marcelocaldasdevops.com.passin.domain.event.Event;
import marcelocaldasdevops.com.passin.domain.event.exceptions.EventFullException;
import marcelocaldasdevops.com.passin.domain.event.exceptions.EventNotFoundException;
import marcelocaldasdevops.com.passin.dto.attendee.AttendeeIdDTO;
import marcelocaldasdevops.com.passin.dto.attendee.AttendeeRequestDTO;
import marcelocaldasdevops.com.passin.dto.event.EventIdDTO;
import marcelocaldasdevops.com.passin.dto.event.EventRequestDTO;
import marcelocaldasdevops.com.passin.dto.event.EventResponseDTO;
import marcelocaldasdevops.com.passin.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeesService attendeesService;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeesService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }
    public EventIdDTO createEvent(EventRequestDTO eventDTO){
        Event newEvent = new Event();

        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDTO.title()));

        this.eventRepository.save(newEvent);
        return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO){
        this.attendeesService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeesService.getAllAttendeesFromEvent(eventId);

        if(event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Event in full");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeesService.registerAttendee(newAttendee);
        return new AttendeeIdDTO(newAttendee.getId());
    }

    private Event getEventById(String eventId){
        return this.eventRepository.findById(eventId).orElseThrow(() ->  new EventNotFoundException("Event not found with ID: " + eventId));
    }
    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
