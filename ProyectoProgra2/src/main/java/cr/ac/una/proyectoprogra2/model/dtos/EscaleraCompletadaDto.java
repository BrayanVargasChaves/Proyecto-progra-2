/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model.dtos;

import cr.ac.una.proyectoprogra2.model.EscaleraCompletada;
import javafx.beans.property.*;
import java.util.Objects;
import java.util.List;

/**
 *
 * @author emena
 */
public class EscaleraCompletadaDto {

    private StringProperty id;
    private Long version;
    private LongProperty fkJuegoId;
    private Boolean modificado;

    /**
     * Constructor por defecto, inicializa propiedades con valores por defecto.
     */
    public EscaleraCompletadaDto() {
        this.id = new SimpleStringProperty("");
        this.fkJuegoId = new SimpleLongProperty(0L);
        this.modificado = false;
    }

    /**
     * Constructor que mapea desde la entidad EscaleraCompletada
     */
    public EscaleraCompletadaDto(EscaleraCompletada escalera) {
        this();
        if (escalera.getId() != null) {
            this.id.set(escalera.getId().toString());
        }
        this.version = escalera.getVersion();
        if (escalera.getFkJuego() != null && escalera.getFkJuego().getId() != null) {
            this.fkJuegoId.set(escalera.getFkJuego().getId());
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
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EscaleraCompletadaDto)) {
            return false;
        }
        EscaleraCompletadaDto other = (EscaleraCompletadaDto) obj;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public String toString() {
        return "EscaleraCompletadaDto{"
                + "id=" + id.get()
                + "} ";
    }
}
