package com.climatechange.family;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.climatechange.user.User;


@ExtendWith(MockitoExtension.class)
public class FamilyServiceTest {
    @Mock
    private FamilyRepository fam;

    @InjectMocks
    private FamilyServiceImpl famService;

    @Test
    void addFamily_NewFamily_ReturnSaved() {
        List<User> users = generateUsers();
        Family family = new Family(1L, users);


        when(fam.save(any(Family.class))).thenReturn(family);
        Family savedFamily = famService.addFamily(family);

        assertEquals(savedFamily, family);
        verify(fam).save(family);
    }

    @Test
    void listFamilies_NoFamilies_ReturnEmpty() {
        List<Family> famList = new ArrayList<>();

        when(fam.findAll()).thenReturn(famList);
        List<Family> savedFamilies = famService.listFamilies();

        assertEquals(savedFamilies, famList);
        verify(fam).findAll();
    }

    @Test
    void listFamilies_HasFamilies_ReturnList() {
        List<User> userList = generateUsers();
        List<Family> famList = new ArrayList<>();
        for (long i = 0; i < 3; i++) {
            famList.add(new Family(i, userList));
        }

        when(fam.findAll()).thenReturn(famList);
        List<Family> savedFamilies = famService.listFamilies();

        assertEquals(savedFamilies, famList);
        verify(fam).findAll();
    }

    @Test
    void getFamily_FamilyExists_ReturnFamily() {
        List<User> users = generateUsers();
        Family family = new Family(1L, users);

        when(fam.findById(any(Long.class))).thenReturn(Optional.of(family));
        Family foundFamily = famService.getFamily(1L);

        assertEquals(foundFamily, family);
        verify(fam).findById(1L);
    }

    @Test
    void getFamily_FamilyNotExists_ReturnNull() {

        when(fam.findById(any(Long.class))).thenReturn(Optional.empty());
        Family foundFamily = famService.getFamily(2L);

        assertNull(foundFamily);
        verify(fam).findById(2L);
    }

    @Test
    void getFamilyByUser_UserExists_ReturnFamily() {
        List<User> users = generateUsers();
        User searchUser = users.get(2);
        Family family = new Family(1L, users);
        List<Family> families = new ArrayList<>();
        families.add(family);

        when(fam.findAll()).thenReturn(families);
        Family foundFamily = famService.getFamilyByUser(searchUser);

        assertEquals(foundFamily, family);
        verify(fam).findAll();
    }

    @Test
    void getFamilyByUser_UserNotExists_ReturnNull() {
        List<User> users = generateUsers();
        User searchUser = generateUser(4);
        Family family = new Family(1L, users);
        List<Family> families = new ArrayList<>();
        families.add(family);

        when(fam.findAll()).thenReturn(families);
        Family foundFamily = famService.getFamilyByUser(searchUser);

        assertNull(foundFamily);
        verify(fam).findAll();
    }

    private static User generateUser(long id) {
        return new User(id, "T", "tester", "tester", "test@gmail.com");
    }

    private static List<User> generateUsers() {
        List<User> users = new ArrayList<>();

        for (long i = 1; i <= 3 ; i++) {
            users.add(generateUser(i));
        }

        return users;
    }
}
