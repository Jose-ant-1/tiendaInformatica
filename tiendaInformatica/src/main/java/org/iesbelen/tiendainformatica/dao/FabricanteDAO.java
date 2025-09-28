package org.iesbelen.tiendainformatica.dao;

import org.iesbelen.tiendainformatica.entity.Fabricante;
import java.util.List;

public interface FabricanteDAO {
    
        List<Fabricante> findAll();
        Fabricante findOne(Long id);
        boolean create(Fabricante fabricante);
        boolean update(Fabricante fabricante);
        boolean delete(Long id);
}
