/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model;

import cr.ac.una.proyectoprogra2.model.dtos.UsuarioDto;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "USUARIO", schema = "UNA")
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
    @SequenceGenerator(name = "USUARIO_ID_GENERATOR", sequenceName = "una.USUARIO_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private Long id;
    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Column(name = "MODO_OSCURO")
    private String modoOscuro;
    @Column(name = "IMAGEN_CARTA")
    private String imagenCarta;
    @Column(name = "CARA_CARTA_IMG")
    private String caraCartaImg;
    @Basic(optional = false)
    @Column(name = "PUNTAJE_TOTAL")
    private Integer puntajeTotal;
    @Basic(optional = false)
    @Column(name = "PARTIDAS_PERDIDAS")
    private Integer partidasPerdidas;
    @Basic(optional = false)
    @Column(name = "PARTIDAS_GANADAS")
    private Integer partidasGanadas;
    @Basic(optional = false)
    @Version
    @Column(name = "VERSION")
    private Long version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkUsuario", fetch = FetchType.LAZY)
    private List<Juego> juegos;

    public Usuario() {
    }

    public Usuario(UsuarioDto usuarioDto) {
        this.id = usuarioDto.getId();
        actualizar(usuarioDto);
    }

    public void actualizar(UsuarioDto usuarioDto) {
        this.nombreUsuario = usuarioDto.getNombreUsuario();
        this.imagenCarta = usuarioDto.getImagenCarta();
        this.caraCartaImg = usuarioDto.getCaraCartaImg();
        this.puntajeTotal = usuarioDto.getPuntajeTotal();
        this.partidasPerdidas = usuarioDto.getPartidasPerdidas();
        this.partidasGanadas = usuarioDto.getPartidasGanadas();
        this.version = usuarioDto.getVersion();
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Integer getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(Integer puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public Integer getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public void setPartidasPerdidas(Integer partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    public Integer getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(Integer partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public String getModoOscuro() {
        return modoOscuro;
    }

    public void setModoOscuro(String modoOscuro) {
        this.modoOscuro = modoOscuro;
    }

    public String getImagenCarta() {
        return imagenCarta;
    }

    public void setImagenCarta(String imagenCarta) {
        this.imagenCarta = imagenCarta;
    }

    public String getCaraCartaImg() {
        return caraCartaImg;
    }

    public void setCaraCartaImg(String caraCartaImg) {
        this.caraCartaImg = caraCartaImg;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Juego> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<Juego> juegos) {
        this.juegos = juegos;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyectoprogra2.model.Usuario[ idUsuario=" + id + " ]";
    }

}
