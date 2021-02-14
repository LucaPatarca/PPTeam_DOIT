package com.github.trionfettinicoUNICAM.PPTeam_DOIT.security;

import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserAdapter;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.model.UserEntity;
import com.github.trionfettinicoUNICAM.PPTeam_DOIT.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserAdapter loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(username).orElseThrow(()->
            new UsernameNotFoundException("Nessun utente con la mail: "+username)
        );
        return new UserAdapter(user);
    }
}
