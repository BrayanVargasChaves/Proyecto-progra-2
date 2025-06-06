/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra2.model.dtos;

/**
 *
 * @author emena
 */
import cr.ac.una.proyectoprogra2.model.Carta;
import javafx.beans.property.*;
import java.util.Objects;

/**
 * DTO para la entidad Carta, usando propiedades JavaFX para binding en UI.
 */
public class CartaDto {

    private StringProperty id;
    private IntegerProperty indicePosCarta;
    private IntegerProperty numeroCarta;
    private StringProperty paloCarta;
    private ObjectProperty<Short> bocaArriba;
    private Long version;
    private LongProperty fkColumnaTableroId;
    private LongProperty fkEscaleraId;
    private LongProperty fkMazoId;
    private Boolean modificado;

    /** Constructor por defecto, inicializa propiedades con valores por defecto. */
    public CartaDto() {
        this.id = new SimpleStringProperty("");
        this.indicePosCarta = new SimpleIntegerProperty(0);
        this.numeroCarta = new SimpleIntegerProperty(0);
        this.paloCarta = new SimpleStringProperty("");
        this.bocaArriba = new SimpleObjectProperty<>((short)0);
        this.fkColumnaTableroId = new SimpleLongProperty(0);
        this.fkEscaleraId = new SimpleLongProperty(0);
        this.fkMazoId = new SimpleLongProperty(0);
        this.modificado = false;
    }

    /** Constructor desde la entidad Carta */
    public CartaDto(Carta carta) {
        this();
        if (carta.getId() != null) {
            this.id.set(carta.getId().toString());
        }
        if (carta.getIndicePosCarta() != null) {
            this.indicePosCarta.set(carta.getIndicePosCarta());
        }
        this.numeroCarta.set(carta.getNumeroCarta());
        this.paloCarta.set(carta.getPaloCarta());
        if (carta.getBocaArriba() != null) {
            this.bocaArriba.set(carta.getBocaArriba());
        }
        this.version = carta.getVersion();
        if (carta.getFkColumnaTablero() != null && carta.getFkColumnaTablero().getId() != null) {
            this.fkColumnaTableroId.set(carta.getFkColumnaTablero().getId());
        }
        if (carta.getFkEscalera() != null && carta.getFkEscalera().getId() != null) {
            this.fkEscaleraId.set(carta.getFkEscalera().getId());
        }
        if (carta.getFkMazo() != null && carta.getFkMazo().getId() != null) {
            this.fkMazoId.set(carta.getFkMazo().getId());
        }
        this.modificado = false;
    }

    // Getters y setters

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public int getIndicePosCarta() {
        return indicePosCarta.get();
    }

    public void setIndicePosCarta(int indicePosCarta) {
        this.indicePosCarta.set(indicePosCarta);
    }

    public int getNumeroCarta() {
        return numeroCarta.get();
    }

    public void setNumeroCarta(int numeroCarta) {
        this.numeroCarta.set(numeroCarta);
    }

    public String getPaloCarta() {
        return paloCarta.get();
    }

    public void setPaloCarta(String paloCarta) {
        this.paloCarta.set(paloCarta);
    }

    public Short getBocaArriba() {
        return bocaArriba.get();
    }

    public void setBocaArriba(Short bocaArriba) {
        this.bocaArriba.set(bocaArriba);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getFkColumnaTableroId() {
        return fkColumnaTableroId.get();
    }

    public void setFkColumnaTableroId(Long id) {
        this.fkColumnaTableroId.set(id);
    }

    public Long getFkEscaleraId() {
        return fkEscaleraId.get();
    }

    public void setFkEscaleraId(Long id) {
        this.fkEscaleraId.set(id);
    }

    public Long getFkMazoId() {
        return fkMazoId.get();
    }

    public void setFkMazoId(Long id) {
        this.fkMazoId.set(id);
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

    public IntegerProperty indicePosCartaProperty() {
        return indicePosCarta;
    }

    public IntegerProperty numeroCartaProperty() {
        return numeroCarta;
    }

    public StringProperty paloCartaProperty() {
        return paloCarta;
    }

    public ObjectProperty<Short> bocaArribaProperty() {
        return bocaArriba;
    }

    public LongProperty fkColumnaTableroIdProperty() {
        return fkColumnaTableroId;
    }

    public LongProperty fkEscaleraIdProperty() {
        return fkEscaleraId;
    }

    public LongProperty fkMazoIdProperty() {
        return fkMazoId;
    }

    // equals, hashCode y toString

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CartaDto)) return false;
        CartaDto other = (CartaDto) obj;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public String toString() {
        return "CartaDto{" +
               "id=" + id.get() +
               ", numeroCarta=" + numeroCarta.get() +
               ", paloCarta=" + paloCarta.get() +
               '}';
    }
}

