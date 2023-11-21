package com.thermostate.users.model;

import com.thermostate.users.model.service.HashGenerator;
import net.datafaker.Faker;

public class UserObjectMother{

    public static User randomUser(String pass){
        Faker faker = new Faker();
        var salt = faker.howIMetYourMother().highFive();
        return new User(
                faker.number().positive(),
				faker.friends().character(),
                HashGenerator.generate(pass, salt),
				"email@email.com",
				salt);
    }
}
