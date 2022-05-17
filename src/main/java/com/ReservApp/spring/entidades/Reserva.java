package com.ReservApp.spring.entidades;

import com.ReservApp.spring.enumeracion.Turno;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Usuario cliente;
    @Temporal(TemporalType.DATE)
    private Date dia;
    @Enumerated(EnumType.STRING)
    private Turno turno;

    public Reserva() {
    }

    public Reserva(Usuario cliente, Turno turno, Date dia) {
        this.cliente = cliente;
        this.turno = turno;
        this.dia = dia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Enum getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "Reserva{" + "id=" + id + ", cliente=" + cliente + ", dia=" + dia + ", turno=" + turno + '}';
    }

}
