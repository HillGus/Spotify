package main;

import java.util.ArrayList;

public class User {

	private ArrayList<Playlist> playlists = new ArrayList<>();
	
	private String nome, senha;
	private int level;
	
	public User(String nome, String senha, int level) {
		
		this.nome = nome;
		this.senha = senha;
		this.level = level;
	}
	
	public ArrayList<Playlist> getPlaylists() {
		
		return this.playlists;
	}
	
	public Playlist getPlaylist(String titulo) {
		
		//Retorna a playlist com título enviado
		for (Playlist playlist : playlists) {
			
			if (playlist.getNome().equals(titulo)) {
				
				return playlist;
			}
		}
		
		return null;
	}
	
	public String getNome() {
		
		return this.nome;
	}
	
	public String getSenha() {
		
		return this.senha;
	}
	
	public int getLevel() {
		
		return this.level;
	}
	
	public Object[] getInfo() {
		
		return new Object[] {getNome(), getSenha(), getLevel()};
	}
}
