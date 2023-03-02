package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.enums.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.List;

@Entity
@Table(name = "back_office_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"bo_username"})
        })
@Data
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
public class BackOfficeUser {

    private static final Logger logger = LogManager.getLogger(BackOfficeUser.class);

    @Id
    private Integer id;

    @Column(name = "bo_username")
    private String username;

    @Column(name = "bo_password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;


    public boolean isOrderManager() {
        return getRoles().stream().anyMatch(role -> role.getRoleName() == RoleEnum.ORDER_MANAGER);
    }

    public void subscribeForNewOrderTopicMessages(String topic, String url) {

        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new StringMessageConverter());

        StompSessionHandler sessionHandler = new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe(topic, new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return String.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        logger.info("Hey " + username + ", a new order has been placed! : " + payload);
                    }
                });
            }
        };

        try {
            stompClient.connect(url, sessionHandler).get();
        } catch (Exception e) {
            logger.warn("Error connecting to topic " + topic);
        }
    }
}
