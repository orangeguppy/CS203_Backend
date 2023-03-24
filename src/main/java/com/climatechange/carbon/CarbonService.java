package com.climatechange.carbon;

import java.sql.Timestamp;
import java.util.List;

public interface CarbonService {
    List<CarbonRecord> listRecords();
    List<CarbonRecord> getRecordsByUserId(long userId);
    CarbonRecord addRecord(CarbonRecord cr);

    List<CarbonRecord> dailyRecordsRankList(long userId, Timestamp dateTime);

    void deleteRecord(CarbonRecord cr);
    void deleteRecordsByUser(long userId);
}
