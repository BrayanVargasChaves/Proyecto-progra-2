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
@Table(name = "CARTA", schema="UNA")
@NamedQueries({
    @NamedQuery(name = "Carta.findAll", query = "SELECT c FROM Carta c"),
    @NamedQuery(name = "Carta.findByIdCarta", query = "SELECT c FROM Carta c WHERE c.idCarta = :idCarta"),
    @NamedQuery(name = "Carta.findByIndicePosCarta", query = "SELECT c FROM Carta c WHERE c.indicePosCarta = :indicePosCarta"),
    @NamedQuery(name = "Carta.findByNumeroCarta", query = "SELECT c FROM Carta c WHERE c.numeroCarta = :numeroCarta"),
    @NamedQuery(name = "Carta.findByPaloCarta", query = "SELECT c FROM Carta c WHERE c.paloCarta = :paloCarta"),
    @NamedQuery(name = "Carta.findByBocaArriba", query = "SELECT c FROM Carta c WHERE c.bocaArriba = :bocaArriba"),
    @NamedQuery(name = "Carta.findByVersion", query = "SELECT c FROM Carta c WHERE c.version = :version")})
public class Carta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CARTA")
    private Long id;
    @Column(name = "INDICE_POS_CARTA")
    private Integer indicePosCarta;
    @Basic(optional = false)
    @Column(name = "NUMERO_CARTA")
    private Integer numeroCarta;
    @Basic(optional = false)
    @Column(name = "PALO_CARTA")
    private String paloCarta;
    @Column(name = "BOCA_ARRIBA")
    private Short bocaArriba;
    @Basic(optional = false)
    @Version
    @Column(name = "VERSION")
    private Long version;
    @JoinColumn(name = "FK_COLUMNA_TABLERO", referencedColumnName = "ID_COLUMNA_TABLERO")
    @ManyToOne(fetch = FetchType.LAZY)
    private ColumnaTablero fkColumnaTablero;
    @JoinColumn(name = "FK_ESCALERA", referencedColumnName = "ID_ESCALERA")
    @ManyToOne(fetch = FetchType.LAZY)
    private EscaleraCompletada fkEscalera;
    @JoinColumn(name = "FK_MAZO", referencedColumnName = "ID_MAZO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Mazo fkMazo;

    public Carta() {
    }

    public Carta(Long id) {
        this.id = id;
    }
/*
    public Carta(BigDecimal idCarta, BigInteger numeroCarta, String paloCarta, BigInteger version) {
        this.id = idCarta;
        this.numeroCarta = numeroCarta;
        this.paloCarta = paloCarta;
        this.version = version;
    }
*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndicePosCarta() {
        return indicePosCarta;
    }

    public void setIndicePosCarta(Integer indicePosCarta) {
        this.indicePosCarta = indicePosCarta;
    }

    public Integer getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(Integer numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getPaloCarta() {
        return paloCarta;
    }

    public void setPaloCarta(String paloCarta) {
        this.paloCarta = paloCarta;
    }

    public Short getBocaArriba() {
        return bocaArriba;
    }

    public void setBocaArriba(Short bocaArriba) {
        this.bocaArriba = bocaArriba;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ColumnaTablero getFkColumnaTablero() {
        return fkColumnaTablero;
    }

    public void setFkColumnaTablero(ColumnaTablero fkColumnaTablero) {
        this.fkColumnaTablero = fkColumnaTablero;
    }

    public EscaleraCompletada getFkEscalera() {
        return fkEscalera;
    }

    public void setFkEscalera(EscaleraCompletada fkEscalera) {
        this.fkEscalera = fkEscalera;
    }

    public Mazo getFkMazo() {
        return fkMazo;
    }

    public void setFkMazo(Mazo fkMazo) {
        this.fkMazo = fkMazo;
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
        if (!(object instanceof Carta)) {
            return false;
        }
        Carta other = (Carta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.Carta[ idCarta=" + id + " ]";
    }
    
}
