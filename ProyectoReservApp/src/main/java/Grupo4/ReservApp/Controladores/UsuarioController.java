
package Grupo4.ReservApp.Controladores;

import Grupo4.ReservApp.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    ServicioUsuario ServUs;

     @GetMapping("/registrar")
    public String login() {
        return "login";
    }
    
    @GetMapping("/ingreso")
    public String ingreso(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, @RequestParam(required = false) String Exito, ModelMap model){
        
        if (error != null) {
            model.put("error", "Usuario o Contraseña incorrectos");
        }
        if(Exito != null){
            model.put("Exito","Registrado exitosamente.");
        }    
        if (logout != null) {
            model.put("logout","Desconectado correctamente");
        }
        return "login";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap Model, @RequestParam String Nombre , @RequestParam String Email, @RequestParam String Clave1, @RequestParam String Clave2) {
        try {
            ServUs.save(Nombre, Email, Clave1, Clave2);            
             return "redirect:/usuario/ingreso?Exito";
        } catch (Exception ex) {
            Model.put("RgError", ex.getMessage());   
            return "login";
        }
    }
}