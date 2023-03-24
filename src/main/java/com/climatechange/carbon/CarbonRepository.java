package com.climatechange.carbon;

import org.springframework.data.jpa.repository.JpaRepository;

import com.climatechange.user.User;

// import com.climatechange.carbon.CarbonRecord.CarbonRecordId;

import java.sql.Timestamp;
import java.util.List;

public interface CarbonRepository extends JpaRepository<CarbonRecord, Long> {
    List<CarbonRecord> findByDatetime(Timestamp datetime);
    List<CarbonRecord> findByUser(User user);
    
}
