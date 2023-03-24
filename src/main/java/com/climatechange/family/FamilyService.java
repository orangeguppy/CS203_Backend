package com.climatechange.family;

import java.util.List;

import com.climatechange.user.User;

public interface FamilyService {
    List<Family> listFamilies();

    Family getFamily(long id);

    Family getFamilyByUser(User user);

    Family addFamily(Family family);

    void deleteFamily(long id);

}
