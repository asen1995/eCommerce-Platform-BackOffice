package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.security;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.constants.AppConstants;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity.BackOfficeUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoggedInUsers {

    private static final Logger logger = LogManager.getLogger(LoggedInUsers.class);
    private final String webSocketSelfUrl;

    private final Map<String, BackOfficeUser> loggedUsersMap = new ConcurrentHashMap<>();
    
    public LoggedInUsers(ServerProperties serverProperties) {
        this.webSocketSelfUrl = String.format(AppConstants.WEBSOCKET_SERVER_SELF_URL_TEMPLATE, serverProperties.getPort());
    }

    public void loginUser(BackOfficeUser backOfficeUser) {
        if (isUserAlreadyLoggedIn(backOfficeUser.getUsername())) return;

        loggedUsersMap.put(backOfficeUser.getUsername(), backOfficeUser);
        logger.info("User logged in: " + backOfficeUser.getUsername());

        if (backOfficeUser.isOrderManager()) {
            logger.info("User {} is an order manager, subscribing to order topic", backOfficeUser.getUsername());
            backOfficeUser.subscribeForNewOrderTopicMessages(AppConstants.TOPIC_NEW_ORDER, webSocketSelfUrl);
            logger.info("User {} subscribed to order topic successfully", backOfficeUser.getUsername());
        }
    }

    public void logoutUser(String username) {
        loggedUsersMap.remove(username);
        logger.info("User logged out: {}", username);
    }

    public boolean isUserAlreadyLoggedIn(String username) {
        return loggedUsersMap.containsKey(username);
    }

    @PreDestroy
    public void cleanup() {
        logger.info("Cleaning up logged in users");
        loggedUsersMap.clear();
    }
}

