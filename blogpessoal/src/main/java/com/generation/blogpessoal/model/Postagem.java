package com.generation.blogpessoal.model;

import java.time.LocalDateTime;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

//indica que o objeto abaixo vai ser uma tabela no Banco de Dados
@Entity 
//da o nome para a tabela no Banco de Dados a ser criada, sem ela a tabela é nomeada com o mesmo nome do objeto
@Table(name = "tb_postagens") 
public class Postagem {
	
	//indica que o id da tabela será uma chave primária
	@Id 
	//indica que a chave primária será auto increment
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	//notblank não vai aceitar dado nulo/vazio, é mais indicado para campos com texto menor, notnull difine o campo de texto como obrigatório
	@NotBlank(message = "O atributo título é Obrigatório!")
	//define o minimo e o máximo de letras que podem ser inseridos.
	@Size(min = 5, max = 100, message = "O atributo título deve conter no mínimo 05 e no máximo 100 caracteres")
	private String titulo;
	
	@NotBlank(message = "O atributo texto é Obrigatório!")
	@Size(min = 10, max = 1000, message = "O atributo título deve conter no mínimo 10 e no máximo 1000 caracteres")
	private String texto;
	
	//ele atualiza da data quando tiver uma mudança
	@UpdateTimestamp
	@JsonFormat(pattern = "dd/MM/yyyy:HH:mm:ss")
	private LocalDateTime data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	
}
