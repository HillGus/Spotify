package main;

public class Musica {

	private String nome, genero, artista, album;
	private int min, sec;
	
	public Musica(String nome, String genero, String artista, String album, int min, int sec) {
		
		this.nome = nome;
		this.genero = genero;
		this.artista = artista;
		this.album = album;
		this.min = min;
		this.sec = sec;
	}
	
	public String getNome() {
		
		return this.nome;
	}
	
	public String getGenero() {
		
		return this.genero;
	}
	
	public String getArtista() {
		
		return this.artista;
	}
	
	public String getAlbum() {
		
		return this.album;
	}
	
	public String getDuracao() {
		
		return this.min + ":" + this.sec;
	}
	
	public int getMinutos() {
		
		return this.min;
	}
	
	public int getSegundos() {
		
		return this.sec;
	}
	
	public String[] getInfo() {
		
		String[] info = new String[5];
		
		info[0] = getNome();
		info[1] = getGenero();
		info[2] = getArtista();
		info[3] = getAlbum();
		info[4] = getDuracao();
		
		return info;
	}
	
}
