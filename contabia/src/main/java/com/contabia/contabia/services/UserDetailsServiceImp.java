package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.contabia.contabia.exceptions.CnpjNotFoundException;
import com.contabia.contabia.models.entity.UserDetailsImp;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userOptional = userRepository.findByCnpj(username);

        if(userOptional.isPresent()){
            UserModel user = userOptional.get();
            return buildUserForAuthentication(user);
        }else{
            throw new CnpjNotFoundException();
        }
    }

    private UserDetails buildUserForAuthentication(UserModel userModel){
        var user = new UserDetailsImp(userModel);

        return new User(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities()
        );
    }
}
