package Grupo4.ReservApp.Controladores;

import Grupo4.ReservApp.Entidades.Menu;
import Grupo4.ReservApp.Servicios.ServicioMenu;
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
    private ServicioMenu ServMe;
    
    @GetMapping("/menu")
    public ResponseEntity<byte[]> fotosMenu(@RequestParam String id) throws Exception{
        
        Menu M = ServMe.findOne(id);
        byte[] foto = M.getFoto().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);                
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);    
}
}