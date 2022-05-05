package Grupo4.ReservApp.Servicios;

import Grupo4.ReservApp.Entidades.Foto;
import Grupo4.ReservApp.Repositorios.FotoRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServicioFoto {

    @Autowired
    FotoRepositorio RepoFoto;

    public Foto guardar(MultipartFile archivo) throws Exception {

        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return RepoFoto.save(foto);
            } catch (Exception e) {
                throw new Exception("Error al cargar imagen");
            }
        }
        return null;
    }

    public Foto actualizar(String idFoto, MultipartFile archivo) throws Exception {

        if (archivo != null) {
            try {
                Foto foto = new Foto();
                if (idFoto != null) {
                    Optional<Foto> res = RepoFoto.findById(idFoto);
                    if (res.isPresent()) {
                        foto = res.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return RepoFoto.save(foto);
            } catch (Exception e) {
                throw new Exception("Error al Modificar la imagen");
            }
        }
        return null;
    }
}
