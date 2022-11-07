package com.thermostate.events.listeners;

import com.google.common.eventbus.Subscribe;

public class UserLogedInListener {

    @Subscribe
    public void onUserLogedIn(String name) {
        System.out.println(Thread.currentThread().getId() + "------>>>>>User " + name + " logged in");
    }
}
