package com.climatechange.family;

import java.util.List;
import org.springframework.stereotype.Service;
import com.climatechange.user.User;

@Service
public class FamilyServiceImpl implements FamilyService {
    private FamilyRepository familyRepository;

    public FamilyServiceImpl(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    // Gets all families
    @Override
    public List<Family> listFamilies() {
        return familyRepository.findAll();
    }

    // Gets family by family id
    @Override
    public Family getFamily(long id) {
        return familyRepository.findById(id).map(family -> {
            return family;
        }).orElse(null);
    }

    // Gets family by user
    @Override
    public Family getFamilyByUser(User user) {
        // User user = userService.getUser(userId);
        List<Family> families = familyRepository.findAll();
        for (Family fam : families) {
            if (fam.getFamilyMembers().contains(user)) return fam;
        }
        return null;
    }

    // Adds a new family
    @Override
    public Family addFamily(Family family) {
        return familyRepository.save(family);
    }

    // Deletes a family
    @Override
    public void deleteFamily(long id) {
        familyRepository.deleteById(id);
    }
}
