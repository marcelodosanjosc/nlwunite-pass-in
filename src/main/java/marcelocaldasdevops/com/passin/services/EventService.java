package marcelocaldasdevops.com.passin.services;

import lombok.RequiredArgsConstructor;
import marcelocaldasdevops.com.passin.domain.attendee.Attendee;
import marcelocaldasdevops.com.passin.domain.event.Event;
import marcelocaldasdevops.com.passin.domain.event.exceptions.EventNotFoundException;
import marcelocaldasdevops.com.passin.dto.event.EventIdDTO;
import marcelocaldasdevops.com.passin.dto.event.EventRequestDTO;
import marcelocaldasdevops.com.passin.dto.event.EventResponseDTO;
import marcelocaldasdevops.com.passin.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeesService attendeesService;

    public EventResponseDTO getEventDetail(String eventId){
        Event event = this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
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

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
