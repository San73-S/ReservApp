package com.ReservApp.spring.repositorios;

import com.ReservApp.spring.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
    
    @Query("SELECT c FROM Usuario c WHERE c.email = :email")
    public Usuario findByEmail(@Param("email") String email);
    
}
