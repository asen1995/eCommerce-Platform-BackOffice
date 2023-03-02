package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.security;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity.BackOfficeUser;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.enums.RoleEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoggedInUsers {

    private static final Logger logger = LogManager.getLogger(LoggedInUsers.class);


    @Value("${back-office-websocket-service.url}")
    private String backOfficeWebSocketUrl;

    private final Map<String, BackOfficeUser> users = new ConcurrentHashMap<>();

    public void loginUser(BackOfficeUser backOfficeUser) {
        if (isUserLoggedIn(backOfficeUser.getUsername())) return;

        users.put(backOfficeUser.getUsername(), backOfficeUser);
        logger.info("User logged in: " + backOfficeUser.getUsername());

        if (backOfficeUser.getRoles().stream().anyMatch(role -> role.getRoleName() == RoleEnum.ORDER_MANAGER)) {
            logger.info("User {} is an order manager, subscribing to order topic", backOfficeUser.getUsername());
            backOfficeUser.listenForNewOrderTopicMessages("/topic/order", backOfficeWebSocketUrl);
            logger.info("User {} subscribed to order topic successfully", backOfficeUser.getUsername());
        }
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

