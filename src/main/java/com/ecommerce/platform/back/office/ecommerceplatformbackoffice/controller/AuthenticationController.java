package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.controller;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.authentication.AuthenticationRequest;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.authentication.AuthenticationResponse;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.security.JwtTokenProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authenticate")
@Profile("dev")
public class AuthenticationController {

    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationController(UserDetailsService iBackOfficeUserDetailsService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = iBackOfficeUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest loginRequest) {

        logger.info("Authenticating user: {}", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );


        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(userDetails);

        logger.info("User {} authenticated successfully", loginRequest.getUsername());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
