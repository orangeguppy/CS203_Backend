package com.climatechange.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.sql.Timestamp;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.climatechange.carbon.CarbonRecord;
import com.climatechange.family.Family;
import com.climatechange.family.FamilyService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository users;

    @Mock
    private FamilyService familyService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void addUser_NewUser_ReturnSaved() {
        User user = generateUser();

        when(users.save(any(User.class))).thenReturn(user);
        User savedUser = userService.addUser(user);

        assertEquals(savedUser, user);
        verify(users).save(user);
    }

    @Test
    void listUsers_NoUsers_ReturnEmpty() {
        List<User> userList = new ArrayList<>();

        when(users.findAll()).thenReturn(userList);
        List<User> savedUsers = userService.listUsers();

        assertEquals(savedUsers, userList);
        verify(users).findAll();
    }

    @Test
    void listUsers_HasUsers_ReturnList() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            userList.add(generateUser());
        }

        when(users.findAll()).thenReturn(userList);
        List<User> savedUsers = userService.listUsers();

        assertEquals(savedUsers, userList);
        verify(users).findAll();
    }

    @Test
    void getUserById_UserExists_ReturnUser() {
        User user = generateUser();
        Optional<User> opuser = Optional.of(user);


        when(users.findById(any(Long.class))).thenReturn(opuser);
        User foundUser = userService.getUserById(1L);

        assertEquals(foundUser, user);
        verify(users).findById(1L);
    }

    @Test
    void getUserById_UserNotExists_ReturnNull() {

        when(users.findById(any(Long.class))).thenReturn(Optional.empty());
        User foundUser = userService.getUserById(2L);

        assertNull(foundUser);
        verify(users).findById(2L);
    }

    @Test
    void findUserByEmail_UserExists_ReturnUser() {
        User user = generateUser();
        Optional<User> opuser = Optional.of(user);


        when(users.findByEmail(any(String.class))).thenReturn(opuser);
        User foundUser = userService.findUserByEmail("tester@gmail.com");

        assertEquals(foundUser, user);
        verify(users).findByEmail("tester@gmail.com");
    }

    @Test
    void findUserByEmail_UserNotExists_ReturnUser() {
//    void findUserByEmail_UserNotExists_ReturnNull() {
//>>>>>>> 01bcbef8a61bd439e8761e6aa01213e693d4044e
//=======
//    void findUserByEmail_UserNotExists_ReturnNull() {
//>>>>>>> 01bcbef8a61bd439e8761e6aa01213e693d4044e
        Optional<User> opuser = Optional.empty();


        when(users.findByEmail(any(String.class))).thenReturn(opuser);
        User foundUser = userService.findUserByEmail("tester@gmail.com");

        assertNull(foundUser);
        verify(users).findByEmail("tester@gmail.com");
    }

    @Test
    void updateUserFamily_User1HasFamily_ReturnUser() {
        User user1 = generateUser();
        User user2 = generateUser();
        Family fam = new Family();
        user1.setFamily(fam);

        when(users.save(any(User.class))).thenReturn(user1);
        User savedUser = userService.updateUserFamily(user1, user2);

        assertEquals(savedUser, user1);
        verify(users).save(user1);
        verify(users).save(user2);
    }

    @Test
    void updateUserFamily_User2HasFamily_ReturnUser() {
        User user1 = generateUser();
        User user2 = generateUser();
        Family fam = new Family();
        user2.setFamily(fam);

        when(users.save(any(User.class))).thenReturn(user1);
        User savedUser = userService.updateUserFamily(user1, user2);

        assertEquals(savedUser, user1);
        verify(users).save(user1);
        verify(users).save(user2);
    }

    @Test
    void updateUserFamily_bothUsersHasFamily_ReturnUser() {
        Family fam = new Family();
        User user1 = generateUser();
        User user2 = generateUser();
        user1.setFamily(fam);
        user2.setFamily(fam);

        when(users.save(any(User.class))).thenReturn(user1);
        User savedUser = userService.updateUserFamily(user1, user2);

        assertEquals(savedUser, user1);
        verify(users).save(user1);
        verify(users).save(user2);
    }

    @Test
    void getLatestRecordByUser_UserNotExists_ReturnNull() {


        when(users.findById(any(Long.class))).thenReturn(Optional.empty());
        CarbonRecord savRecord = userService.getLatestRecordByUser(2L);

        assertNull(savRecord);
        verify(users).findById(2L);
    }

    @Test
    void getLatestRecordByUser_NoRecords_ReturnNull() {
        User user = generateUser();
        List<CarbonRecord> records = new ArrayList<>();
        user.setRecords(records);

        when(users.findById(any(Long.class))).thenReturn(Optional.of(user));
        CarbonRecord latestRecord = userService.getLatestRecordByUser(1L);

        assertNull(latestRecord);
        verify(users).findById(1L);
    }

    @Test
    void getLatestRecordByUser_HasRecords_ReturnNull() {
        User user = generateUser();
        List<CarbonRecord> records = generateRecords(user);
        user.setRecords(records);

        when(users.findById(any(Long.class))).thenReturn(Optional.of(user));
        CarbonRecord latestRecord = userService.getLatestRecordByUser(1L);

        assertEquals(latestRecord, records.get(records.size() - 1));
        verify(users).findById(1L);
    }

    private static User generateUser() {
        return new User(1L, "T", "tester", "tester", "test@gmail.com");
    }

    private static List<CarbonRecord> generateRecords(User user) {
        List<CarbonRecord> res = new ArrayList<>();
        Random rng = new Random();

        for (long i = 0; i < 5; i++) {
            res.add(new CarbonRecord( i, new Timestamp(i), rng.nextDouble(500, 999), user));
        }

        return res;
    }
}