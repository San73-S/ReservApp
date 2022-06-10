package com.ReservApp.spring.servicios;

import com.ReservApp.spring.entidades.Reserva;
import com.ReservApp.spring.enumeracion.Turno;
import com.ReservApp.spring.repositorios.ReservaRepositorio;
import com.ReservApp.spring.repositorios.UsuarioRepositorio;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservaServicios {
    
    @Autowired
    private ReservaRepositorio reservaRepo;
    
    @Autowired
    private UsuarioRepositorio userRepo;
    
    private SimpleDateFormat formatD = new SimpleDateFormat("yyyy-MM-dd");
        
    public Reserva save(Integer userID,Date dia,String turno) throws Exception{
        
        validator(turno, dia);
        Reserva reserva = new Reserva();
        Integer id = userID;
        reserva.setCliente(userRepo.getById(id));
        reserva.setDia(dia);
        reserva.setTurno(Turno.valueOf(turno));
        return reservaRepo.save(reserva);
        
    }
    
    public void validator(String turno, Date dia) throws Exception{
        
        if(dia==null || turno==null || turno.trim().isEmpty())
            throw new Exception("Debes completar todos los datos de la reserva!");
        if(dia.compareTo(formatD.parse(formatD.format(new Date())))<0)
            throw new Exception("Fecha invalida");
        if(!turno.equalsIgnoreCase("cena") && !turno.equalsIgnoreCase("almuerzo"))
            throw new Exception("Turno invalido");
        List<Reserva> reservas = reservaRepo.findAllByTurnoAndDia(Turno.valueOf(turno), dia);
        if(reservas.size() >= 3)
            throw new Exception("No hay más reservas diponibles para este horario!");
        
    }

}
