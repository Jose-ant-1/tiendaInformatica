package org.iesbelen.tiendainformatica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="fabricantes")
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fabricante")
    private Integer idFabricante;

    @NotEmpty // Mejor que @NotNull para Strings
    @Column(name = "nombre", nullable = false, unique = true) // El nombre del fabricante debería ser único
    private String nombre;

    @OneToMany(
            mappedBy = "fabricante",
            cascade = CascadeType.ALL, // Para que las operaciones (persist, remove) se propaguen a los productos
            orphanRemoval = true,      // Para eliminar productos que se queden "huérfanos"
            fetch = FetchType.LAZY     // Sugerencia: Usar LAZY para mejorar el rendimiento
    )
    @JsonIgnoreProperties("fabricante") // Evita la recursión infinita al serializar a JSON
    private Set<Producto> productos = new HashSet<>(); // No es necesario inicializar con 0


    public Fabricante() {

    }

    public Fabricante(String nombre) {
        this.nombre = nombre;
    }


    public Integer getIdFabricante() {
        return this.idFabricante;
    }

    public void setIdFabricante(Integer idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }


    public void addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setFabricante(this);
    }

    public void removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setFabricante(null);
    }

    @Override
    public String toString() {
        return "Fabricante [idFabricante=" + idFabricante + ", nombre=" + nombre + "]";
    }
}