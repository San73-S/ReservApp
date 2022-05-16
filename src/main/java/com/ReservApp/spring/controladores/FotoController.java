package com.ReservApp.spring.controladores;

import com.ReservApp.spring.entidades.Menu;
import com.ReservApp.spring.servicios.MenuServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/foto")
public class FotoController {
   
    @Autowired
    private MenuServicios ServMe;
    
    @GetMapping("/menu")
    public ResponseEntity<byte[]> fotosMenu(@RequestParam String id) throws Exception{
        
        Menu M = ServMe.findOne(id);
        byte[] foto = M.getFoto().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);                
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);    
}
}