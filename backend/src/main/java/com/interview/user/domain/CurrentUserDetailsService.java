package com.interview.user.domain;

import com.interview.user.adapter.out.model.User;
import com.interview.user.adapter.out.repo.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        user.isEnabled(), true, true, true, getAuthorities(user));
  }

  public boolean userHasRole(String roleToCheck) {
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + roleToCheck));
  }

  public User getCurrentUser() {
    return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + SecurityContextHolder.getContext().getAuthentication().getName()));
  }

  private List<GrantedAuthority> getAuthorities(User user) {
    return user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
        .map(GrantedAuthority.class::cast)
        .toList();
  }
}

