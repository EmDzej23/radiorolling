package com.nevreme.rolling.configuration;

import com.nevreme.rolling.interceptors.MyUserPrincipal;
import com.nevreme.rolling.model.Playlist;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null) {
            return false;
        }

        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return true;
        }

        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
        return hasPermission(authentication, targetType, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication == null) {
            return false;
        }

        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return true;
        }

        List<Playlist> playlists = principal.getUser().getPlaylists();
        if (!playlists.stream().filter(playlist -> playlist.getId().equals(targetId)).collect(Collectors.toList()).isEmpty()) {
            return true;
        }
        return false;
    }
}
