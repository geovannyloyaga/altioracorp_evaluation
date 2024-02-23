package com.altioracorp.backend.interfaces;

import com.altioracorp.backend.dto.ResponseDto;
import com.altioracorp.backend.dto.ResponseListDto;
import com.altioracorp.backend.entities.Client;

public interface IClientService {
	
	public ResponseDto<Client> save(Client client);
	
	public ResponseDto<Client> update(Client client);
	
	public ResponseDto<Client> delete(Long clientId);

	public ResponseListDto<Client> getClientList();
}
