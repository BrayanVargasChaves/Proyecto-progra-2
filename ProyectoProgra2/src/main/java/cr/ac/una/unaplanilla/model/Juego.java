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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "JUEGO")
@NamedQueries({
    @NamedQuery(name = "Juego.findAll", query = "SELECT j FROM Juego j"),
    @NamedQuery(name = "Juego.findByIdJuego", query = "SELECT j FROM Juego j WHERE j.idJuego = :idJuego"),
    @NamedQuery(name = "Juego.findByFechaHora", query = "SELECT j FROM Juego j WHERE j.fechaHora = :fechaHora"),
    @NamedQuery(name = "Juego.findByDificultad", query = "SELECT j FROM Juego j WHERE j.dificultad = :dificultad"),
    @NamedQuery(name = "Juego.findByEstado", query = "SELECT j FROM Juego j WHERE j.estado = :estado"),
    @NamedQuery(name = "Juego.findByPuntaje", query = "SELECT j FROM Juego j WHERE j.puntaje = :puntaje"),
    @NamedQuery(name = "Juego.findByTiempoTranscurrido", query = "SELECT j FROM Juego j WHERE j.tiempoTranscurrido = :tiempoTranscurrido"),
    @NamedQuery(name = "Juego.findByVersion", query = "SELECT j FROM Juego j WHERE j.version = :version")})
public class Juego implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_JUEGO")
    private BigDecimal idJuego;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Basic(optional = false)
    @Column(name = "DIFICULTAD")
    private Character dificultad;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "PUNTAJE")
    private BigInteger puntaje;
    @Basic(optional = false)
    @Column(name = "TIEMPO_TRANSCURRIDO")
    private BigInteger tiempoTranscurrido;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkJuego", fetch = FetchType.LAZY)
    private List<ColumnaTablero> columnaTableroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkJuego", fetch = FetchType.LAZY)
    private List<EscaleraCompletada> escaleraCompletadaList;
    @JoinColumn(name = "FK_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario fkUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkJuego", fetch = FetchType.LAZY)
    private List<Mazo> mazoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkJuego", fetch = FetchType.LAZY)
    private List<Movimiento> movimientoList;

    public Juego() {
    }

    public Juego(BigDecimal idJuego) {
        this.idJuego = idJuego;
    }

    public Juego(BigDecimal idJuego, Date fechaHora, Character dificultad, String estado, BigInteger puntaje, BigInteger tiempoTranscurrido, BigInteger version) {
        this.idJuego = idJuego;
        this.fechaHora = fechaHora;
        this.dificultad = dificultad;
        this.estado = estado;
        this.puntaje = puntaje;
        this.tiempoTranscurrido = tiempoTranscurrido;
        this.version = version;
    }

    public BigDecimal getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(BigDecimal idJuego) {
        this.idJuego = idJuego;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Character getDificultad() {
        return dificultad;
    }

    public void setDificultad(Character dificultad) {
        this.dificultad = dificultad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigInteger getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(BigInteger puntaje) {
        this.puntaje = puntaje;
    }

    public BigInteger getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    public void setTiempoTranscurrido(BigInteger tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public List<ColumnaTablero> getColumnaTableroList() {
        return columnaTableroList;
    }

    public void setColumnaTableroList(List<ColumnaTablero> columnaTableroList) {
        this.columnaTableroList = columnaTableroList;
    }

    public List<EscaleraCompletada> getEscaleraCompletadaList() {
        return escaleraCompletadaList;
    }

    public void setEscaleraCompletadaList(List<EscaleraCompletada> escaleraCompletadaList) {
        this.escaleraCompletadaList = escaleraCompletadaList;
    }

    public Usuario getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Usuario fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public List<Mazo> getMazoList() {
        return mazoList;
    }

    public void setMazoList(List<Mazo> mazoList) {
        this.mazoList = mazoList;
    }

    public List<Movimiento> getMovimientoList() {
        return movimientoList;
    }

    public void setMovimientoList(List<Movimiento> movimientoList) {
        this.movimientoList = movimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJuego != null ? idJuego.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Juego)) {
            return false;
        }
        Juego other = (Juego) object;
        if ((this.idJuego == null && other.idJuego != null) || (this.idJuego != null && !this.idJuego.equals(other.idJuego))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.Juego[ idJuego=" + idJuego + " ]";
    }
    
}
