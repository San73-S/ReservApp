package Grupo4.ReservApp.Servicios;

import Grupo4.ReservApp.Entidades.Usuario;
import Grupo4.ReservApp.Repositorios.UsuarioRepositorio;
import enunm.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class ServicioUsuario implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usRepositorio;

    public void validar(String Nombre, String email, String password, String password2) throws Exception {

        if (Nombre == null || Nombre.isEmpty()) {
            throw new Exception("Nombre incorrecto.");
        }

        if (email == null || email.isEmpty()) {
            throw new Exception("Email incorrecto.");
        }
        
        /*VERIFICACION DE TODOS LOS EMAIL*/
        
        if(usRepositorio.buscarPorEmail(email) != null){
             throw new Exception("Email Ya registrado.");
        }
        
        if (password == null || password.isEmpty() || password.length() < 5) {
            throw new Exception("Contraseña incorrecta.");
        }
        
        if (!password.equals(password2) )  {
            throw new Exception("Las contraseñas no pueden ser distintas.");
        }
    }

    @Transactional
    public Usuario save(String Nombre,  String email, String password, String password2) throws Exception {
        validar(Nombre,  email, password, password2);
        Usuario entidad = new Usuario();
        entidad.setNombreApellido(Nombre);
        entidad.setEmail(email);
        String pwEncriptada = new BCryptPasswordEncoder().encode(password);
        entidad.setPassword(pwEncriptada);
        entidad.setRole(Role.USER);
        return usRepositorio.save(entidad);
    }
    
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Usuario findOne(String id) throws Exception {
        Optional<Usuario> M = usRepositorio.findById(id);

        if (M.isPresent()) {
            return usRepositorio.getById(id);
        } else {
            throw new Exception("Id equivocado.");
        }
    }
    
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usRepositorio.buscarPorEmail(email);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority permiso1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRole());
            permisos.add(permiso1);
//            GrantedAuthority permiso2 = new SimpleGrantedAuthority("ROLE_ADMIN");
//            permisos.add(permiso2);

            //Guarda la session de usuario. el usuario lo guarda en "usuariosession"
            //session guarda los datos de "usuariosession" y se puede manipular en html/thymeleaf
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
