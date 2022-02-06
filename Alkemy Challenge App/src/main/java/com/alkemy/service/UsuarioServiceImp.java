package com.alkemy.service;

import java.util.ArrayList;
import java.util.List;

import com.alkemy.models.dao.IUsuarioDao;
import com.alkemy.models.entity.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.alkemy.models.entity.Role;

import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioServiceImp implements IUsuarioService, UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    @Transactional
        public List<Usuario> findAll() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    @Transactional
    public Usuario findOne(Long id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        usuarioDao.deleteById(id);
    }

    @Override
    @Transactional
    public Usuario findByUserName(String username) {
        Usuario usuarioEncontrado = null;
        for (Usuario u : this.findAll()) {
            if(u.getUsername().equals(username)){
                usuarioEncontrado = u;
                break;
            }
        }
        return usuarioEncontrado;
    }

    /*
     * en este caso voy utilicé un método "crear" para poder aplicarle más fácil la
     * encriptación de la contraseña
     */
    @Override
    @Transactional
    public Usuario create(Usuario usuario) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            usuario.setUsername(usuario.getUsername());
            usuario.setPassword(usuario.getPassword());
            usuario.setEmail(usuario.getEmail());
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            usuario.setRol(Role.USER);
            return usuarioDao.save(usuario);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = findByUserName(username);
            List<GrantedAuthority> permisos = new ArrayList<>();
            permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
            if (usuario.getRol().equals(Role.ADMIN)) {
                permisos.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new User(username, usuario.getPassword(), permisos);

        } catch (Exception e) {
            throw new UsernameNotFoundException("Error");
        }
    }

}
