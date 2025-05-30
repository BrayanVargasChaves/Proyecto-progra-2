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
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author emena
 */
@Entity
@Table(name = "JUEGO", schema = "UNA")
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
    private Long id;
    @Basic(optional = false)
    @Column(name = "DIFICULTAD")
    private String dificultad;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate fechaHora;
    @Basic(optional = false)
    @Column(name = "PUNTAJE")
    private Integer puntaje;
    @Basic(optional = false)
    @Column(name = "TIEMPO_TRANSCURRIDO")
    private Integer tiempoTranscurrido;
    @Version
    @Basic(optional = false)
    @Column(name = "VERSION")
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkJuego", fetch = FetchType.LAZY)
    private List<ColumnaTablero> columnasTablero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkJuego", fetch = FetchType.LAZY)
    private List<EscaleraCompletada> escalerasCompletadas;
    @JoinColumn(name = "FK_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario fkUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkJuego", fetch = FetchType.LAZY)
    private List<Mazo> mazos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkJuego", fetch = FetchType.LAZY)
    private List<Movimiento> movimientos;
    public Juego() {
    }

    public Juego(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public Integer getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    public void setTiempoTranscurrido(Integer tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<ColumnaTablero> getColumnasTablero() {
        return columnasTablero;
    }

    public void setColumnasTablero(List<ColumnaTablero> columnasTablero) {
        this.columnasTablero = columnasTablero;
    }

    public List<EscaleraCompletada> getEscalerasCompletadas() {
        return escalerasCompletadas;
    }

    public void setEscalerasCompletadas(List<EscaleraCompletada> escalerasCompletadas) {
        this.escalerasCompletadas = escalerasCompletadas;
    }

    public Usuario getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Usuario fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public List<Mazo> getMazos() {
        return mazos;
    }

    public void setMazos(List<Mazo> mazos) {
        this.mazos = mazos;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
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
        if (!(object instanceof Juego)) {
            return false;
        }
        Juego other = (Juego) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.Juego[ idJuego=" + id + " ]";
    }
}
