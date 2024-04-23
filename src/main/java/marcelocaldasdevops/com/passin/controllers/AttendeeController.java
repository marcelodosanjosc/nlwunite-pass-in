package marcelocaldasdevops.com.passin.controllers;

import lombok.RequiredArgsConstructor;
import marcelocaldasdevops.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
import marcelocaldasdevops.com.passin.services.AttendeesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeesService attendeesService;
    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        AttendeeBadgeResponseDTO response =  this.attendeesService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        this.attendeesService.checkInAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();
    }
}
