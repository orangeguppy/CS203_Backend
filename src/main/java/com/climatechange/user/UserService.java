package com.climatechange.user;

import com.climatechange.exception.UserNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.climatechange.carbon.CarbonRecord;


public interface UserService {
    // Find user by id
    User getUserById(Long id);

    // Return newly-added user
    User addUser(User user);

    // Update a User
    User updateUser(User user);

    // Update a user's family
    User updateUserFamily(User user1, User user2);

    // Find a user by email
    User findUserByEmail(String email);

    // Find the latest carbon record for a user
    CarbonRecord getLatestRecordByUser(Long id);

    // Update a user
    User updateUser(UserDTO userDTO) throws UserNotFoundException;

    // Return all users
    List<User> listUsers();

    List<User> getAllByOrderByLatestCarbonScoreAsc();
    Page<User> findAllByOrderByLatestCarbonScoreAsc(Pageable page);
}
