package com.ReservApp.spring.servicios;

import com.ReservApp.spring.entidades.Usuario;
import com.ReservApp.spring.enumeracion.Role;
import com.ReservApp.spring.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Service
public class UsuarioServicios implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional(rollbackFor = Throwable.class)
    public Usuario crearUsuario(String nombre, String email, String password, String password2) throws Exception{
        validator(nombre,email,password,password2);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Usuario usuario = new Usuario(nombre,email,encoder.encode(password));
        usuario.setRol(Role.USER);
        return usuarioRepositorio.save(usuario);
    }
    
    
    
    public void validator(String nombre, String email, String password, String password2) throws Exception{
          
        if(nombre==null || nombre.trim().isEmpty())
            throw new Exception("Nombre y Apellido no puede estar vacio");

        if(email==null || email.trim().isEmpty())
            throw new Exception("Email no puede estar vacio");
        Usuario u = usuarioRepositorio.findByEmail(email);
        if(u!=null)
            throw new Exception("Ya posees una cuenta registrada!");
        
        if(password==null || password2==null || password.trim().isEmpty() || password2.trim().isEmpty())
            throw new Exception("La contraseña no puede estar vacía");
        if(!password.equals(password2))
            throw new Exception("Las contraseñas no coinciden");
        
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());//ROLE_ADMIN O ROLE_USER
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);
            return user;

        } else {
            return null;
        }
    }
    
    
}
