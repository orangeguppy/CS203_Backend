package com.climatechange.carbon;

import java.io.BufferedReader;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.climatechange.family.Family;
import com.climatechange.user.User;
import com.climatechange.user.UserService;

@RestController
@CrossOrigin
public class CarbonController {
    private CarbonService carbonService;
    private UserService userService;

    public CarbonController(CarbonService carbonService, UserService userService) {
        this.carbonService = carbonService;
        this.userService = userService;
    }

    //Returns all carbon emissions in database
    @GetMapping("/carbonEmissions")
    public List<CarbonRecord> getCarbonRecords() {
        return carbonService.listRecords();
    }

    // Gets carbon records by user id
    // For demo purposes, if the number of emissions is less than 6, populate until 6
    // If demo is set to false, return actual emissions
    @GetMapping("/carbonEmissions/{id}")
    public List<CarbonRecord> getUserEmissions(@PathVariable long id) {
        List<CarbonRecord> res = generateRandomEmissions(id, false);
        return res;
    }

    // Get latest carbon record for a given user
    @GetMapping("/carbonEmissions/latest/{id}")
    public CarbonRecord getLatestRecord(@PathVariable long id) {
        return userService.getLatestRecordByUser(id);
    }

    // Get total carbon emissions for a family
    @GetMapping("/carbonEmissions/family/{id}")
    public Double getFamilyCarbon(@PathVariable long id) {
        User user = userService.getUserById(id);
        Family userFam = user.getFamily();
        if (userFam == null)
            return (double) user.getLatestRecord();
        double value = userFam.getFamilyEmissions();
        return value;
    }

    // Parses HTTP Body for to add carbon record
    @PostMapping("/carbonEmissions")
    public CarbonRecord addCarbonEmission(HttpServletRequest request) {

        BufferedReader reader;
        long datetime = 0;
        double emission = 0;
        long userId = 0;
        try {
            reader = request.getReader();
            String[] arr = reader.readLine().split(",");
            System.out.println(String.join("\n", arr));

            String preDatetime = arr[0].split(":")[1];
            datetime = Long.parseLong(preDatetime, 0, preDatetime.length(), 10);

            String preEmission = arr[1].split(":\"")[1];
            emission = Double.parseDouble(preEmission.substring(0, preEmission.length() - 1));
            System.out.println(preEmission);

            String preUserID = arr[2].split(":")[1];
            userId = Long.parseLong(preUserID, 0, preUserID.length() - 1, 10);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        User carbonUser = userService.getUserById(userId);
        carbonUser.setLatestCarbonScore(emission);
        userService.updateUser(carbonUser);
        CarbonRecord cr = new CarbonRecord(null, new Timestamp(datetime), emission, carbonUser);
        return carbonService.addRecord(cr);
    }

    private List<CarbonRecord> generateRandomEmissions(long id, boolean isDemo) {
        List<CarbonRecord> res = carbonService.getRecordsByUserId(id);
        if (!isDemo)
            return res;

        int numToAdd = 6 - res.size();
        Random rng = new Random();
        User user = userService.getUserById(id);

        for (int i = 0; i < numToAdd; i++) {
            carbonService.addRecord(new CarbonRecord(null, new Timestamp(System.currentTimeMillis()),
                    rng.nextDouble(600, 1300), user));
        }
        res = carbonService.getRecordsByUserId(id);
        return res;
    }
}
