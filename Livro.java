package aula;

import java.util.ArrayList;
import java.util.List;


public class Livro {

	private String titulo;
	private String autor;
	private String isbn;
	public Livro(String titulo,String autor,String isbn) {
		this.titulo=titulo;
		this.autor=autor;
		this.isbn=isbn;
	}
	public void emprestar() {
		System.out.println("Livro"+titulo+"emprestado.");
	}
	public String getTitulo() {return titulo;}
}
	
		
	


	

