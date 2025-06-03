/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model.dtos;

import cr.ac.una.proyectoprogra2.model.Usuario;
import javafx.beans.property.*;
import java.util.Objects;

/**
 *
 * @author emena
 */
public class UsuarioDto {

    private StringProperty id;
    private StringProperty nombreUsuario;
    private ObjectProperty<byte[]> imagenCarta;
    private ObjectProperty<byte[]> caraCartaImg;
    private IntegerProperty puntajeTotal;
    private IntegerProperty partidasPerdidas;
    private IntegerProperty partidasGanadas;
    private StringProperty modoOscuro;
    private Long version;
    private Boolean modificado;

    /** Constructor por defecto, inicializa propiedades con valores por defecto. */
    public UsuarioDto() {
        this.id = new SimpleStringProperty("");
        this.nombreUsuario = new SimpleStringProperty("");
        this.imagenCarta = new SimpleObjectProperty<>(new byte[0]);
        this.caraCartaImg = new SimpleObjectProperty<>(new byte[0]);
        this.puntajeTotal = new SimpleIntegerProperty(0);
        this.partidasPerdidas = new SimpleIntegerProperty(0);
        this.partidasGanadas = new SimpleIntegerProperty(0);
        this.modoOscuro = new SimpleStringProperty("");
        this.modificado = false;
    }

    /** Constructor que mapea desde la entidad Usuario */
    public UsuarioDto(Usuario usuario) {
        this();
        if (usuario.getId() != null) {
            this.id.set(usuario.getId().toString());
        }
        if (usuario.getNombreUsuario() != null) {
            this.nombreUsuario.set(usuario.getNombreUsuario());
        }
        if (usuario.getImagenCarta() != null) {
            this.imagenCarta.set(usuario.getImagenCarta());
        }
        if (usuario.getCaraCartaImg() != null) {
            this.caraCartaImg.set(usuario.getCaraCartaImg());
        }
        if (usuario.getPuntajeTotal() != null) {
            this.puntajeTotal.set(usuario.getPuntajeTotal());
        }
        if (usuario.getPartidasPerdidas() != null) {
            this.partidasPerdidas.set(usuario.getPartidasPerdidas());
        }
        if (usuario.getPartidasGanadas() != null) {
            this.partidasGanadas.set(usuario.getPartidasGanadas());
        }
        if (usuario.getModoOscuro() != null) {
            this.modoOscuro.set(usuario.getModoOscuro());
        }
        this.version = usuario.getVersion();
    }

    // Getters y setters de valor
    public Long getId() {
        if (id.get() != null && !id.get().isBlank()) {
            return Long.valueOf(id.get());
        }
        return null;
    }
    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getNombreUsuario() {
        return nombreUsuario.get();
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario.set(nombreUsuario);
    }

    public byte[] getImagenCarta() {
        return imagenCarta.get();
    }
    public void setImagenCarta(byte[] imagenCarta) {
        this.imagenCarta.set(imagenCarta);
    }

    public byte[] getCaraCartaImg() {
        return caraCartaImg.get();
    }
    public void setCaraCartaImg(byte[] caraCartaImg) {
        this.caraCartaImg.set(caraCartaImg);
    }

    public int getPuntajeTotal() {
        return puntajeTotal.get();
    }
    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal.set(puntajeTotal);
    }

    public int getPartidasPerdidas() {
        return partidasPerdidas.get();
    }
    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas.set(partidasPerdidas);
    }

    public int getPartidasGanadas() {
        return partidasGanadas.get();
    }
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas.set(partidasGanadas);
    }

    public String getModoOscuro() {
        return modoOscuro.get();
    }
    public void setModoOscuro(String modoOscuro) {
        this.modoOscuro.set(modoOscuro);
    }

    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getModificado() {
        return modificado;
    }
    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    // Property getters para binding
    public StringProperty idProperty() {
        return id;
    }
    public StringProperty nombreUsuarioProperty() {
        return nombreUsuario;
    }
    public ObjectProperty<byte[]> imagenCartaProperty() {
        return imagenCarta;
    }
    public ObjectProperty<byte[]> caraCartaImgProperty() {
        return caraCartaImg;
    }
    public IntegerProperty puntajeTotalProperty() {
        return puntajeTotal;
    }
    public IntegerProperty partidasPerdidasProperty() {
        return partidasPerdidas;
    }
    public IntegerProperty partidasGanadasProperty() {
        return partidasGanadas;
    }
    public StringProperty modoOscuroProperty() {
        return modoOscuro;
    }

    // equals, hashCode y toString
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UsuarioDto)) return false;
        UsuarioDto other = (UsuarioDto) obj;
        return Objects.equals(getId(), other.getId());
    }
    @Override
    public String toString() {
        return "UsuarioDto{" +
                "id=" + id.get() +
                ", nombreUsuario=" + nombreUsuario.get() +
                '}';
    }
}

