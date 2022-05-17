package com.ReservApp.spring.controladores;

import com.ReservApp.spring.entidades.Menu;
import com.ReservApp.spring.servicios.MenuServicios;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @Autowired
    MenuServicios ServMenu;

    @GetMapping("")
    public String menu(ModelMap modelo) {
        List<Menu> m = ServMenu.findAll();
        modelo.addAttribute("comidas", m);
        return "menu";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/agregar")
    public String agregarComidas(ModelMap modelo) {        
        return "form-comidas";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/agregar")
    public String guardar(ModelMap modelo, @RequestParam String nombre, @RequestParam Double precio,
                            @RequestParam String ingredientes, @RequestParam String tipo, @RequestParam MultipartFile archivo){
        try {            
            ServMenu.guardar(nombre, precio, ingredientes, tipo, archivo);
            modelo.put("Exito", "Se ingreso correctamente.");
            return "form-comidas";
        } catch (Exception e) {
            modelo.put("Error", "no se ingreso correctamente.");
            return "form-comidas";
        }          
    }
    
     @GetMapping("/lista")
    public String ListaComidas(ModelMap modelo) {    
       List<Menu> M = ServMenu.findAll();
        modelo.addAttribute("comidas", M);
        return "eliminar-comida";
    }
    
     @GetMapping("/modificar/{id}")
    public String modificarEliminarComidas(@PathVariable String id, ModelMap modelo) throws Exception {    
        modelo.put("menu", ServMenu.findOne(id));
        return "form-comidas-mod";
    }
    
     @PostMapping("/modificar/{id}")
    public String modificarEliminarCom(@PathVariable String id,@RequestParam String nombre, 
            @RequestParam Double precio, @RequestParam String ingredientes, @RequestParam String tipo, 
            @RequestParam MultipartFile archivo, ModelMap modelo) throws Exception{
        try {            
            ServMenu.editar(id, nombre, precio, ingredientes, tipo, archivo);
            return "redirect:/menu/lista";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("menu", ServMenu.findOne(id));
            return "form-comidas-mod";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id){
        try {
            ServMenu.eliminar(id);
            return "redirect:/menu/lista";
        } catch (Exception e) {
            return "redirect:/menu/lista";
        }
    }
}
