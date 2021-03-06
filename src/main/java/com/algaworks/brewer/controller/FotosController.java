package com.algaworks.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.dto.FotoDto;
import com.algaworks.brewer.storage.FotoStorage;
import com.algaworks.brewer.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage; 
	
	@PostMapping
	public DeferredResult<FotoDto> upload(@RequestParam("files[]") MultipartFile[] files){
		DeferredResult<FotoDto> resultado = new DeferredResult<>();

		Thread thread = new Thread(new FotoStorageRunnable(files, resultado, fotoStorage));
		thread.start();
		
		return resultado;		
	}
	
	/* para copiar a foto para o temporario*/
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotoTemporaria(@PathVariable("nome") String nomeFoto){
		return fotoStorage.recuperarFotoTemporaria(nomeFoto);
	}
	
	/* para copiar a foto do temporario para o local*/
	@GetMapping("/{nome:.*}")
	public byte[] recuperarFotoLocal(@PathVariable("nome") String nomeFoto){
		return fotoStorage.recuperarFotoLocal(nomeFoto);
	}
	
	/* para excluir a foto do temporario */
	@RequestMapping(path="/temp/{nome:.*}", method = { RequestMethod.DELETE })
	public void apagarFotoTemporaria(@PathVariable("nome") String nomeFoto){
		fotoStorage.apagaFotosTemporaria(nomeFoto);
	}	
	

}
