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
    private ObjectProperty<String> imagenCarta;
    private ObjectProperty<String> caraCartaImg;
    private IntegerProperty puntajeTotal;
    private IntegerProperty partidasPerdidas;
    private IntegerProperty partidasGanadas;
    //private ObjectProperty<String> modoOscuro;
    private Long version;
    private Boolean modificado;

    /**
     * Constructor por defecto, inicializa propiedades con valores por defecto.
     */
    public UsuarioDto() {
        this.id = new SimpleStringProperty("");
        this.nombreUsuario = new SimpleStringProperty("");
        this.imagenCarta = new SimpleObjectProperty("");
        this.caraCartaImg = new SimpleObjectProperty("");
        this.puntajeTotal = new SimpleIntegerProperty(0);
        this.partidasPerdidas = new SimpleIntegerProperty(0);
        this.partidasGanadas = new SimpleIntegerProperty(0);
        //this.modoOscuro = new SimpleObjectProperty("");
        this.modificado = false;
    }

    /**
     * Constructor que mapea desde la entidad Usuario
     */
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
      //  if (usuario.getModoOscuro() != null) {
       //     this.modoOscuro.set(usuario.getModoOscuro());
      //  }
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

    public String getImagenCarta() {
        return imagenCarta.get();
    }

    public void setImagenCarta(String imagenCarta) {
        this.imagenCarta.set(imagenCarta);
    }

    public String getCaraCartaImg() {
        return caraCartaImg.get();
    }

    public void setCaraCartaImg(String caraCartaImg) {
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

  //  public String getModoOscuro() {
  //      return modoOscuro.get();
  //  }

  //  public void setModoOscuro(String modoOscuro) {
  //      this.modoOscuro.set(modoOscuro);
  //  }

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
    public StringProperty getIdProperty() {
        return id;
    }

    public StringProperty getNombreUsuarioProperty() {
        return nombreUsuario;
    }

    public ObjectProperty getImagenCartaProperty() {
        return imagenCarta;
    }

    public ObjectProperty getCaraCartaImgProperty() {
        return caraCartaImg;
    }

    public IntegerProperty getPuntajeTotalProperty() {
        return puntajeTotal;
    }

    public IntegerProperty getPartidasPerdidasProperty() {
        return partidasPerdidas;
    }

    public IntegerProperty getPartidasGanadasProperty() {
        return partidasGanadas;
    }

  //  public ObjectProperty getModoOscuroProperty() {
  //      return modoOscuro;
  //  }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioDto other = (UsuarioDto) obj;
        return Objects.equals(this.id.get(), other.id.get());
    }

    @Override
    public String toString() {
        return "UsuarioDto{"
                + "id=" + id.get()
                + ", nombreUsuario=" + nombreUsuario.get()
                + '}';
    }
}
