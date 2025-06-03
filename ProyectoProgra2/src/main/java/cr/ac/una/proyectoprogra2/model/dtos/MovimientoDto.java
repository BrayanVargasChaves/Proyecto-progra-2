/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model.dtos;

import cr.ac.una.proyectoprogra2.model.Movimiento;
import javafx.beans.property.*;
import java.util.Objects;

/**
 *
 * @author emena
 */
public class MovimientoDto {

    private StringProperty id;
    private IntegerProperty indiceColumna;
    private IntegerProperty indiceCarta;
    private Long version;
    private LongProperty fkJuegoId;
    private Boolean modificado;

    /** Constructor por defecto, inicializa propiedades con valores por defecto. */
    public MovimientoDto() {
        this.id = new SimpleStringProperty("");
        this.indiceColumna = new SimpleIntegerProperty(0);
        this.indiceCarta = new SimpleIntegerProperty(0);
        this.fkJuegoId = new SimpleLongProperty(0L);
        this.modificado = false;
    }

    /** Constructor que mapea desde la entidad Movimiento */
    public MovimientoDto(Movimiento mov) {
        this();
        if (mov.getId() != null) {
            this.id.set(mov.getId().toString());
        }
        if (mov.getIndiceColumna() != null) {
            this.indiceColumna.set(mov.getIndiceColumna());
        }
        if (mov.getIndiceCarta() != null) {
            this.indiceCarta.set(mov.getIndiceCarta());
        }
        this.version = mov.getVersion();
        if (mov.getFkJuego() != null && mov.getFkJuego().getId() != null) {
            this.fkJuegoId.set(mov.getFkJuego().getId());
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

    public int getIndiceColumna() {
        return indiceColumna.get();
    }
    public void setIndiceColumna(int indice) {
        this.indiceColumna.set(indice);
    }

    public int getIndiceCarta() {
        return indiceCarta.get();
    }
    public void setIndiceCarta(int indice) {
        this.indiceCarta.set(indice);
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
    public IntegerProperty indiceColumnaProperty() {
        return indiceColumna;
    }
    public IntegerProperty indiceCartaProperty() {
        return indiceCarta;
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
        if (!(obj instanceof MovimientoDto)) return false;
        MovimientoDto other = (MovimientoDto) obj;
        return Objects.equals(getId(), other.getId());
    }
    @Override
    public String toString() {
        return "MovimientoDto{" +
                "id=" + id.get() +
                ", indiceColumna=" + indiceColumna.get() +
                ", indiceCarta=" + indiceCarta.get() +
                '}';
    }
}

