/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author emena
 */
@Entity
@Table(name = "MOVIMIENTO", schema="UNA")
@NamedQueries({
    @NamedQuery(name = "Movimiento.findAll", query = "SELECT m FROM Movimiento m"),
    @NamedQuery(name = "Movimiento.findByIdMovimiento", query = "SELECT m FROM Movimiento m WHERE m.idMovimiento = :idMovimiento"),
    @NamedQuery(name = "Movimiento.findByIndiceColumna", query = "SELECT m FROM Movimiento m WHERE m.indiceColumna = :indiceColumna"),
    @NamedQuery(name = "Movimiento.findByIndiceCarta", query = "SELECT m FROM Movimiento m WHERE m.indiceCarta = :indiceCarta"),
    @NamedQuery(name = "Movimiento.findByVersion", query = "SELECT m FROM Movimiento m WHERE m.version = :version")})
public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MOVIMIENTO")
    private Long id;
    @Column(name = "INDICE_COLUMNA")
    private Integer indiceColumna;
    @Column(name = "INDICE_CARTA")
    private Integer indiceCarta;
    @Basic(optional = false)
    @Version
    @Column(name = "VERSION")
    private Long version;
    @JoinColumn(name = "FK_JUEGO", referencedColumnName = "ID_JUEGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Juego fkJuego;

    public Movimiento() {
    }

    public Movimiento(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndiceColumna() {
        return indiceColumna;
    }

    public void setIndiceColumna(Integer indiceColumna) {
        this.indiceColumna = indiceColumna;
    }

    public Integer getIndiceCarta() {
        return indiceCarta;
    }

    public void setIndiceCarta(Integer indiceCarta) {
        this.indiceCarta = indiceCarta;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Juego getFkJuego() {
        return fkJuego;
    }

    public void setFkJuego(Juego fkJuego) {
        this.fkJuego = fkJuego;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimiento)) {
            return false;
        }
        Movimiento other = (Movimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.Movimiento[ idMovimiento=" + id + " ]";
    }
    
}
