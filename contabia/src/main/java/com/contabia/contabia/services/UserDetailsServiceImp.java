package com.contabia.contabia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.contabia.contabia.exceptions.CnpjNotFoundException;
import com.contabia.contabia.models.entity.ClientModel;
import com.contabia.contabia.models.entity.UserDetailsImp;
import com.contabia.contabia.models.entity.UserModel;
import com.contabia.contabia.repository.ClientRepository;
import com.contabia.contabia.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientModel> clientOptional = clientRepository.findByUsername(username);

        if(clientOptional.isPresent()){
            ClientModel cliente = clientOptional.get();
            return buildUserForAuthentication(cliente);
        }else{
            throw new CnpjNotFoundException();
        }
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
