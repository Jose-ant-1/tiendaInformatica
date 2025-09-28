package org.iesbelen.tiendainformatica.dao;

import jakarta.persistence.EntityManager;
import org.iesbelen.tiendainformatica.entity.Fabricante;

import java.util.List;

public class FabricanteDAOImpl implements FabricanteDAO {

    // El EntityManager es un atributo de la clase
    private EntityManager em;

    // Se inyecta a través del constructor
    public FabricanteDAOImpl(EntityManager em) {
        this.em = em;
    }

    // Los métodos de transacción ahora usan el EntityManager de la clase
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
    public List<Fabricante> findAll() {

        return em.createQuery("SELECT f FROM Fabricante f", Fabricante.class).getResultList();
    }

    @Override
    public Fabricante findOne(Long id) {

        return em.find(Fabricante.class, id);
    }

    @Override
    public boolean create(Fabricante fabricante) {

        try {
            em.persist(fabricante);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Fabricante fabricante) {
        // Implementación correcta de update
        try {
            em.merge(fabricante);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        // Implementación correcta de delete
        try {
            Fabricante fabricante = findOne(id);
            if (fabricante != null) {
                em.remove(fabricante);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
