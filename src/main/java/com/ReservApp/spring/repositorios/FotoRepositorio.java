package com.ReservApp.spring.repositorios;

import com.ReservApp.spring.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FotoRepositorio extends JpaRepository<Foto, String> {
    
}
