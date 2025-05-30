/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanilla.model;

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
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "MOVIMIENTO")
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
    private BigDecimal idMovimiento;
    @Column(name = "INDICE_COLUMNA")
    private BigInteger indiceColumna;
    @Column(name = "INDICE_CARTA")
    private BigInteger indiceCarta;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "FK_JUEGO", referencedColumnName = "ID_JUEGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Juego fkJuego;

    public Movimiento() {
    }

    public Movimiento(BigDecimal idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Movimiento(BigDecimal idMovimiento, BigInteger version) {
        this.idMovimiento = idMovimiento;
        this.version = version;
    }

    public BigDecimal getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(BigDecimal idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public BigInteger getIndiceColumna() {
        return indiceColumna;
    }

    public void setIndiceColumna(BigInteger indiceColumna) {
        this.indiceColumna = indiceColumna;
    }

    public BigInteger getIndiceCarta() {
        return indiceCarta;
    }

    public void setIndiceCarta(BigInteger indiceCarta) {
        this.indiceCarta = indiceCarta;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
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
        hash += (idMovimiento != null ? idMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimiento)) {
            return false;
        }
        Movimiento other = (Movimiento) object;
        if ((this.idMovimiento == null && other.idMovimiento != null) || (this.idMovimiento != null && !this.idMovimiento.equals(other.idMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.Movimiento[ idMovimiento=" + idMovimiento + " ]";
    }
    
}
