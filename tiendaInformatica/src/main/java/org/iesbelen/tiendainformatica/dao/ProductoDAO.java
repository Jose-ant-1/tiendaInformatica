package org.iesbelen.tiendainformatica.dao;

import org.iesbelen.tiendainformatica.entity.Producto;
import java.util.List;

public interface ProductoDAO {

    List<Producto> findAll();
    Producto findOne(Long id);
    boolean create(Producto producto);
    boolean update(Producto producto);
    boolean delete(Long id);
}
