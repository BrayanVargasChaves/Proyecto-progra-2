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
@Table(name = "ESCALERA_COMPLETADA")
@NamedQueries({
    @NamedQuery(name = "EscaleraCompletada.findAll", query = "SELECT e FROM EscaleraCompletada e"),
    @NamedQuery(name = "EscaleraCompletada.findByIdEscalera", query = "SELECT e FROM EscaleraCompletada e WHERE e.idEscalera = :idEscalera"),
    @NamedQuery(name = "EscaleraCompletada.findByVersion", query = "SELECT e FROM EscaleraCompletada e WHERE e.version = :version")})
public class EscaleraCompletada implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESCALERA")
    private BigDecimal idEscalera;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "FK_JUEGO", referencedColumnName = "ID_JUEGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Juego fkJuego;
    @OneToMany(mappedBy = "fkEscalera", fetch = FetchType.LAZY)
    private List<Carta> cartaList;

    public EscaleraCompletada() {
    }

    public EscaleraCompletada(BigDecimal idEscalera) {
        this.idEscalera = idEscalera;
    }

    public EscaleraCompletada(BigDecimal idEscalera, BigInteger version) {
        this.idEscalera = idEscalera;
        this.version = version;
    }

    public BigDecimal getIdEscalera() {
        return idEscalera;
    }

    public void setIdEscalera(BigDecimal idEscalera) {
        this.idEscalera = idEscalera;
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
        hash += (idEscalera != null ? idEscalera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EscaleraCompletada)) {
            return false;
        }
        EscaleraCompletada other = (EscaleraCompletada) object;
        if ((this.idEscalera == null && other.idEscalera != null) || (this.idEscalera != null && !this.idEscalera.equals(other.idEscalera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.EscaleraCompletada[ idEscalera=" + idEscalera + " ]";
    }
    
}
