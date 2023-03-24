package com.climatechange.carbon;



import com.climatechange.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class CarbonServiceTest {
    @Mock
    private CarbonRepository emissions;

    @InjectMocks
    private CarbonServiceImpl carbonService;

    @Test
    void listRecords_NoRecords_ReturnEmpty() {
        List<CarbonRecord> crList = new ArrayList<>();

        when(emissions.findAll()).thenReturn(new ArrayList<>());
        List<CarbonRecord> savedList = carbonService.listRecords();

        assertEquals(savedList, crList);
        verify(emissions).findAll();
    }

    @Test
    void listRecords_MultipleRecords_ReturnEmpty() {
        List<CarbonRecord> crList = generateRecords(3);

        when(emissions.findAll()).thenReturn(crList);
        List<CarbonRecord> savedList = carbonService.listRecords();

        assertEquals(savedList, crList);
        verify(emissions).findAll();
    }

    @Test
    void addRecord_NewRecord_ReturnSaved() {
        CarbonRecord crd = generateRecord(1);

        when(emissions.save(any(CarbonRecord.class))).thenReturn(crd);
        CarbonRecord savRecord = carbonService.addRecord(crd);

        assertEquals(savRecord, crd);
        verify(emissions).save(crd);
    }

    @Test
    void addCarbon_NoId_ReturnSaved() {
        CarbonRecord crd = new CarbonRecord(null, new Timestamp(System.currentTimeMillis()), 500.0, new User("Tester", "test@gmail.com", "tester"));

        when(emissions.save(any(CarbonRecord.class))).thenReturn(crd);
        CarbonRecord savRecord = carbonService.addRecord(crd);

        assertEquals(savRecord, crd);
        verify(emissions).save(crd);
    }

    @Test
    void addCarbon_ZeroEmission_ReturnSaved() {
        CarbonRecord crd = new CarbonRecord(1L, new Timestamp(System.currentTimeMillis()), 0.0, new User("Tester", "test@gmail.com", "tester"));

        when(emissions.save(any(CarbonRecord.class))).thenReturn(crd);
        CarbonRecord savRecord = carbonService.addRecord(crd);

        assertEquals(savRecord, crd);
        verify(emissions).save(crd);
    }

    @Test
    void deleteRecord_Single_ReturnEmpty() {
        CarbonRecord crd = generateRecord(1);

        when(emissions.save(any(CarbonRecord.class))).thenReturn(crd);
        when(emissions.findAll()).thenReturn(new ArrayList<>());
        carbonService.addRecord(crd);
        carbonService.deleteRecord(crd);
        List<CarbonRecord> res = carbonService.listRecords();
        

        assertEquals(res.size(), 0);
        verify(emissions).save(crd);
        verify(emissions).delete(crd);
        verify(emissions).findAll();
    }

    private static CarbonRecord generateRecord(final long id) {
        User user = new User("Tester", "test@gmail.com", "tester");
        Random rng = new Random();
        CarbonRecord res = new CarbonRecord(id, new Timestamp(System.currentTimeMillis()), rng.nextDouble(500, 999), user);
        return res;
    }

    private static List<CarbonRecord> generateRecords(final int numOfRecs) {
        List<CarbonRecord> res = new ArrayList<>();
        for (int i = 0; i < numOfRecs; i++) {
            res.add(generateRecord(i));
        }
        return res;
    }

}
