package org.iesbelen.tiendainformatica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;


@Entity
@Table(name="productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @NotEmpty // Mejor que @NotNull para Strings, asegura que no esté vacío ""
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Positive // Asegura que el precio sea un valor positivo
    @Column(name = "precio", nullable = false)
    private double precio;

    @ManyToOne(fetch = FetchType.LAZY) // Sugerencia: Usar LAZY para mejorar el rendimiento
    @JoinColumn(name = "id_fabricante",
            foreignKey = @ForeignKey(name = "FK_PRODUCTO_FABRICANTE"))
    private Fabricante fabricante;


    public Producto() {

    }

    // El constructor con parámetros es útil para crear nuevas instancias
    public Producto(Fabricante fabricante, String nombre, double precio) {
        this.fabricante = fabricante;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters (sin cambios, están correctos)
    public Integer getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Fabricante getFabricante() {
        return this.fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        // Se comprueba si el fabricante está inicializado para evitar errores.
        String nombreFabricante = (fabricante != null && Persistence.getPersistenceUtil().isLoaded(fabricante))
                ? fabricante.getNombre()
                : "[Fabricante no cargado]";
        return "Producto [idProducto=" + idProducto + ", fabricante=" + nombreFabricante + ", nombre=" + nombre + ", precio=" + precio
                + "]";
    }
}