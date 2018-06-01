package main;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Artista extends User {

	private ArrayList<Album> albuns = new ArrayList<>();
	private ArrayList<Musica> musicas = new ArrayList<>();
	
	private DefaultTableModel musicasModel, musicasAlbumModel;
	
	private JComboBox<String> albunsBox;
	
	public Artista(String nome, String senha) {
		
		super(nome, senha, 1);
		
		//Inicia o combo box de álbuns
		albunsBox = new JComboBox<>();
		((JLabel) albunsBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		//Inicia o modelo da tabela de músicas
		musicasModel = new DefaultTableModel();
		
		musicasModel.addColumn("Título");
		musicasModel.addColumn("Álbum");
		musicasModel.addColumn("Duração");
		
		//Inicia o modelo da tabela de álbuns
		musicasAlbumModel = new DefaultTableModel();
		musicasAlbumModel.addColumn("Título");
		musicasAlbumModel.addColumn("Duração");
	}
	
	public ArrayList<Musica> getMusicas() {
		
		return this.musicas;
	}
	
	public ArrayList<Album> getAlbuns() {
		
		return this.albuns;
	}
	
	public void addMusica(Musica musica, String tituloAlbum) {
		
		getAlbum(tituloAlbum).addMusica(musica);
		musicas.add(musica);
		
		atualizarMusicas();
		atualizarMusicasAlbum(tituloAlbum);
	}
	
	public Musica getMusica(String titulo) {
		
		for (Musica musica : musicas) {
			
			if (musica.getNome().equals(titulo)) {
				
				return musica;
			}
		}
		
		return null;
	}
	
 	public void remMusica(int index) {
		
		Musica musica = musicas.get(index);
		
		musicas.remove(musica);
		
		for (Album album : albuns) {
			
			album.remMusica(musica);
		}
		
		atualizarMusicas();
		
		Manager.remMusica(musica);
	}
	
	public void newAlbum(String titulo) {
		
		albuns.add(new Album(titulo));
		
		atualizarAlbunsBox();
	}
	
	public void removeAlbum(String titulo) {
		
		Album album = getAlbum(titulo);
		
		for(Musica musica : album.getMusicas()) {
			
			musicas.remove(musica);
			Manager.remMusica(musica);
		}
		
		albuns.remove(album);
		
		atualizarAlbunsBox();
		atualizarMusicas();
		atualizarMusicasAlbum(titulo);
	}
	
	public Album getAlbum(String titulo) {
		
		for (Album album : albuns) {
			
			if (album.getTitulo().equals(titulo)) {
				
				return album;
			}
		}
		
		return null;
	}
	
	public JComboBox<String> getAlbunsBox() {
		
		atualizarAlbunsBox();
		
		return albunsBox;
	}
	
	private void atualizarAlbunsBox() {
		
		albunsBox.removeAllItems();
		
		for (Album album : albuns) {
			
			albunsBox.addItem(album.getTitulo());
		}
		
		albunsBox.setSelectedIndex(albuns.size() - 1);
		
		atualizarMusicasAlbum((String) albunsBox.getSelectedItem());
	}
	
 	public DefaultTableModel getModeloMusicas() {
		
		return this.musicasModel;
	}
	
	public DefaultTableModel getModeloMusicasAlbum() {
		
		return this.musicasAlbumModel;
	}
	
	public JScrollPane getMusicasScroll() {
				
		JTable tabela = new JTable() {
			
			//Essa parte do código arruma as colunas da tabela para as informações ficarem centralizadas
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			{
				render.setHorizontalAlignment(SwingConstants.CENTER);
			}
			
			@Override
			public TableCellRenderer getCellRenderer(int arg0, int arg1) {
				return render;
			}
		};
		
		tabela.setModel(musicasModel);
		tabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
	
	public JScrollPane getAlbumMusicasScroll(String nomeAlbum) {
		
		atualizarMusicasAlbum(nomeAlbum);
		
		JTable tabela = new JTable() {
			
			//Essa parte do código arruma as colunas da tabela para as informações ficarem centralizadas
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			{
				render.setHorizontalAlignment(SwingConstants.CENTER);
			}
			
			@Override
			public TableCellRenderer getCellRenderer(int arg0, int arg1) {
				return render;
			}
		};
		
		tabela.setModel(musicasAlbumModel);
		tabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
		
	public void atualizarMusicas() {
		
		musicasModel.setRowCount(0);
		
		for (Musica musica : musicas) {
			
			musicasModel.addRow(new Object[] {musica.getNome(), musica.getAlbum(), musica.getDuracao()});
		}
	}
	
	public void atualizarMusicasAlbum(String nomeAlbum) {
		
		musicasAlbumModel.setRowCount(0);
		
		for (Album album : albuns) {
			
			if (album.getTitulo().equals(nomeAlbum)) {
				
				for (Musica musica : album.getMusicas()) {
					
					musicasAlbumModel.addRow(new Object[] {musica.getNome(), musica.getDuracao()});
				}
			}
		}
	}
}