/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model.dtos;

/**
 *
 * @author emena
 */
import cr.ac.una.proyectoprogra2.model.ColumnaTablero;
import javafx.beans.property.*;
import java.util.Objects;

/**
 * DTO para la entidad ColumnaTablero, usando propiedades JavaFX para binding en UI.
 */
public class ColumnaTableroDto {

    private StringProperty id;
    private IntegerProperty indiceColumna;
    private Long version;
    private LongProperty fkJuegoId;
    private Boolean modificado;

    /** Constructor por defecto, inicializa propiedades con valores por defecto. */
    public ColumnaTableroDto() {
        this.id = new SimpleStringProperty("");
        this.indiceColumna = new SimpleIntegerProperty(0);
        this.fkJuegoId = new SimpleLongProperty(0L);
        this.modificado = false;
    }

    /** Constructor desde la entidad ColumnaTablero */
    public ColumnaTableroDto(ColumnaTablero col) {
        this();
        if (col.getId() != null) {
            this.id.set(col.getId().toString());
        }
        if (col.getIndiceColumna() != null) {
            this.indiceColumna.set(col.getIndiceColumna());
        }
        this.version = col.getVersion();
        if (col.getFkJuego() != null && col.getFkJuego().getId() != null) {
            this.fkJuegoId.set(col.getFkJuego().getId());
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
    public void setModificado(Boolean mod) {
        this.modificado = mod;
    }

    // Property getters para binding
    public StringProperty idProperty() {
        return id;
    }
    public IntegerProperty indiceColumnaProperty() {
        return indiceColumna;
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
        if (!(obj instanceof ColumnaTableroDto)) return false;
        ColumnaTableroDto other = (ColumnaTableroDto) obj;
        return Objects.equals(getId(), other.getId());
    }
    @Override
    public String toString() {
        return "ColumnaTableroDto{" +
               "id=" + id.get() +
               ", indiceColumna=" + indiceColumna.get() +
               '}';
    }
}

