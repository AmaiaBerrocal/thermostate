package com.thermostate.spring.security.infrastucture.listeners;
import com.thermostate.users.model.event.UserLoggedIn;
import com.thermostate.users.model.event.UserLoginFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserLoggedInListener {
    private static final Logger logger = LoggerFactory.getLogger(UserLoggedInListener.class);
    @EventListener
    public void onUserLogedIn(UserLoggedIn user) {
        logger.trace(Thread.currentThread().getId() + "------>>>>>User " + user.name + " logged in");
    }

    @EventListener
    public void loginFailure(UserLoginFailure user) {
        logger.trace(Thread.currentThread().getId() + "------>>>>>User " + user.name + " unable to login");
    }
}
