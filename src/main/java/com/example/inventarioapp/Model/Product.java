 package com.example.inventarioapp.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("productos")
public class Product {

    @Id
    private Integer codigo;
    private String nombre;
    private double precio;
    private Integer inventario;

    public Product() {
    }

    public Product(String nombre, double precio, Integer inventario) {
        this.nombre = nombre;
        this.precio = precio;
        this.inventario = inventario;
    }

    
    
    public Product(Integer codigo, String nombre, double precio, Integer inventario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.inventario = inventario;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getInventario() {
        return inventario;
    }

    public void setInventario(Integer inventario) {
        this.inventario = inventario;
    }

    @Override
    public String toString() {
        return "Product{" + "codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio + ", inventario=" + inventario + '}';
    }
    
    
    
}
