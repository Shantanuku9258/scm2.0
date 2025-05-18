package com.scm.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name ="user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class user implements UserDetails {

    @Id
    private String userId;
    @Column(name ="user_name",nullable = false)
    private String name ;
    @Column(unique = true,nullable = false)
    private String email;
    @Getter(AccessLevel.NONE)
    private String password;
    @Column(length = 1000)
    private String about;
    @Column(length = 1000)
    private String profilePic;
    private String phoneNumber;

//informsyion
   @Getter(AccessLevel.NONE)
    private boolean enabled = true;

    private boolean emailverified=false;
    private boolean phoneverified= false;

 @Enumerated(value = EnumType.STRING)
    //self,google,facebook,linkedin,github
    private Providers provider = Providers.SELF;
    private String providerUserId;
    //add more data

 @OneToMany(mappedBy = "user", cascade=CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

@ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();


    //add security region 

@Override//during the roles it will work
public Collection<? extends GrantedAuthority> getAuthorities() {
    //List of roles [User,Admin]
    //Collections of simpleGrantedAuthority[roles{Admin,User}]

   Collection <SimpleGrantedAuthority> roles= roleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    return roles;
   
}

//we use email id as a username
@Override
public String getUsername() {
return  this.email;
}

@Override
public boolean isAccountNonExpired() {
    return true;
   
}
@Override
public boolean isAccountNonLocked() {
    return true;
   
}
@Override
public boolean isCredentialsNonExpired() {
    return true;
   
}
@Override
public boolean isEnabled() {
    return this.enabled;
   
}

@Override
public String getPassword() {
    return this.password;
    
}




}
