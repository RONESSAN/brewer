package com.algaworks.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public String salvarTemporariamente(MultipartFile[] files);

	public byte[] recuperarFotoTemporaria(String nome);

	void apagaFotosTemporaria(String nome);

	public void salvar(String foto);

	public byte[] recuperarFotoLocal(String foto); 
	
}
