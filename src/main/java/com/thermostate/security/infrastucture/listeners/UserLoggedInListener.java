package com.thermostate.security.infrastucture.listeners;
import com.thermostate.users.model.event.UserLoggedIn;
import com.thermostate.users.model.event.UserLoginFailure;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserLoggedInListener {

    @EventListener
    public void onUserLogedIn(UserLoggedIn user) {
        System.out.println(Thread.currentThread().getId() + "------>>>>>User " + user.name + " logged in");
    }

    @EventListener
    public void loginFailure(UserLoginFailure user) {
        System.out.println(Thread.currentThread().getId() + "------>>>>>User " + user.name + " unable to login");
    }
}
