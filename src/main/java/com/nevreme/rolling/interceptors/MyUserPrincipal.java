package com.nevreme.rolling.interceptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nevreme.rolling.model.Role;
import com.nevreme.rolling.model.User;

public class MyUserPrincipal implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5694665634575424841L;
	private User user;
 
    public MyUserPrincipal(User user) {
        this.user = user;
    }
    
    public Long getId() {
    	return user.getId();
    }
    
    public String getImage() {
    	return user.getImage();
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for(Role role : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}


