package com.alkemy.service;

import java.util.List;
import java.util.Optional;

import com.alkemy.models.dao.IUsuarioDao;
import com.alkemy.models.entity.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.alkemy.models.dao.IGeneroDao;
import com.alkemy.models.dao.IPeliculaDao;
import com.alkemy.models.entity.Genero;
import com.alkemy.models.entity.Pelicula;
import com.alkemy.models.entity.Role;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImp implements IUsuarioService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    public Usuario findOne(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Usuario usuario) {
        usuarioDao.save(usuario);

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


}
