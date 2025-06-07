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
    private IntegerProperty estado;
    private Long version;
    private Boolean modificado;

    /**
     * Constructor por defecto, inicializa propiedades con valores por defecto.
     */
    public MazoDto() {
        this.id = new SimpleStringProperty("");
        this.estado = new SimpleIntegerProperty(0);
        this.modificado = false;
    }

    /**
     * Constructor que mapea desde la entidad Mazo
     */
    public MazoDto(Mazo mazo) {
        this();
        if (mazo.getId() != null) {
            this.id.set(mazo.getId().toString());
        }
        if (mazo.getEstado() != null) {
            this.estado.set(mazo.getEstado());
        }
        this.version = mazo.getVersion();
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

    public Integer getEstado() {
        return estado.get();
    }

    public void setEstado(Integer estado) {
        this.estado.set(estado);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    // Property getters para binding
    public StringProperty getIdProperty() {
        return id;
    }

    public IntegerProperty getEstadoProperty() {
        return estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MazoDto other = (MazoDto) obj;
        return Objects.equals(this.id.get(), other.id.get());
    }

    @Override
    public String toString() {
        return "MazoDto{"
                + "id=" + id.get()
                + ", estado=" + estado.get()
                + '}';
    }
}
