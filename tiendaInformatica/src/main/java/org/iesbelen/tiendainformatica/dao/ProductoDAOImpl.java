package org.iesbelen.tiendainformatica.dao;

import jakarta.persistence.EntityManager;
import org.iesbelen.tiendainformatica.entity.Producto;

import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    // El EntityManager es un atributo de la clase, inyectado por el constructor
    private EntityManager em;

    public ProductoDAOImpl(EntityManager em) {
        this.em = em;
    }

    // Los métodos de transacción operan sobre el EntityManager de la clase
    public void beginTransaction() {
        em.getTransaction().begin();
    }

    public void commitTransaction() {
        em.getTransaction().commit();
    }

    public void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    @Override
    public List<Producto> findAll() {
        return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    }

    @Override
    public Producto findOne(Long id) {
        return em.find(Producto.class, id);
    }

    @Override
    public boolean create(Producto producto) {

        try {
            em.persist(producto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Producto producto) {
        try {
            em.merge(producto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Producto producto = findOne(id);
            if (producto != null) {
                em.remove(producto);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}