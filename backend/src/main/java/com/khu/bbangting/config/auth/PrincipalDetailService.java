package com.khu.bbangting.config.auth;

import com.khu.bbangting.model.User;
import com.khu.bbangting.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        User principal = userRepository.findByEmail(email);

        if(principal == null) {
            return null;
        } else {
            return new PrincipalDetail(principal);
        }
    }

}
