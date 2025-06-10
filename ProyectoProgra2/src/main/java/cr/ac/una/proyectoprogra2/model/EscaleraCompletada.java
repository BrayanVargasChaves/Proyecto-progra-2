/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model;

import jakarta.persistence.Basic;
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
import java.util.List;

/**
 *
 * @author emena
 */
@Entity
@Table(name = "ESCALERA_COMPLETADA", schema = "UNA")
@NamedQueries({
    @NamedQuery(name = "EscaleraCompletada.findAll", query = "SELECT e FROM EscaleraCompletada e"),
    @NamedQuery(name = "EscaleraCompletada.findByIdEscalera", query = "SELECT e FROM EscaleraCompletada e WHERE e.idEscalera = :idEscalera"),
    @NamedQuery(name = "EscaleraCompletada.findByVersion", query = "SELECT e FROM EscaleraCompletada e WHERE e.version = :version")})
public class EscaleraCompletada implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "ESCALERA_COMPLETADA_ID_GENERATOR", sequenceName = "una.ESCALERA_COMPLETADA_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESCALERA_COMPLETADA_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ID_ESCALERA")
    private Long id;
    @Version
    @Column(name = "VERSION")
    private Long version;
    @JoinColumn(name = "FK_JUEGO", referencedColumnName = "ID_JUEGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Juego fkJuego;
    @OneToMany(mappedBy = "fkEscalera", fetch = FetchType.LAZY)
    private List<Carta> cartas;

    public EscaleraCompletada() {
    }

    public EscaleraCompletada(Long idEscalera) {
        this.id = idEscalera;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof EscaleraCompletada)) {
            return false;
        }
        EscaleraCompletada other = (EscaleraCompletada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.EscaleraCompletada[ idEscalera=" + id + " ]";
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
