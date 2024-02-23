package com.altioracorp.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altioracorp.backend.dto.ResponseDto;
import com.altioracorp.backend.dto.ResponseListDto;
import com.altioracorp.backend.entities.Client;
import com.altioracorp.backend.interfaces.IClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {
	
	@Autowired
	private IClientService clientService;
	
	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	@PostMapping("/save")
	public ResponseDto<Client> save(@RequestBody Client requestClient){
		try {
			ResponseDto<Client> clientCreated = this.clientService.save(requestClient);
			return clientCreated;
		} catch (Exception e) {
			return new ResponseDto<Client>(409, "Error al crear cliente", null);
		}
	}
	
	@PutMapping("/update")
	public ResponseDto<Client> update(@RequestBody Client requestClient){
		try {
			ResponseDto<Client> clientCreated = this.clientService.update(requestClient);
			return clientCreated;
		} catch (Exception e) {
			return new ResponseDto<Client>(409, "Error al actualizar cliente", null);
		}
	}
	
	@DeleteMapping("/delete/{clientId}")
	public ResponseDto<Client> delete(@PathVariable Long clientId){
		try {
			ResponseDto<Client> clientDeleted = this.clientService.delete(clientId);
			return clientDeleted;
		} catch (Exception e) {
			return new ResponseDto<Client>(409, "Error al eliminar cliente", null);
		}
	}

	@GetMapping("/getClientList")
	public ResponseListDto<Client> getClientList(){
		try {
			ResponseListDto<Client> clientsFound = this.clientService.getClientList();
			return clientsFound;
		} catch (Exception e) {
			return new ResponseListDto<Client>(409, "Error para obtener lista de clientes", null, 0);
		}
	}
}