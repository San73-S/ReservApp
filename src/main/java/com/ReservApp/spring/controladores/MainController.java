package com.ReservApp.spring.controladores;

import com.ReservApp.spring.entidades.Reserva;
import com.ReservApp.spring.entidades.Usuario;
import com.ReservApp.spring.servicios.ReservaServicios;
import com.ReservApp.spring.servicios.UsuarioServicios;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class MainController {
    
    @Autowired
    private UsuarioServicios userServ;
    
    @Autowired
    private ReservaServicios reserServ;
    
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout,ModelMap modelo){
        if (error != null){
            modelo.put("error", "Usuario o Contraseña incorrectos");
        }
        if (logout != null){
            modelo.put("logout","Desconectado correctamente");
        }
        return "login";
    }
        
    @PostMapping("/register")
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2) throws Exception{
        try {
            userServ.crearUsuario(nombre, email, password, password2);
            modelo.put("Exito","Registrado exitosamente.");
            return "login";
        } catch(Exception e){
            modelo.put("RgError", e.getMessage());  
            modelo.put("noExito", "Registro Fallido.");  
            return "login";
        }
    }
    
    @GetMapping("/reservas")
    public String misReservas(ModelMap modelo, HttpSession session){
        Usuario usuario=(Usuario) session.getAttribute("usuariosession");
        List<Reserva> r = reserServ.findAllByUserId(usuario.getId());
        modelo.put("reservas", r);
        return "reservas";
    }
    @GetMapping("reservas/cancelar/{id}")
    public String cancelarReserva(@PathVariable Integer id){
        try {
            reserServ.cancelarReserva(id);
            return "redirect:/reservas";
        } catch (Exception e) {
            return "redirect:/reservas";
        }
    }
    
    @GetMapping("/resto")
    public String resto(){
        return "resto";
    }
}
