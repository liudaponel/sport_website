package nsu.ponomareva.sport_web_1.repository;

import nsu.ponomareva.sport_web_1.models.Event;
import nsu.ponomareva.sport_web_1.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import nsu.ponomareva.sport_web_1.DTO.EventsReportDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.sql.Timestamp;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @Query(value = "SELECT e.name, COUNT(*) as count_people, e.start_time as date, e.event_id " +
            "FROM users_events ue " +
            "JOIN events e ON ue.event_id = e.event_id " +
            "WHERE e.place_id = :place " +
            "AND e.start_time BETWEEN :startTime AND :endTime " +
            "GROUP BY e.event_id, e.start_time, e.name;", nativeQuery = true)
    List<Object[]> findEventsByPlaceAndStartTimeBetween(@Param("place") Long place_id,
                                                         @Param("startTime") Timestamp startTime,
                                                         @Param("endTime") Timestamp endTime);
}
