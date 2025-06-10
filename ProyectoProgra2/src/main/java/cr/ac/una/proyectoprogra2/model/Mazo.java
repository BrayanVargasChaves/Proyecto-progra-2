/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author emena
 */
@Entity
@Table(name = "MAZO", schema = "UNA")
@NamedQueries({
    @NamedQuery(name = "Mazo.findAll", query = "SELECT m FROM Mazo m"),
    @NamedQuery(name = "Mazo.findByIdMazo", query = "SELECT m FROM Mazo m WHERE m.idMazo = :idMazo"),
    @NamedQuery(name = "Mazo.findByEstado", query = "SELECT m FROM Mazo m WHERE m.estado = :estado"),
    @NamedQuery(name = "Mazo.findByVersion", query = "SELECT m FROM Mazo m WHERE m.version = :version")})
public class Mazo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "MAZO_ID_GENERATOR", sequenceName = "una.MAZO_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MAZO_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ID_MAZO")
    private Long id;
    @Column(name = "ESTADO")
    private Integer estado;
    @Version
    @Column(name = "VERSION")
    private Long version;
    @JoinColumn(name = "FK_JUEGO", referencedColumnName = "ID_JUEGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Juego fkJuego;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkMazo", fetch = FetchType.LAZY)
    private List<Carta> cartas;

    public Mazo() {
    }

    public Mazo(Long id) {
        this.id = id;
    }

    /*
    public Mazo(BigDecimal idMazo, BigInteger version) {
        this.id = idMazo;
        this.version = version;
    }
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Juego getFkJuego() {
        return fkJuego;
    }

    public void setFkJuego(Juego fkJuego) {
        this.fkJuego = fkJuego;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
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
        if (!(object instanceof Mazo)) {
            return false;
        }
        Mazo other = (Mazo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyectoprogra2.model.Mazo[ idMazo=" + id + " ]";
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
