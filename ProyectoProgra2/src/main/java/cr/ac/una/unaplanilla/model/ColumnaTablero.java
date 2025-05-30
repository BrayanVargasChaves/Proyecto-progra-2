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
@Table(name = "COLUMNA_TABLERO")
@NamedQueries({
    @NamedQuery(name = "ColumnaTablero.findAll", query = "SELECT c FROM ColumnaTablero c"),
    @NamedQuery(name = "ColumnaTablero.findByIdColumnaTablero", query = "SELECT c FROM ColumnaTablero c WHERE c.idColumnaTablero = :idColumnaTablero"),
    @NamedQuery(name = "ColumnaTablero.findByIndiceColumna", query = "SELECT c FROM ColumnaTablero c WHERE c.indiceColumna = :indiceColumna"),
    @NamedQuery(name = "ColumnaTablero.findByVersion", query = "SELECT c FROM ColumnaTablero c WHERE c.version = :version")})
public class ColumnaTablero implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_COLUMNA_TABLERO")
    private BigDecimal idColumnaTablero;
    @Column(name = "INDICE_COLUMNA")
    private BigInteger indiceColumna;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @JoinColumn(name = "FK_JUEGO", referencedColumnName = "ID_JUEGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Juego fkJuego;
    @OneToMany(mappedBy = "fkColumnaTablero", fetch = FetchType.LAZY)
    private List<Carta> cartaList;

    public ColumnaTablero() {
    }

    public ColumnaTablero(BigDecimal idColumnaTablero) {
        this.idColumnaTablero = idColumnaTablero;
    }

    public ColumnaTablero(BigDecimal idColumnaTablero, BigInteger version) {
        this.idColumnaTablero = idColumnaTablero;
        this.version = version;
    }

    public BigDecimal getIdColumnaTablero() {
        return idColumnaTablero;
    }

    public void setIdColumnaTablero(BigDecimal idColumnaTablero) {
        this.idColumnaTablero = idColumnaTablero;
    }

    public BigInteger getIndiceColumna() {
        return indiceColumna;
    }

    public void setIndiceColumna(BigInteger indiceColumna) {
        this.indiceColumna = indiceColumna;
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
        hash += (idColumnaTablero != null ? idColumnaTablero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColumnaTablero)) {
            return false;
        }
        ColumnaTablero other = (ColumnaTablero) object;
        if ((this.idColumnaTablero == null && other.idColumnaTablero != null) || (this.idColumnaTablero != null && !this.idColumnaTablero.equals(other.idColumnaTablero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.ColumnaTablero[ idColumnaTablero=" + idColumnaTablero + " ]";
    }
    
}
