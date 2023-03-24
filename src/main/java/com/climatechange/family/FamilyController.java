package com.climatechange.family;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class FamilyController {
    FamilyService familyService;

    @Autowired
    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    
}
