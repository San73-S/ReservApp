package Grupo4.ReservApp.Controladores;

import Grupo4.ReservApp.Servicios.ServicioUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    ServicioUsuario ServUs;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }
/*
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap Model, @RequestParam String NombreApellido, @RequestParam String Email, @RequestParam String Clave1, @RequestParam String Clave2) {
        try {
            ServUs.save(NombreApellido, Email, Clave1, Clave2);
        } catch (Exception ex) {
            Model.addAttribute("Error", ex.getMessage());
            return "login.html";
        }
        return "index.html";
    }

    
    @GetMapping("/menu")
    public String menu() {
        return "menu.html";
    }

    @GetMapping("/reserva")
    public String Reserva() {
        return "Reserva.html";
    }
@GetMapping("/lista-comidas")
    public String comidas() {
        return "form-comidas.html";
    }*/

}
