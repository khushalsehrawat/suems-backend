package com.suems.repository;

import com.suems.model.SensorData;
import com.suems.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    // Fetch last 100 entries for a specific user
    List<SensorData> findTop100ByUserIdOrderByTimestampDesc(User userId);

    // Fetch all data after a certain timestamp for a user
    List<SensorData> findByUserIdAndTimestampAfterOrderByTimestampAsc(User userId, LocalDateTime timestamp);

    // Fetch data between two timestamps for a user
    List<SensorData> findByUserIdAndTimestampBetweenOrderByTimestampAsc(User userId, LocalDateTime from, LocalDateTime to);

    // Delete user’s data in time range
    long deleteByUserIdAndTimestampBetween(User userId, LocalDateTime from, LocalDateTime to);
}
