package com.system.bank.security;

import com.system.bank.entity.RoleEntity;
import com.system.bank.entity.UserEntity;
import com.system.bank.repository.RoleRepository;
import com.system.bank.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomerUserDetailService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Không tìm thấy user: " + username
                        )
                );

        String roleName = "CUSTOMER";
        if (user.getRoleId() != null) {
            roleName = roleRepository.findById(user.getRoleId())
                    .map(RoleEntity::getNameRole)
                    .orElse("CUSTOMER");
        }

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roleName)
                .build();
    }
}
