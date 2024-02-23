package com.altioracorp.backend.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.altioracorp.backend.dao.ClientDao;
import com.altioracorp.backend.dto.ResponseDto;
import com.altioracorp.backend.dto.ResponseListDto;
import com.altioracorp.backend.entities.Client;
import com.altioracorp.backend.interfaces.IClientService;

@Service
public class ClientService implements IClientService {

	@Autowired
	private ClientDao clientDao;

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	
	@Transactional(readOnly=true)
	public ResponseListDto<Client> getClientList() {
		ResponseListDto<Client> responseClientList = new ResponseListDto<>(200, null, new ArrayList<>(), 0);
		List<Client> foundClientList = null;
		try {
			foundClientList = this.clientDao.getClientList();
			responseClientList.setResponseList(foundClientList);
			return responseClientList;
		} catch (Exception e) {
			responseClientList.setCode(409);
			responseClientList.setError("Error al momento de obtener la lista de clientes");
			return responseClientList;
		} finally {
			responseClientList = null;
			foundClientList = null;
		}
	}
	
	public ResponseDto<Client> save(Client client) {
		Client clientCreated = null;
		ResponseDto<Client> responseClient = new ResponseDto<Client>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			clientCreated = this.clientDao.save(client);
			if (clientCreated != null && clientCreated.getId() != null) {
				responseClient.setResponseObject(clientCreated);
				this.transactionManager.commit(statusTransaction);
				responseClient.setCode(200);
			} else {					
				this.transactionManager.rollback(statusTransaction);
				responseClient.setCode(409);
				responseClient.setError("Error al momento de crear el cliente");
			}
			return responseClient;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responseClient.setCode(409);
			responseClient.setError("Error al momento de crear el cliente");
			return responseClient;
		} finally {
			clientCreated = null;
			responseClient = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}
	
	public ResponseDto<Client> update(Client client) {
		Client clientUpdated = null;
		ResponseDto<Client> responseClient = new ResponseDto<Client>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			clientUpdated = this.clientDao.update(client);
			if (clientUpdated != null && clientUpdated.getId() != null) {
				responseClient.setResponseObject(clientUpdated);
				this.transactionManager.commit(statusTransaction);
				responseClient.setCode(200);	
			} else {
				this.transactionManager.rollback(statusTransaction);
				responseClient.setCode(409);
				responseClient.setError("Error al momento de crear el cliente");
			}
			return responseClient;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responseClient.setCode(409);
			responseClient.setError("Error al momento de crear el cliente");
			return responseClient;
		} finally {
			clientUpdated = null;
			responseClient = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}

	
	public ResponseDto<Client> delete(Long clientId) {
		Client clientDeleted = null;
		Boolean isDeleted = false;
		ResponseDto<Client> responseClient = new ResponseDto<Client>(200, null, null);
		
		DefaultTransactionDefinition definirTransaccion = new DefaultTransactionDefinition();
		definirTransaccion.setReadOnly(Boolean.FALSE);
		definirTransaccion.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
		TransactionStatus statusTransaction = this.transactionManager.getTransaction(definirTransaccion);
		
		try {
			clientDeleted = this.clientDao.findById(clientId);
			isDeleted = this.clientDao.delete(clientDeleted);
			if (isDeleted) {
				this.transactionManager.commit(statusTransaction);
				responseClient.setCode(200);	
			} else {
				this.transactionManager.rollback(statusTransaction);
				responseClient.setCode(409);
				responseClient.setError("Error al momento de eliminar el cliente");
			}
			return responseClient;
		} catch (Exception e) {
			this.transactionManager.rollback(statusTransaction);
			responseClient.setCode(409);
			responseClient.setError("Error al momento de eliminar el cliente");
			return responseClient;
		} finally {
			clientDeleted = null;
			isDeleted = null;
			responseClient = null;
			definirTransaccion = null;
			statusTransaction = null;
		}
	}
}
