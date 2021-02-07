package ru.job4j.url_shortcut.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.url_shortcut.models.Site;
import ru.job4j.url_shortcut.models.jwt.JwtResponse;
import ru.job4j.url_shortcut.services.JwtUserDetailsService;
import ru.job4j.url_shortcut.utils.JwtTokenUtil;
import ru.job4j.url_shortcut.utils.devtools.DevLog;

@RestController
@CrossOrigin(value = {"*"})
public class LoginController {
    public static final String CONTROLLER_LOGIN = "/shortcut/login/";
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private JwtUserDetailsService userDetailsService;
    
    /**
     * Request for get JWT.
     */
    @PostMapping(CONTROLLER_LOGIN)
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody Site loginInfo)
            throws Exception {
    
        DevLog.print("LoginController", "createAuthenticationToken");
        authenticate(loginInfo.getLogin(), loginInfo.getPassword());
        DevLog.print("LoginController", "authenticate");
        
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginInfo.getLogin());
        DevLog.print("LoginController", "UserDetails", userDetails);
        
        final String token = jwtTokenUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new JwtResponse(token));
    }
    
    private void authenticate(String login, String password) throws Exception {
        try {
            DevLog.print("LoginController - authenticate", "login", login);
            DevLog.print("LoginController - authenticate", "password", password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
            DevLog.print("LoginController - authenticate", "try - finish");
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    
    
}
