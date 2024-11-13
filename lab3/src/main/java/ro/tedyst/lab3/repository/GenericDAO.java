package ro.tedyst.lab3.repository;

import jakarta.ejb.Stateless;
import jakarta.enterprise.inject.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ro.tedyst.lab3.model.Client;
import ro.tedyst.lab3.model.GenericData;

import java.util.List;

@Stateless
public class GenericDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<GenericData> getAllData() {
        return entityManager.createQuery("SELECT d FROM GenericData d", GenericData.class).getResultList();
    }

    public void saveData(GenericData d) {
        entityManager.persist(d);
    }
}
