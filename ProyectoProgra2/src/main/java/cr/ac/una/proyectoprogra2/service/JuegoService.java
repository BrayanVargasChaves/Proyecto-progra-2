/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.service;

/**
 *
 * @author emena
 */
import cr.ac.una.proyectoprogra2.model.Juego;
import cr.ac.una.proyectoprogra2.model.Mazo;
import cr.ac.una.proyectoprogra2.model.ColumnaTablero;
import cr.ac.una.proyectoprogra2.model.Carta;
import cr.ac.una.proyectoprogra2.model.EscaleraCompletada;
import cr.ac.una.proyectoprogra2.util.EntityManagerHelper;
import cr.ac.una.proyectoprogra2.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JuegoService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    // Método para guardar un nuevo juego o actualizar uno existente
    public Respuesta guardarJuego(Juego juego) {
        try {
            et = em.getTransaction();
            et.begin();

            // Verificar si el juego ya existe
            if (juego.getId() != null) {
                Juego existingJuego = em.find(Juego.class, juego.getId());
                if (existingJuego == null) {
                    return new Respuesta(false, "No se encontró el juego a modificar.", "guardarJuego NoResultException");
                }
                // Actualizamos el juego
                existingJuego.setEstado(juego.getEstado());
                existingJuego.setPuntaje(juego.getPuntaje());
                existingJuego.setTiempoTranscurrido(juego.getTiempoTranscurrido());
                em.merge(existingJuego);
            } else {
                // Si no existe, creamos un nuevo juego
                em.persist(juego);
            }

            et.commit();
            return new Respuesta(true, "Juego guardado exitosamente.", juego);
        } catch (Exception ex) {
            if (et.isActive()) {
                et.rollback();
            }
            Logger.getLogger(JuegoService.class.getName()).log(Level.SEVERE, "Error guardando el juego", ex);
            return new Respuesta(false, "Error guardando el juego.", "guardarJuego " + ex.getMessage());
        }
    }

    // Método para obtener un juego por ID
    public Respuesta obtenerJuego(Long idJuego) {
        try {
            Juego juego = em.find(Juego.class, idJuego);
            if (juego == null) {
                return new Respuesta(false, "No se encontró el juego con el id proporcionado.", "obtenerJuego NoResultException");
            }
            return new Respuesta(true, "Juego encontrado.", juego);
        } catch (Exception ex) {
            Logger.getLogger(JuegoService.class.getName()).log(Level.SEVERE, "Error obteniendo el juego", ex);
            return new Respuesta(false, "Error obteniendo el juego.", "obtenerJuego " + ex.getMessage());
        }
    }

    // Método para eliminar un juego
    public Respuesta eliminarJuego(Long idJuego) {
        try {
            Juego juego = em.find(Juego.class, idJuego);
            if (juego == null) {
                return new Respuesta(false, "No se encontró el juego a eliminar.", "eliminarJuego NoResultException");
            }
            et = em.getTransaction();
            et.begin();
            em.remove(juego);  // Eliminar el juego de la base de datos
            et.commit();
            return new Respuesta(true, "Juego eliminado exitosamente.", null);
        } catch (Exception ex) {
            if (et.isActive()) {
                et.rollback();
            }
            Logger.getLogger(JuegoService.class.getName()).log(Level.SEVERE, "Error eliminando el juego", ex);
            return new Respuesta(false, "Error eliminando el juego.", "eliminarJuego " + ex.getMessage());
        }
    }

    // Método para guardar el estado de un mazo
    public Respuesta guardarMazo(Mazo mazo) {
        try {
            et = em.getTransaction();
            et.begin();
            if (mazo.getId() != null) {
                Mazo existingMazo = em.find(Mazo.class, mazo.getId());
                if (existingMazo == null) {
                    return new Respuesta(false, "No se encontró el mazo a modificar.", "guardarMazo NoResultException");
                }
                existingMazo.setEstado(mazo.getEstado());
                em.merge(existingMazo);
            } else {
                em.persist(mazo);
            }
            et.commit();
            return new Respuesta(true, "Mazo guardado exitosamente.", mazo);
        } catch (Exception ex) {
            if (et.isActive()) {
                et.rollback();
            }
            Logger.getLogger(JuegoService.class.getName()).log(Level.SEVERE, "Error guardando el mazo", ex);
            return new Respuesta(false, "Error guardando el mazo.", "guardarMazo " + ex.getMessage());
        }
    }

    // Método para guardar las cartas en las columnas
    public Respuesta guardarCartasEnColumna(ColumnaTablero columnaTablero) {
        try {
            et = em.getTransaction();
            et.begin();
            for (Carta carta : columnaTablero.getCartas()) {
                em.persist(carta);  // Guardar cada carta de la columna
            }
            et.commit();
            return new Respuesta(true, "Cartas guardadas en la columna.", columnaTablero);
        } catch (Exception ex) {
            if (et.isActive()) {
                et.rollback();
            }
            Logger.getLogger(JuegoService.class.getName()).log(Level.SEVERE, "Error guardando las cartas en la columna", ex);
            return new Respuesta(false, "Error guardando las cartas en la columna.", "guardarCartasEnColumna " + ex.getMessage());
        }
    }

    public Respuesta guardarEscaleraCompletada(EscaleraCompletada escalera) {
        try {
            et = em.getTransaction();
            et.begin();

            // Primero, guardamos la escalera completada
            em.persist(escalera);

            // Luego, guardamos las cartas que están asociadas a esta escalera
            for (Carta carta : escalera.getCartas()) {
                carta.setFkEscalera(escalera);  // Asociamos cada carta con esta escalera
                em.persist(carta);  // Guardamos la carta
            }

            et.commit();
            return new Respuesta(true, "Escalera completada guardada exitosamente.", escalera);
        } catch (Exception ex) {
            if (et.isActive()) {
                et.rollback();
            }
            Logger.getLogger(JuegoService.class.getName()).log(Level.SEVERE, "Error guardando la escalera completada", ex);
            return new Respuesta(false, "Error guardando la escalera completada.", "guardarEscaleraCompletada " + ex.getMessage());
        }
    }
}

