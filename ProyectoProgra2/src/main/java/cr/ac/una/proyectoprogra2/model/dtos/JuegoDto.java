/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model.dtos;

import cr.ac.una.proyectoprogra2.model.Juego;
import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author emena
 */
public class JuegoDto {

    private StringProperty id;
    private StringProperty dificultad;
    private StringProperty estado;
    private ObjectProperty<LocalDate> fechaHora;
    private IntegerProperty puntaje;
    private IntegerProperty tiempoTranscurrido;
    private Long version;
    private LongProperty fkUsuarioId;
    private Boolean modificado;

    /**
     * Constructor por defecto, inicializa propiedades con valores por defecto.
     */
    public JuegoDto() {
        this.id = new SimpleStringProperty("");
        this.dificultad = new SimpleStringProperty("");
        this.estado = new SimpleStringProperty("");
        this.fechaHora = new SimpleObjectProperty<>(LocalDate.now());
        this.puntaje = new SimpleIntegerProperty(0);
        this.tiempoTranscurrido = new SimpleIntegerProperty(0);
        this.fkUsuarioId = new SimpleLongProperty(0L);
        this.modificado = false;
    }

    /**
     * Constructor que mapea desde la entidad Juego
     */
    public JuegoDto(Juego juego) {
        this();
        if (juego.getId() != null) {
            this.id.set(juego.getId().toString());
        }
        if (juego.getDificultad() != null) {
            this.dificultad.set(juego.getDificultad());
        }
        if (juego.getEstado() != null) {
            this.estado.set(juego.getEstado());
        }
        if (juego.getFechaHora() != null) {
            this.fechaHora.set(juego.getFechaHora());
        }
        if (juego.getPuntaje() != null) {
            this.puntaje.set(juego.getPuntaje());
        }
        if (juego.getTiempoTranscurrido() != null) {
            this.tiempoTranscurrido.set(juego.getTiempoTranscurrido());
        }
        this.version = juego.getVersion();
        if (juego.getFkUsuario() != null && juego.getFkUsuario().getId() != null) {
            this.fkUsuarioId.set(juego.getFkUsuario().getId());
        }
    }

    // Getters y setters de valor
    public Long getId() {
        if (id.get() != null && !id.get().isBlank()) {
            return Long.valueOf(id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getDificultad() {
        return dificultad.get();
    }

    public void setDificultad(String dificultad) {
        this.dificultad.set(dificultad);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public LocalDate getFechaHora() {
        return fechaHora.get();
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora.set(fechaHora);
    }

    public int getPuntaje() {
        return puntaje.get();
    }

    public void setPuntaje(int puntaje) {
        this.puntaje.set(puntaje);
    }

    public int getTiempoTranscurrido() {
        return tiempoTranscurrido.get();
    }

    public void setTiempoTranscurrido(int tiempoTranscurrido) {
        this.tiempoTranscurrido.set(tiempoTranscurrido);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getFkUsuarioId() {
        return fkUsuarioId.get();
    }

    public void setFkUsuarioId(Long usuarioId) {
        this.fkUsuarioId.set(usuarioId);
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    // Property getters para binding
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty dificultadProperty() {
        return dificultad;
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public ObjectProperty<LocalDate> fechaHoraProperty() {
        return fechaHora;
    }

    public IntegerProperty puntajeProperty() {
        return puntaje;
    }

    public IntegerProperty tiempoTranscurridoProperty() {
        return tiempoTranscurrido;
    }

    public LongProperty fkUsuarioIdProperty() {
        return fkUsuarioId;
    }

    // equals, hashCode y toString
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JuegoDto)) {
            return false;
        }
        JuegoDto other = (JuegoDto) obj;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public String toString() {
        return "JuegoDto{"
                + "id=" + id.get()
                + ", dificultad=" + dificultad.get()
                + ", estado=" + estado.get()
                + '}';
    }
}
