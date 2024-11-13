package ro.tedyst.lab3.repository;

import jakarta.ejb.Stateless;
import jakarta.enterprise.inject.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ro.tedyst.lab3.model.Client;
import ro.tedyst.lab3.model.Product;

import java.util.List;

@Stateless
public class ClientDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Client> getAllClients() {
        return entityManager.createNamedQuery("Client.findAll", Client.class).getResultList();
    }

    public Client getClientById(int id) {
        return entityManager.find(Client.class, id);
    }

    public void createClient(Client client) {
        entityManager.persist(client);
    }

    public void updateClient(Client client) {
        entityManager.merge(client);
    }

    public void deleteClientById(int id) {
        entityManager.remove(entityManager.find(Client.class, id));
    }
}
