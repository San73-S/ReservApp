package com.ReservApp.spring.repositorios;

import com.ReservApp.spring.entidades.Reserva;
import com.ReservApp.spring.enumeracion.Turno;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Integer>{
    
    @Query("SELECT r FROM Reserva r WHERE r.turno = :turno AND r.dia = :dia")
    public List<Reserva> findAllByTurnoAndDia(@Param("turno") Turno turno, @Param("dia") Date dia);
    
}
