package main;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Manager {
	
	public static ArrayList<Musica> musicas = new ArrayList<>();
	private static ArrayList<Musica> musicasExibidas = new ArrayList<>();
	private static ArrayList<User> users = new ArrayList<>();
	public static DefaultTableModel tbTdMusicas, tbMusicasPlaylist, tbTdMusicasResumidas;
	
	private static JComboBox<String> playlistBox;
	private static String playlistAtual;
	
	public static int selecMusica;
	
	public static User getUser(String nome, String senha) {
		
		for (User user : users) {
			
			if ((user.getNome().equals(nome)) && (user.getSenha().equals(senha))) {
				
				return user;
			}
		}
		
		return null;
	}
	
	public static void addUser(String nome, String senha, int level) {
		
		users.add(level == 0 ? new User(nome, senha, 0) : new Artista(nome, senha));
	}
	
	public static Musica getMusica(int index) {
		
		return musicasExibidas.get(index);
	}
	
	public static void addMusica(String nome, String genero, String artista, String album, int min, int sec) {
		
		Musica musica = new Musica(nome, genero, artista, album, min, sec);
		
		musicas.add(musica);
		
		//Procura o album em que a música está
		for (Album albumA : ((Artista) Acao.user).getAlbuns()) {
			
			String titulo = albumA.getTitulo();
			
			if (titulo.equals(album)) {
				
				albumA.addMusica(musica);
			}
		}
		
		atualizarTbTodasMusicas();
	}
	
	public static Album getAlbum(int index) {
		
		return ((Artista) Acao.user).getAlbuns().get(index);
	}
	
	public static JComboBox<String> getAlbumBox() {
		
		JComboBox<String> combo = new JComboBox<>();
		((JLabel) combo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		combo.addItem("Álbum");
		
		for (Album album : ((Artista) Acao.user).getAlbuns()) {
			
			combo.addItem(album.getTitulo());
		}
		
		return combo;
	}
	
	public static JComboBox<String> getPlaylistBox() {
		
		if (playlistBox == null) {
			
			playlistBox = new JComboBox<>();
			((JLabel) playlistBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		playlistBox.removeAllItems();	
		
		for (Playlist playlist : Acao.user.getPlaylists()) {
			
			playlistBox.addItem(playlist.getNome());
		}
		
		return playlistBox;
	}
	
	public static void newAlbum(String titulo) {
		
		((Artista) Acao.user).getAlbuns().add(new Album(titulo));
	}	
	
	public static Playlist getPlaylist(String titulo) {
		
		for (Playlist playlist : Acao.user.getPlaylists()) {
			
			if (playlist.getNome().equals(titulo)) {
				
				return playlist;
			}
		}
		
		return null;
	}
	
	public static void newPlaylist(String titulo) {
		
		Acao.user.getPlaylists().add(new Playlist(titulo));
		
		atualizarPlaylistBox();
	}
	
	public static void removePlaylist(String titulo) {
		
		for (int i = 0; i < Acao.user.getPlaylists().size(); i++) {
			
			Playlist playlist = Acao.user.getPlaylists().get(i);
			
			if (playlist.getNome().equals(titulo)) {
				
				Acao.user.getPlaylists().remove(playlist);
			}
		}
		
		atualizarPlaylistBox();
	}
	
	private static void atualizarPlaylistBox() {
		
		playlistBox.removeAllItems();
		
		for (Playlist playlist : Acao.user.getPlaylists()) {
			
			playlistBox.addItem(playlist.getNome());
		}
		
		playlistBox.setSelectedIndex(Acao.user.getPlaylists().size() - 1);
		
		atualizarTbMusicasPlaylist((String) playlistBox.getSelectedItem());
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
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
	
	public static JScrollPane getTbTodasMusicasResumida() {
		
		if (tbTdMusicasResumidas == null) {
			
			tbTdMusicasResumidas = new DefaultTableModel();
			
			tbTdMusicasResumidas.addColumn("Título");
			tbTdMusicasResumidas.addColumn("Artista");
			
		}
		
		atualizarTbTodasMusicasResumidas();
		
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
		
		tabela.setModel(tbTdMusicasResumidas);
		tabela.setDefaultEditor(Object.class, null);
		tabela.getTableHeader().setBackground(Color.WHITE);
		
		tabela.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}			
			@Override
			public void mousePressed(MouseEvent e) {}		
			@Override
			public void mouseExited(MouseEvent e) {}			
			@Override
			public void mouseEntered(MouseEvent e) {}			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() >= 2) {
					
					getPlaylist(playlistAtual).addMusica(getMusica(tabela.getSelectedRow()));
				}
			}
		});
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
	
	private static void atualizarTbTodasMusicas() {
		
		tbTdMusicas.setRowCount(0);
		musicasExibidas.clear();
		
		for (Musica musica : musicas) {
			
			musicasExibidas.add(musica);
			tbTdMusicas.addRow(musica.getInfo());
		}
	}

	private static void atualizarTbTodasMusicasResumidas() {
		
		tbTdMusicasResumidas.setRowCount(0);
		musicasExibidas.clear();
		
		for (Musica musica : musicas) {
			
			musicasExibidas.add(musica);
			tbTdMusicasResumidas.addRow(new Object[] {musica.getNome(), musica.getArtista()});
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
		
		playlistAtual = playlist;
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
		
		tabela.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}	
			@Override
			public void mousePressed(MouseEvent e) {
				
				selecMusica = tabela.getSelectedRow();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}		
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
	
	public static void atualizarTbMusicasPlaylist(String playlist) {
		
		tbMusicasPlaylist.setRowCount(0);
		musicasExibidas.clear();
		
		for (Playlist plist : Acao.user.getPlaylists()) {
			
			if (plist.getNome().equals(playlist)) {
		
				playlistAtual = playlist;
				
				for (Musica musica : plist.getMusicas()) {
					
					musicasExibidas.add(musica);
					tbMusicasPlaylist.addRow(musica.getInfo());
				}
				
				break;
			}
		}
	}
	
	public static void filtrarTb(String filtro, DefaultTableModel tabela, ArrayList<Musica> musicas) {
		
		tabela.setRowCount(0);
		musicasExibidas.clear();
		
		if (filtro.equals("")) {
			
			atualizarTbTodasMusicas();
			atualizarTbTodasMusicasResumidas();
			atualizarTbMusicasPlaylist(playlistAtual);
		} else {
			
			if (filtro.matches("\\d*(:\\d{0,2})?")) {
				
				for (Musica musica : musicas) {
					
					if (musica.getDuracao().contains(filtro)) {
						
						tabela.addRow(musica.getInfo());
					}
				}
			} else {
				
				for (Musica musica : musicas) {
					
					if ((musica.getAlbum().toUpperCase().startsWith(filtro.toUpperCase())) ||
						(musica.getNome().toUpperCase().startsWith(filtro.toUpperCase())) || 
						(musica.getArtista().toUpperCase().startsWith(filtro.toUpperCase())) || 
						(musica.getGenero().toUpperCase().startsWith(filtro.toUpperCase()))) {
						
						musicasExibidas.add(musica);
						
						tabela.addRow(musica.getInfo());
					}
				}
			}
		}
	}
}