package cr.ac.una.proyectoprogra2.service;

import cr.ac.una.proyectoprogra2.model.Usuario;
import cr.ac.una.proyectoprogra2.model.dtos.UsuarioDto;
import cr.ac.una.proyectoprogra2.util.EntityManagerHelper;
import cr.ac.una.proyectoprogra2.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emena
 */
public class UsuarioService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    // Obtener usuario por id
    public Respuesta getUsuario(String nombreUsuario) {
        try {
            Query qryUsuario = em.createNamedQuery("Usuario.findByNombreUsuario", Usuario.class);
            qryUsuario.setParameter("nombreUsuario", nombreUsuario);
            return new Respuesta(true, "Usuario encontrado", new UsuarioDto((Usuario) qryUsuario.getSingleResult()));
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un usuario con el id ingresado.", "getUsuario NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error al consultar el usuario.", ex);
            return new Respuesta(false, "Error al consultar el usuario.", "getUsuario NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        }
    }

    // Guardar o actualizar usuario
    public Respuesta guardarUsuario(UsuarioDto usuarioDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Usuario usuario;

            if (usuarioDto.getId() != null) {
                usuario = em.find(Usuario.class, usuarioDto.getId());
                if (usuario == null) {
                    return new Respuesta(false, "No se encontró el usuario a modificar.", "guardarUsuario NoResultException");
                }
                usuario.actualizar(usuarioDto);
                usuario = em.merge(usuario);
            } else {
                usuario = new Usuario(usuarioDto);
                em.persist(usuario);
            }
            et.commit();

            return new Respuesta(true, "Usuario guardado", new UsuarioDto(usuario));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el usuario", ex);
            return new Respuesta(false, "Error guardando el usuario.", "guardarUsuario " + ex.getMessage());
        }
    }

    // Eliminar usuario
    public Respuesta eliminarUsuario(Long id) {
        try {
            Usuario usuario;
            if (id != null) {
                usuario = em.find(Usuario.class, id);
                if (usuario == null) {
                    return new Respuesta(false, "No se encontró el usuario a eliminar.", "eliminarUsuario NoResultException");
                }
                et = em.getTransaction();
                et.begin();
                em.remove(usuario);
                et.commit();
            } else {
                return new Respuesta(false, "No se encontró el usuario a eliminar.", "eliminarUsuario");
            }
            return new Respuesta(true, "Usuario eliminado", new UsuarioDto(usuario));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error eliminando el usuario", ex);
            return new Respuesta(false, "Error eliminando el usuario.", "eliminarUsuario " + ex.getMessage());
        }
    }
}
