package com.climatechange.carbon;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.stereotype.Service;

import com.climatechange.user.User;
import com.climatechange.user.UserService;

@Service
public class CarbonServiceImpl implements CarbonService {
    private CarbonRepository carbonEmissions;
    private UserService userService;

    public CarbonServiceImpl(CarbonRepository carbonEmissions, UserService userService) {
        this.carbonEmissions = carbonEmissions;
        this.userService = userService;
    }

    // List all carbon records
    @Override
    public List<CarbonRecord> listRecords() {
        return carbonEmissions.findAll();
    }

    // List all carbon records by user
    @Override
    public List<CarbonRecord> getRecordsByUserId(long userId) {
        User user = userService.getUserById(userId);
        return carbonEmissions.findByUser(user);
    }

    // Adds carbon record
    @Override
    public CarbonRecord addRecord(CarbonRecord cr) {
        return carbonEmissions.save(cr);
    }

    // Delete carbon record
    public void deleteRecord(CarbonRecord cr) {
        carbonEmissions.delete(cr);
    }
    // Deletes carbon records by user
    @Override
    public void deleteRecordsByUser(long userid) {
        List<CarbonRecord> toDelete = new ArrayList<>();

        for (CarbonRecord cr : listRecords()) {
            if (cr.getUser().getId() == userid) {
                toDelete.add(cr);
            }
        }
        carbonEmissions.deleteAll(toDelete);;
    }

    @Override
    public List<CarbonRecord> dailyRecordsRankList(long userId, Timestamp dateTime)
    {
        User user = userService.getUserById(userId);
        List<CarbonRecord> list =  carbonEmissions.findByDatetime(dateTime);
        //List<CarbonRecord> userRecords =  carbonEmissions.findByUser(user);
        //List<CarbonRecord> topRecords = new ArrayList<CarbonRecord>();
        CarbonRecord userDayRecord = null;

        for(CarbonRecord cr: list)
        {
            if(cr.getUser() == user)
            {
                userDayRecord = cr;
                break;
            }
        }

        Collections.sort(list);
        return list;
    }
}
