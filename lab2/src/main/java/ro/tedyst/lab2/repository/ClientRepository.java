package ro.tedyst.lab2.repository;

import ro.tedyst.lab2.model.Client;

import java.util.List;

public interface ClientRepository {
    void save(Client client);
    Client getById(Long id);
    List<Client> getAllClients();
}
