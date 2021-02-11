package ru.job4j.url_shortcut.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.url_shortcut.models.Site;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    SiteManager siteManager;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    
    /**
     * @implNote passwordEncoder.encode(site.getPassword())
     * - use this, if you use NOT Spring DAO data store{@code List, Set, map, etc...}.
     * Else: it don't need, cause Spring make it by himself.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Site site = siteManager.getByLogin(username);
        if (site == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(site.getLogin(), passwordEncoder.encode(site.getPassword()), new ArrayList<>());
    }
    
}