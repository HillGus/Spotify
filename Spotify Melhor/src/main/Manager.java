package main;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Manager {
	
	private static ArrayList<Musica> musicas = new ArrayList<>();
	private static ArrayList<Album> albuns = new ArrayList<>();
	private static ArrayList<Playlist> playlists = new ArrayList<>();
	public static DefaultTableModel tbTdMusicas, tbMusicasPlaylist;
	
	public static Musica getMusica(int index) {
		
		return musicas.get(index);
	}
	
	public static void addMusica(String nome, String genero, String artista, String album, int min, int sec) {
		
		Musica musica = new Musica(nome, genero, artista, album, min, sec);
		
		musicas.add(musica);
		
		//Procura o album em que a música está
		for (Album albumA : albuns) {
			
			String titulo = albumA.getTitulo();
			
			if (titulo.equals(album)) {
				
				albumA.addMusica(musica);
			}
		}
		
		atualizarTbTodasMusicas();
	}
	
	public static Album getAlbum(int index) {
		
		return albuns.get(index);
	}
	
	public static JComboBox<String> getAlbumBox() {
		
		JComboBox<String> combo = new JComboBox<>();
		combo.addItem("Álbum");
		
		for (Album album : albuns) {
			
			combo.addItem(album.getTitulo());
		}
		
		return combo;
	}
	
	public static void newAlbum(String titulo) {
		
		albuns.add(new Album(titulo));
	}
	
	public static JScrollPane getTbTodasMusicas() {
		
		if (tbTdMusicas == null) {
			
			tbTdMusicas = new DefaultTableModel();
			
			//Adicionando colunas
			tbTdMusicas.addColumn("Título");
			tbTdMusicas.addColumn("Gênero");
			tbTdMusicas.addColumn("Artista");
			tbTdMusicas.addColumn("Álbum");
			tbTdMusicas.addColumn("Duração");
		}
		
		atualizarTbTodasMusicas();
		
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
		
		tabela.setModel(tbTdMusicas);
		tabela.setDefaultEditor(Object.class, null);
		tabela.getTableHeader().setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
	
	private static void atualizarTbTodasMusicas() {
		
		tbTdMusicas.setRowCount(0);
		
		for (Musica musica : musicas) {
			
			tbTdMusicas.addRow(musica.getInfo());
		}
	}

	public static JScrollPane getTbMusicasPlaylist(String playlist) {
		
		if (tbMusicasPlaylist == null) {
			
			tbMusicasPlaylist = new DefaultTableModel();
			
			//Adicionando colunas
			tbMusicasPlaylist.addColumn("Título");
			tbMusicasPlaylist.addColumn("Gênero");
			tbMusicasPlaylist.addColumn("Artista");
			tbMusicasPlaylist.addColumn("Álbum");
			tbMusicasPlaylist.addColumn("Duração");
		}
		
		atualizarTbMusicasPlaylist(playlist);
		
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
		
		tabela.setModel(tbMusicasPlaylist);
		tabela.setDefaultEditor(Object.class, null);
		tabela.getTableHeader().setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
	
	public static void atualizarTbMusicasPlaylist(String playlist) {
		
		tbMusicasPlaylist.setRowCount(0);
		
		for (Playlist plist : playlists) {
			
			if (plist.getNome().equals(playlist)) {
				
				for (Musica musica : plist.getMusicas()) {
					
					tbMusicasPlaylist.addRow(musica.getInfo());
				}
				
				break;
			}
		}
	}
	
	public static void filtrarTb(String filtro, DefaultTableModel tabela) {
		
		tabela.setRowCount(0);
		
		if (filtro.equals("")) {
			
			atualizarTbTodasMusicas();
		} else {
			
			if (filtro.matches("\\d*(:\\d{0,2})?")) {
				
				for (Musica musica : musicas) {
					
					if (musica.getDuracao().contains(filtro)) {
						
						tabela.addRow(musica.getInfo());
					}
				}
			} else {
				
				for (Musica musica : musicas) {
					
					if ((musica.getAlbum().toUpperCase().contains(filtro.toUpperCase())) ||
						(musica.getNome().toUpperCase().contains(filtro.toUpperCase())) || 
						(musica.getArtista().toUpperCase().contains(filtro.toUpperCase())) || 
						(musica.getGenero().toUpperCase().contains(filtro.toUpperCase()))) {
						
						tabela.addRow(musica.getInfo());
					}
				}
			}
		}
	}
}