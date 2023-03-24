package com.climatechange.user;

import com.climatechange.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.climatechange.carbon.CarbonRecord;
import com.climatechange.family.Family;
import com.climatechange.family.FamilyService;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository users;
    @Autowired
    private FamilyService famService;

    @Autowired
    public UserServiceImpl(UserRepository users) {
        this.users = users;

    }

    @Override
    public List<User> listUsers() {
        return users.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return users.findById(id).orElse(null);
    }

    @Override
    @CrossOrigin(origins = "*", maxAge = 3600)
    public User addUser(User user) {
        return users.save(user);
    }

    @Override
    public User updateUser(User user) {
        return users.save(user);
    }

    @Override
    @Transactional(propagation= Propagation.NEVER)
    public User updateUser(UserDTO userDTO) throws UserNotFoundException {
        User updatingUser = users.findById(userDTO.getId()).orElseThrow(() -> new UserNotFoundException(userDTO.getId()));
        updatingUser.setFirstName(userDTO.getFirstName());
        updatingUser.setLastName(userDTO.getLastName());
        updatingUser.setEmail(userDTO.getEmail());
        updatingUser.setCity(userDTO.getCity());
        updatingUser.setCountry(userDTO.getCountry());
        updatingUser.setAboutMe(userDTO.getAboutMe());
        users.save(updatingUser);
        return updatingUser;
    }

    @Override
    public CarbonRecord getLatestRecordByUser(Long id) {
        User user = getUserById(id);
        if (user == null) return null;
        List<CarbonRecord> carbonRecords = user.getRecords();
        if (carbonRecords.isEmpty()) return null;

        return carbonRecords.get(carbonRecords.size() - 1);
    }

    @Override
    public User updateUserFamily(User user1, User user2) {
        Family fam1 = user1.getFamily();
        Family fam2 = user2.getFamily();
        if (fam1 == null && fam2 == null) {
            fam1 = new Family();
            famService.addFamily(fam1);
        }

        fam1 = fam1 == null ? fam2 : fam1;

        user1.setFamily(fam1);
        users.save(user1);
        user2.setFamily(fam1);
        users.save(user2);
        return user1;
    }


    @Override
    public User findUserByEmail(String email) {
        return users.findByEmail(email).map(user -> {
            return user;
        }).orElse(null);
    }

    @Override
    public Page<User> findAllByOrderByLatestCarbonScoreAsc(Pageable page) {
        return users.findAllByOrderByLatestCarbonScoreAsc(page);
    }

    @Override
    public List<User> getAllByOrderByLatestCarbonScoreAsc() {
        return users.findAllByOrderByLatestCarbonScoreAsc();
    }
}