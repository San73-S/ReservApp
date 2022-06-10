package com.ReservApp.spring.controladores;

import com.ReservApp.spring.entidades.Usuario;
import com.ReservApp.spring.servicios.ReservaServicios;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/reserva")
public class ReservaController {
    
    @Autowired
    private ReservaServicios reservaServ; 
        
    @GetMapping("")
    public String reserva(ModelMap modelo, HttpSession session){
        LocalDate now = LocalDate.now();
        modelo.addAttribute("now", now);
        modelo.put("usuario",(Usuario) session.getAttribute("usuariosession"));
                
        return "reserva";
    }
    
    @PostMapping("")
    public String guardar(@DateTimeFormat(pattern = "yyyy-MM-dd") Date dia, @RequestParam String turno, ModelMap modelo, HttpSession session) throws Exception{
        try {
            Usuario usuario=(Usuario) session.getAttribute("usuariosession");
            reservaServ.save(usuario.getId(), dia, turno);
            return "index";
        } catch(Exception e){
           // e.printStackTrace();
            modelo.put("reservError", e.getMessage()); 
            modelo.put("diaError",dia);
            modelo.put("turnoError",turno);
            modelo.put("usuario",(Usuario) session.getAttribute("usuariosession"));
            LocalDate now = LocalDate.now();
            modelo.addAttribute("now", now);
            return "/reserva";
        }
    }
}
