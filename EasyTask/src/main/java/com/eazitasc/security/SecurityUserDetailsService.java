package com.eazitasc.security;

import com.eazitasc.entity.Mt_user;
import com.eazitasc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service(value = "CustomUserDetailsService")
@Transactional(readOnly = true)
public class SecurityUserDetailsService implements UserDetailsService {

    private SessionRegistry sessionRegistry;

    public SecurityUserDetailsService() {
        this.sessionRegistry = new SessionRegistryImpl();
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Mt_user mt_user = this.userRepository.getByUsername(username);
        if (mt_user == null) {
            throw new UsernameNotFoundException("Can't find user");
        }
        User user = new User(mt_user.getUsername(), mt_user.getEncrypted_password(), mt_user.getIs_active(),
                true, true, true, new ArrayList<GrantedAuthority>());
        return user;
    }

    public void expireUserSession(String username) {
        User user = (User) sessionRegistry.getAllPrincipals().stream().filter(principal -> principal instanceof User && ((User) principal).getUsername().equals(username)).findFirst().orElse(null);
        if (user != null) {
            sessionRegistry.getAllSessions(user, true).forEach(SessionInformation::expireNow);
        }
    }
}
