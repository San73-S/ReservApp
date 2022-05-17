package com.ReservApp.spring.servicios;

import com.ReservApp.spring.entidades.Menu;
import com.ReservApp.spring.entidades.Foto;
import com.ReservApp.spring.repositorios.FotoRepositorio;
import com.ReservApp.spring.repositorios.MenuRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MenuServicios {

    @Autowired
    MenuRepositorio RepoMenu;
    @Autowired
    FotoRepositorio RepoFoto;
    @Autowired
    FotoServicios ServFoto;

    public void validar(String nombre, Double precio, String ingredientes, String tipo, MultipartFile archivo) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("Nombre inválido.");
        }
        if (precio == null || precio <= 0) {
            throw new Exception("Precio inválido.");
        }

        if (ingredientes == null || ingredientes.isEmpty()) {
            throw new Exception("ingredientes inválido.");
        }       
        
        if (tipo == null || tipo.isEmpty()) {
            throw new Exception("tipo inválido.");
        }   
    }

    @Transactional
    public Menu guardar(String nombre, Double precio, String ingredientes, String tipo, MultipartFile archivo) throws Exception {

        validar(nombre, precio, ingredientes, tipo, archivo);

        Menu entidad = new Menu();
        entidad.setNombre(nombre);
        entidad.setPrecio(precio);
        entidad.setIngredientes(ingredientes);
        entidad.setTipo(tipo);

        Foto foto = ServFoto.guardar(archivo);
        entidad.setFoto(foto);

        return RepoMenu.save(entidad);
    }

     @Transactional
    public Menu editar(String id, String nombre, Double precio, String ingredientes, String tipo, MultipartFile archivo) throws Exception {
        Optional<Menu> menu = RepoMenu.findById(id);

        if (menu.isPresent()) {
            validar(nombre, precio, ingredientes, tipo, archivo);
            Menu entidad = menu.get();
            entidad.setNombre(nombre);
            entidad.setPrecio(precio);
            entidad.setIngredientes(ingredientes);
            entidad.setTipo(tipo);
            
            String idFoto = null;
            if(entidad.getFoto() !=null){
                idFoto = entidad.getFoto().getId();
            }
            
            Foto foto = ServFoto.actualizar(idFoto, archivo);
            entidad.setFoto(foto);
            return RepoMenu.save(entidad);
        } else{
            return null;
        }
    }
    
     @Transactional
    public void eliminar(String id) throws Exception {
        Optional<Menu> menu = RepoMenu.findById(id);
        if (menu.isPresent()) {
           RepoMenu.deleteById(id);
        } else {
            throw new Exception("Id equivocado.");
        }
    }
    
     @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return RepoMenu.findAll();
    }

    @Transactional(readOnly = true)
    public Menu findOne(String id) throws Exception {
        Optional<Menu> M = RepoMenu.findById(id);

        if (M.isPresent()) {
            Menu menu = M.get();
            return menu;
        } else {
            throw new Exception("Id equivocado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Menu> buscarPorTipo(String tipo) throws Exception {
        if (tipo != null) {
            return RepoMenu.buscarPorTipo(tipo);
        } else {
            throw new Exception("tipo equivocado.");
        }
    }
}
