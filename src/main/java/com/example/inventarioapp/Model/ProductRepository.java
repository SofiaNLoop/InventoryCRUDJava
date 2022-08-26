package com.example.inventarioapp.Model;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("SELECT nombre FROM productos WHERE precio = (SELECT MAX(precio) FROM productos)")
    public String maxPrice();
    
    @Query("SELECT nombre FROM productos WHERE precio = (SELECT MIN(precio) FROM productos)")
    public String minPrice();
    
    @Query("SELECT AVG(precio) FROM productos")
    public double avgPrice();
    
}


