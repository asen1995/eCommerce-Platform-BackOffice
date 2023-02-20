package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.service;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity.BackOfficeUser;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity.Role;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.enums.RoleEnum;
import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.repository.BackOfficeUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BackOfficeUserDetailsService implements UserDetailsService {

    private final BackOfficeUserRepository backOfficeUserRepository;

    public BackOfficeUserDetailsService(BackOfficeUserRepository backOfficeUserRepository) {
        this.backOfficeUserRepository = backOfficeUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        BackOfficeUser user = backOfficeUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .map(RoleEnum::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

}
