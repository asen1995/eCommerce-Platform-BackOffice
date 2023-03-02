package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.security;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity.BackOfficeUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoggedInUsers {

    private static final Logger logger = LogManager.getLogger(LoggedInUsers.class);

    private final Map<String, BackOfficeUser> users = new ConcurrentHashMap<>();

    public void loginUser(BackOfficeUser backOfficeUser) {
        if (isUserLoggedIn(backOfficeUser.getUsername())) return;

        users.put(backOfficeUser.getUsername(), backOfficeUser);
        logger.info("User logged in: " + backOfficeUser.getUsername());

    }

    public void logoutUser(BackOfficeUser user) {
        users.remove(user.getUsername());
        logger.info("User logged out: " + user.getUsername());
    }

    public boolean isUserLoggedIn(String username) {
        return users.containsKey(username);
    }

    @PreDestroy
    public void cleanup() {
        logger.info("Cleaning up logged in users");
        users.clear();
    }
}

