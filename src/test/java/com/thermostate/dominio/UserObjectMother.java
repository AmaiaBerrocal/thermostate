package com.thermostate.dominio;

import com.github.javafaker.Faker;

public class UserObjectMother{
  public static User randomUser(){
        Faker faker = new Faker();
        return new User(
				faker.number().randomDigit(),
				faker.backToTheFuture().character(),
				faker.rickAndMorty().quote(),
				faker.howIMetYourMother().quote(),
				faker.number().randomDigit(), true);
    }
}