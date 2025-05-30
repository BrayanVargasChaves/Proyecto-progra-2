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
import jakarta.persistence.Lob;
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
@Table(name = "USUARIO")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Usuario.findByPuntajeTotal", query = "SELECT u FROM Usuario u WHERE u.puntajeTotal = :puntajeTotal"),
    @NamedQuery(name = "Usuario.findByPartidasPerdidas", query = "SELECT u FROM Usuario u WHERE u.partidasPerdidas = :partidasPerdidas"),
    @NamedQuery(name = "Usuario.findByPartidasGanadas", query = "SELECT u FROM Usuario u WHERE u.partidasGanadas = :partidasGanadas"),
    @NamedQuery(name = "Usuario.findByModoOscuro", query = "SELECT u FROM Usuario u WHERE u.modoOscuro = :modoOscuro"),
    @NamedQuery(name = "Usuario.findByVersion", query = "SELECT u FROM Usuario u WHERE u.version = :version")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private BigDecimal idUsuario;
    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Lob
    @Column(name = "IMAGEN_CARTA")
    private String imagenCarta;
    @Lob
    @Column(name = "CARA_CARTA_IMG")
    private Serializable caraCartaImg;
    @Basic(optional = false)
    @Column(name = "PUNTAJE_TOTAL")
    private BigInteger puntajeTotal;
    @Basic(optional = false)
    @Column(name = "PARTIDAS_PERDIDAS")
    private BigInteger partidasPerdidas;
    @Basic(optional = false)
    @Column(name = "PARTIDAS_GANADAS")
    private BigInteger partidasGanadas;
    @Column(name = "MODO_OSCURO")
    private String modoOscuro;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private BigInteger version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkUsuario", fetch = FetchType.LAZY)
    private List<Juego> juegoList;

    public Usuario() {
    }

    public Usuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(BigDecimal idUsuario, String nombreUsuario, BigInteger puntajeTotal, BigInteger partidasPerdidas, BigInteger partidasGanadas, BigInteger version) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.puntajeTotal = puntajeTotal;
        this.partidasPerdidas = partidasPerdidas;
        this.partidasGanadas = partidasGanadas;
        this.version = version;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getImagenCarta() {
        return imagenCarta;
    }

    public void setImagenCarta(String imagenCarta) {
        this.imagenCarta = imagenCarta;
    }

    public Serializable getCaraCartaImg() {
        return caraCartaImg;
    }

    public void setCaraCartaImg(Serializable caraCartaImg) {
        this.caraCartaImg = caraCartaImg;
    }

    public BigInteger getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(BigInteger puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public BigInteger getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public void setPartidasPerdidas(BigInteger partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    public BigInteger getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(BigInteger partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public String getModoOscuro() {
        return modoOscuro;
    }

    public void setModoOscuro(String modoOscuro) {
        this.modoOscuro = modoOscuro;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public List<Juego> getJuegoList() {
        return juegoList;
    }

    public void setJuegoList(List<Juego> juegoList) {
        this.juegoList = juegoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
