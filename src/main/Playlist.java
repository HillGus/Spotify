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
		Manager.atualizarTbMusicasPlaylist(nome);
	}
	
	public void remMusica(Musica musica) {
		
		//Remove todas as aparições da música nessa playlist
		for (int i = 0; i < musicas.size(); i++) {
			
			if (musicas.get(i) == musica) {
				
				musicas.remove(i);
				i--;
			}
		}
		Manager.atualizarTbMusicasPlaylist(nome);
	}
		
	public void remMusica(int index) {
		
		musicas.remove(index);
		Manager.atualizarTbMusicasPlaylist(nome);
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