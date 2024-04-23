package marcelocaldasdevops.com.passin.services;

import lombok.RequiredArgsConstructor;
import marcelocaldasdevops.com.passin.domain.attendee.Attendee;
import marcelocaldasdevops.com.passin.domain.checkin.CheckIn;
import marcelocaldasdevops.com.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import marcelocaldasdevops.com.passin.repositories.CheckinRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckinRepository checkinRepository;
    public void registerCheckIn(Attendee attendee){
        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());
        this.checkinRepository.save(newCheckIn);
    }

    private void verifyCheckInExists(String attendeeId){
        Optional<CheckIn> isCheckedIn = getCheckIn(attendeeId);
        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId){
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }
}
