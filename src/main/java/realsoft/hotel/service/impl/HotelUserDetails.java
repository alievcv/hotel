//package realsoft.hotel.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import realsoft.hotel.dao.AccountDAO;
//import realsoft.hotel.dao.RoleDAO;
//import realsoft.hotel.entity.Account;
//import realsoft.hotel.entity.Role;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class HotelUserDetails implements UserDetailsService {
//
//   @Autowired
//   private AccountDAO accountDAO;
//   @Autowired
//   private RoleDAO roleDAO;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Account account = accountDAO.findByEmail(email);
//        if (account == null){
//            throw new UsernameNotFoundException("Cannot load User");
//        }
//        return new User(account.getEmail(),account.getPassword(), mapRoles(account.getRoles()));
//    }
//
//    private Collection<GrantedAuthority> mapRoles(List<Role> roles){
//        roles = roleDAO.findAllRoles();
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
//    }
//}
