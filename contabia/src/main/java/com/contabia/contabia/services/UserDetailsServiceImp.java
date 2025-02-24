package com.contabia.contabia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.contabia.contabia.models.entity.ClientModel;
import com.contabia.contabia.models.entity.UserDetailsImp;
import com.contabia.contabia.repository.ClientRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        ClientModel client = clientRepository.findByUsername(username).get();

        ClientModel cliente = client;
        return buildUserForAuthentication(cliente);
    }

    private UserDetails buildUserForAuthentication(ClientModel clientModel){
        var user = new UserDetailsImp(clientModel);

        return new User(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities()
        );
    }
}
