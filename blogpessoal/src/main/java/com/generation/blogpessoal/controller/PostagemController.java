package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

//classe controller é resposável pelo crud. Toda requisição criada pelo usuário deve passar pelo controller, e este então se comunica com o model.
//endpoints é o endereço da nossa função, neste caso /postagens
//injeção de dependencia = instanciação de classe, transferência de responsabilidade.

//serve para indicar que a classe é um controller, que significa ter a acesso a get, post, put e delete
@RestController
//vai passar um endpoint, o endpoint vai ser o caminho(endereço) que vou usar para conseguir executar o método.
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	
	//indica uma injeção de dependencia, é como se ele estivesse instanciando, private PostagemRepository = new postagemRepository
	@Autowired
	private PostagemRepository postagemRepository;
	
	//ele está trazendo todos os dados da tabela postagem
	@GetMapping
	//criando uma lista para responder um objeto do tipo postagem
	//primeiro prepara a resposta e dps da a resposta
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	//SELECT * FROM tb_postagens where id = id;
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	//SELECT * FROM tb_postagens where titulo like "%titulo%";
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	//INSERT INTO tb_postagens (titulo, texto, data) VALUES ("Título", "Texto", CURRENT_TIMESTAMP());
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(postagemRepository.save(postagem));
	}
	
	//UPDATE tb_postagens SET titulo = "titulo", texto = "texto", data = CURRENT_TIMESTAMP() WHERE id = id;
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
		return postagemRepository.findById(postagem.getId())
			.map(resposta -> ResponseEntity.status(HttpStatus.OK)
					.body(postagemRepository.save(postagem)))
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	//DELETE FROM tb_postagens WHERE id = id;
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id);
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postagemRepository.deleteById(id);
	}
}
