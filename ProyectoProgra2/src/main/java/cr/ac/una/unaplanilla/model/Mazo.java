/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanilla.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "MAZO")
@NamedQueries({
    @NamedQuery(name = "Mazo.findAll", query = "SELECT m FROM Mazo m"),
    @NamedQuery(name = "Mazo.findByIdMazo", query = "SELECT m FROM Mazo m WHERE m.idMazo = :idMazo"),
    @NamedQuery(name = "Mazo.findByEstado", query = "SELECT m FROM Mazo m WHERE m.estado = :estado"),
    @NamedQuery(name = "Mazo.findByVersion", query = "SELECT m FROM Mazo m WHERE m.version = :version")})
public class Mazo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MAZO")
    private BigDecimal idMazo;
    @Column(name = "ESTADO")
    private Short estado;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "FK_JUEGO", referencedColumnName = "ID_JUEGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Juego fkJuego;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkMazo", fetch = FetchType.LAZY)
    private List<Carta> cartaList;

    public Mazo() {
    }

    public Mazo(BigDecimal idMazo) {
        this.idMazo = idMazo;
    }

    public Mazo(BigDecimal idMazo, BigInteger version) {
        this.idMazo = idMazo;
        this.version = version;
    }

    public BigDecimal getIdMazo() {
        return idMazo;
    }

    public void setIdMazo(BigDecimal idMazo) {
        this.idMazo = idMazo;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
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

    public List<Carta> getCartaList() {
        return cartaList;
    }

    public void setCartaList(List<Carta> cartaList) {
        this.cartaList = cartaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMazo != null ? idMazo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mazo)) {
            return false;
        }
        Mazo other = (Mazo) object;
        if ((this.idMazo == null && other.idMazo != null) || (this.idMazo != null && !this.idMazo.equals(other.idMazo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.Mazo[ idMazo=" + idMazo + " ]";
    }
    
}
