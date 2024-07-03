package com.culinary.userservice.user.model.userdetail;


import com.culinary.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service(value = "detailService")
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
        return this
                .userRepository
                .findByEmail(principal)
                .map(CulinaryUserDetails::new) //
                .orElseThrow(() -> new UsernameNotFoundException(principal + " not found"));
    }
}
