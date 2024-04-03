package marcelocaldasdevops.com.passin.services;

import lombok.RequiredArgsConstructor;
import marcelocaldasdevops.com.passin.domain.attendee.Attendee;
import marcelocaldasdevops.com.passin.domain.checkin.CheckIn;
import marcelocaldasdevops.com.passin.dto.attendee.AttendeeDetails;
import marcelocaldasdevops.com.passin.dto.attendee.AttendeesListReponseDTO;
import marcelocaldasdevops.com.passin.repositories.AttendeeRepository;
import marcelocaldasdevops.com.passin.repositories.CheckinRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeesService {
    private final AttendeeRepository attendeeRepository;
    private final CheckinRepository checkinRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
            }
    public AttendeesListReponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkinRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListReponseDTO(attendeeDetailsList);
    }
}
