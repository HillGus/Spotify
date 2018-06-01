package main;

import java.util.ArrayList;

public class Album {

	private ArrayList<Musica> musicas = new ArrayList<>();
	private String titulo;
	
	public Album(String titulo) {
		
		this.titulo = titulo;
	}
	
	public void setTitulo(String titulo) {
		
		this.titulo = titulo;
	}
	
	public String getTitulo() {
		
		return this.titulo;
	}
	
	public void addMusica(Musica musica) {
		
		this.musicas.add(musica);
	}
	
	public void remMusica(Musica musica) {
		
		this.musicas.remove(musica);
	}
	
	public Musica getMusica(int index) {
		
		return this.musicas.get(index);
	}
	
	public ArrayList<Musica> getMusicas() {
		
		return this.musicas;
	}
}