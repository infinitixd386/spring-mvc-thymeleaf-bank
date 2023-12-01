package spring.mvc.bank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.mvc.bank.domain.User;
import spring.mvc.bank.service.DataService;


@Service
public class UserLoginDetailsService implements UserDetailsService {

    @Autowired
    private DataService dataService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dataService.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Wrong Username or Password");
        }
        return new UserLoginDetails(user);
    }

    public String loadAuthenticatedUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
