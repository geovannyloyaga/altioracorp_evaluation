package com.altioracorp.backend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.altioracorp.backend.entities.Client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ClientDao {

    @PersistenceContext
    private EntityManager entityManager;
	
	public List<Client> getClientList() {
        List<Client> clients = null;
		try {
	        clients = entityManager.createQuery("FROM Client", Client.class).getResultList();
	        return clients;
		} catch (Exception e) {
			return clients;
		}  finally {
			clients = null;
		}
	}
	
	public Client findById(Long id) {
		Client clientFound = null;
        try {
			clientFound = entityManager.find(Client.class, id);
			return clientFound;
        } catch (Exception e) {
			return clientFound;
		} finally {
			clientFound = null;
		}
    }

    public Client save(Client client) {
		Client clientCreated = null;
        try {
        	entityManager.persist(client);
        	entityManager.flush();
			clientCreated = client;
			return clientCreated;
        } catch (Exception e) {
			return clientCreated;
		} finally {
			clientCreated = null;
		}
    }

    public Client update(Client client) {
		Client clientUpdated = null;
        try {
        	entityManager.merge(client);
        	entityManager.flush();
			clientUpdated = client;
			return clientUpdated;
        } catch (Exception e) {
			return clientUpdated;
		} finally {
			clientUpdated = null;
		}
    }

    public boolean delete(Client client) {
    	boolean clientDeleted = false;
        try {
        	entityManager.remove(client);
        	entityManager.flush();
        	clientDeleted = true;
			return clientDeleted;
        } catch (Exception e) {
			return clientDeleted;
		}
    }
}
