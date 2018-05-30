package main;

import java.util.ArrayList;

public class Playlist {

	private String nome;
	private ArrayList<Musica> musicas = new ArrayList<>();
	
	public Playlist(String nome) {
		
		this.nome = nome;
	}
	
	public void addMusica(Musica musica) {
		
		musicas.add(musica);
	}
	
	public String getNome() {
		
		return this.nome;
	}
	
	public Musica getMusica(int index) {
		
		return this.musicas.get(index);
	}
	
	public ArrayList<Musica> getMusicas() {
		
		return this.musicas;
	}
	
}
