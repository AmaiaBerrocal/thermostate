package com.thermostate.users.model;

import net.datafaker.Faker;

public class UserObjectMother{

    public static User randomUser(){
        Faker faker = new Faker();
        return new User(
				faker.friends().character(), 
				faker.buffy().characters(), 
				"email@email.com",
				faker.howIMetYourMother().highFive());
    }
}