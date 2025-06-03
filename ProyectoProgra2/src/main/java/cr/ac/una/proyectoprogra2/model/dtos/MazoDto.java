/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model.dtos;

import cr.ac.una.proyectoprogra2.model.Mazo;
import javafx.beans.property.*;
import java.util.Objects;

/**
 *
 * @author emena
 */
public class MazoDto {

    private StringProperty id;
    private ObjectProperty<Short> estado;
    private Long version;
    private LongProperty fkJuegoId;
    private Boolean modificado;

    /** Constructor por defecto, inicializa propiedades con valores por defecto. */
    public MazoDto() {
        this.id = new SimpleStringProperty("");
        this.estado = new SimpleObjectProperty<>((short)0);
        this.fkJuegoId = new SimpleLongProperty(0L);
        this.modificado = false;
    }

    /** Constructor que mapea desde la entidad Mazo */
    public MazoDto(Mazo mazo) {
        this();
        if (mazo.getId() != null) {
            this.id.set(mazo.getId().toString());
        }
        if (mazo.getEstado() != null) {
            this.estado.set(mazo.getEstado());
        }
        this.version = mazo.getVersion();
        if (mazo.getFkJuego() != null && mazo.getFkJuego().getId() != null) {
            this.fkJuegoId.set(mazo.getFkJuego().getId());
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

    public Short getEstado() {
        return estado.get();
    }
    public void setEstado(Short estado) {
        this.estado.set(estado);
    }

    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getFkJuegoId() {
        return fkJuegoId.get();
    }
    public void setFkJuegoId(Long juegoId) {
        this.fkJuegoId.set(juegoId);
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

    public ObjectProperty<Short> estadoProperty() {
        return estado;
    }

    public LongProperty fkJuegoIdProperty() {
        return fkJuegoId;
    }

    // equals, hashCode y toString
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MazoDto)) return false;
        MazoDto other = (MazoDto) obj;
        return Objects.equals(getId(), other.getId());
    }
    @Override
    public String toString() {
        return "MazoDto{" +
                "id=" + id.get() +
                ", estado=" + estado.get() +
                '}';
    }
}

