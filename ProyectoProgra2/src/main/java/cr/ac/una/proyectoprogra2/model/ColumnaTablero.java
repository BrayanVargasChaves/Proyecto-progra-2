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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author emena
 */
@Entity
@Table(name = "COLUMNA_TABLERO", schema = "UNA")
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
    private Long id;
    @Column(name = "INDICE_COLUMNA")
    private Integer indiceColumna;
    @Basic(optional = false)
    @Version
    @Column(name = "VERSION")
    private Long version;
    @JoinColumn(name = "FK_JUEGO", referencedColumnName = "ID_JUEGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Juego fkJuego;
    @OneToMany(mappedBy = "fkColumnaTablero", fetch = FetchType.LAZY)
    private List<Carta> cartas;

    public ColumnaTablero() {
    }

    public ColumnaTablero(Long id) {
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
        if (!(object instanceof ColumnaTablero)) {
            return false;
        }
        ColumnaTablero other = (ColumnaTablero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.ColumnaTablero[ idColumnaTablero=" + id + " ]";
    }

}
