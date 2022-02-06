package com.alkemy.service;

import java.util.List;
import com.alkemy.models.entity.Usuario;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUsuarioService {
    
    public Usuario create (Usuario usuario);
    public List <Usuario> findAll();
    public void save (Usuario usuario);
    public Usuario findOne(Long id); 
    public Usuario findByUserName(String username); 
    public void delete(Long id);






}
