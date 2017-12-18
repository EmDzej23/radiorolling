package com.nevreme.rolling.interceptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nevreme.rolling.model.Role;
import com.nevreme.rolling.model.User;
import com.nevreme.rolling.service.UserService;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
 
     
    @Autowired
	private UserService adminService;
     
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String ssoId)
            throws UsernameNotFoundException {
       	User admin = adminService.findUserByEmail(ssoId);
        if(admin==null){
            throw new UsernameNotFoundException("Username not found");
        }

        return new MyUserPrincipal(admin);
    }
 
     
    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }
     
}

