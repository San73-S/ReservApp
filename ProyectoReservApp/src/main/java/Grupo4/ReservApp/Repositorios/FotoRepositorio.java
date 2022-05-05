package Grupo4.ReservApp.Repositorios;

import Grupo4.ReservApp.Entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepositorio extends JpaRepository<Foto, String> {
    
}
