package com.ReservApp.spring.repositorios;

import com.ReservApp.spring.entidades.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuRepositorio extends JpaRepository<Menu, String>  {
    
    @Query("SELECT m FROM Menu m WHERE m.tipo = :tipo")    
    public List<Menu> buscarPorTipo(@Param("tipo") String tipo); 
     
}
