package com.climatechange.user;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.climatechange.family.Family;


@RestController
@CrossOrigin
public class UserController {
    UserRepository userRepo;
    UserService userService;
    CustomUserDetailsService userDetails;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserController(UserRepository userRepo, UserService userService, CustomUserDetailsService userDetails) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.userDetails = userDetails;
    }

    // Gets all users
    @RequestMapping("/users/all")
    public List<User> getUsers() {
        return userService.listUsers();
    }

    // Gets user by user id
    @RequestMapping("/users/get-user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Register a user
    @RequestMapping("/users/register")
    public void addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @RequestMapping("/users/username-exists")
    public Boolean userExistsByUsername(String username) {
        return userRepo.existsByEmail(username);
    }


    @RequestMapping("/users/email-exists")
    public Boolean userExistsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    // Update a user
    @RequestMapping("/users/update")
    public String updateUser(@RequestBody UserDTO userDTO) throws Exception {
        try {
            userService.updateUser(userDTO);
            return "Success";
        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e);
            e.printStackTrace();
            return e.getMessage();
        }
    }

    // Updates a user family given 2 users
    @RequestMapping("/users/family")
    public User updateUserFamily(@RequestBody User user1, @RequestBody User user2) {
        return userService.updateUserFamily(user1, user2);
    }

    // Updates a user family if giver user id and email
    @RequestMapping("/users/family3")
    public User updateUserFamily3(HttpServletRequest request) {

        BufferedReader reader;
        Long id = 0L;
        String email = null;

        try {
            reader = request.getReader();
            String[] arr = reader.readLine().split(",");
            for (String line : arr) {
                System.out.println(line);
                if (!line.contains(":")) continue;
                line = line.split(":")[1];
                if (line.contains("@")) email = line.substring(1, line.length() - 2);
                else id = Long.parseLong(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(email);
        User user1 = userService.getUserById(id);
        User user2 = userService.findUserByEmail(email);
        return userService.updateUserFamily(user1, user2);
    }

    // Gets the size of a user's family
    @GetMapping("/{userId}/familySize")
    public Integer getFamilySize(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        Family userFam = user.getFamily();
        if (userFam == null) return 0;
        return userFam.getFamilyMembers().size();
    }

    // Gets the names of all family members of a user
    @GetMapping("/familyNames/{userId}")
    public List<String> getFamilyMemberNames(@PathVariable long userId) {
        User reqUser = userService.getUserById(userId);
        Family userFam = reqUser.getFamily();
        List<String> names = new ArrayList<>();
        if (userFam == null) return names;

        for (User user : userFam.getFamilyMembers()) {
            String name = user.getUsername();
            names.add(name);
        }
        return names;
    }

    // Gets the latest carbon emissions of each family member
    @GetMapping("/familyScores/{userId}")
    public List<Double> getFamilyScores(@PathVariable long userId) {
        User reqUser = userService.getUserById(userId);
        Family fam = reqUser.getFamily();

        List<Double> scores = new ArrayList<>();
        if (fam == null) return scores;

        for (User user : fam.getFamilyMembers()) {
            scores.add(user.getLatestRecord());
        }
        return scores;
    }

    @RequestMapping("users/find-all-by-asc-score/page/{pageNo}")
    public List<User> getAllUsersByScoreAsc(@PathVariable int pageNo) {
        PageRequest pr = PageRequest.of(pageNo, 5); //Magic Number is user count per page rips
        Page<User> users = userService.findAllByOrderByLatestCarbonScoreAsc(pr);
        List<User> usersList = users.getContent();
        return usersList;
    }

    @RequestMapping("users/find-all-by-ascending-score")
    public List<User> getAllByOrderByLatestCarbonScoreAsc() {
        return userService.getAllByOrderByLatestCarbonScoreAsc();
    }

    @RequestMapping("users/find-user-rank/{id}")
    public Integer getUserRank(@PathVariable Long id) {
        List<User> users = getAllByOrderByLatestCarbonScoreAsc();
        return users.indexOf(getUserById(id));
    }
}
