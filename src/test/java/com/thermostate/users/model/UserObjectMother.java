package com.thermostate.users.model;

import com.thermostate.users.model.service.HashGenerator;
import net.datafaker.Faker;

import java.util.UUID;

public class UserObjectMother{

    public static User randomUser(String pass){
        Faker faker = new Faker();
        var salt = faker.howIMetYourMother().highFive();
        return User.with(
                UUID.randomUUID(),
                faker.starWars().character(),
                HashGenerator.generate(pass, salt),
				"email@email.com",
				salt,
                true);
    }
}
