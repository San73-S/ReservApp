/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grupo4.ReservApp.configuraciones;

import Grupo4.ReservApp.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Magno
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad extends WebSecurityConfigurerAdapter{
    @Autowired
    private ServicioUsuario userv;
    
    @Autowired
    public void configuracionGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userv)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
//    @Autowired
//    private ServicioUsuario servicioUsuario;
//
//    //metodo que indica que en el servicio se usara metodos de seguridad(en este caso encriptado)
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(servicioUsuario).passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/css/","/js/","/img/*","/**").permitAll()
                .and().formLogin()         //configurando el login
                .loginPage("/usuario/ingreso")        //ubicacion del login
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")        //nombre con que viajan los datos
                .passwordParameter("password")
                .defaultSuccessUrl("/").permitAll()
            .and().logout()  //configurando la salida (logout/deslogeo)
                .logoutUrl("/logout")           // se deslogea desde esta url
                .logoutSuccessUrl("/usuario/ingreso?logout").permitAll()         // nos redirige aquí
            .and().csrf().disable();
    }
}
