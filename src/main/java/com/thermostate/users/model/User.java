package com.thermostate.users.model;

public record User(String name, String password, String email, String salt) {}
//clase java con los atributos que se pasan como arg en este constructor, y que lleva implicito
// el getter de cada atributo y el equals(), tratando como iguales dos objetos con idénticos atributos.