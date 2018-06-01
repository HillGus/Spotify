package main;

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
	
	//M�todo utilizado para iniciar as tabelas do sistema
	public static void iniciarTabelas() {
		
		//Cria tabela que guarda todas as m�sicas
		tbTdMusicas = new DefaultTableModel();
		
		//Adicionando colunas
		tbTdMusicas.addColumn("T�tulo");
		tbTdMusicas.addColumn("G�nero");
		tbTdMusicas.addColumn("Artista");
		tbTdMusicas.addColumn("�lbum");
		tbTdMusicas.addColumn("Dura��o");
		
		//Cria tabela que guarda todas as m�sicas mas com informa��es a menos
		tbTdMusicasResumidas = new DefaultTableModel();
		
		tbTdMusicasResumidas.addColumn("T�tulo");
		tbTdMusicasResumidas.addColumn("Artista");
		
		//Cria tabela que guarda todas as m�sicas de uma certa playlist
		tbMusicasPlaylist = new DefaultTableModel();
		
		//Adicionando colunas
		tbMusicasPlaylist.addColumn("T�tulo");
		tbMusicasPlaylist.addColumn("G�nero");
		tbMusicasPlaylist.addColumn("Artista");
		tbMusicasPlaylist.addColumn("�lbum");
		tbMusicasPlaylist.addColumn("Dura��o");
	}
	
	
 	public static User getUser(String nome, String senha) {
		
		for (User user : users) {
			
			//Verifica existe um usu�rio com os dados enviados
			if ((user.getNome().equals(nome)) && (user.getSenha().equals(senha))) {
				
				return user;
			}
		}
		
		return null;
	}
	
	public static void addUser(String nome, String senha, int level) {
		
		//Adiciona um usu�rio normal caso o n�vel seja 0, caso o n�vel seja 1 adiciona um artista
		users.add(level == 0 ? new User(nome, senha, 0) : new Artista(nome, senha));
	}
	
	public static Musica getMusica(int index) {
		
		return musicasExibidas.get(index);
	}
	
	public static void addMusica(String nome, String genero, String artista, String album, int min, int sec) {
		
		//Instancia nova m�sica
		Musica musica = new Musica(nome, genero, artista, album, min, sec);
		
		//Adiciona a m�sica � lista de m�sicas
		musicas.add(musica);
		
		//Adiciona a m�sica ao �lbum do artista
		((Artista) Acao.user).addMusica(musica, album);
		
		//Atualiza a tabela de m�sicas
		atualizarTbTodasMusicas();
	}

	public static void remMusica(Musica musica) {
		
		//Remove a m�sica da lista de m�sicas
		musicas.remove(musica);
		
		//Remove a m�sica de todas as playlists dos usu�rios
		for (User user : users) {
			
			for (Playlist playlist : user.getPlaylists()) {
				
				playlist.remMusica(musica);
			}
		}
	}
	
	public static JComboBox<String> getAlbumBox() {
		
		//Cria o combo box
		JComboBox<String> combo = new JComboBox<>();
		
		//Centraliza o texto dentro do combo
		((JLabel) combo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		combo.addItem("�lbum");
		
		//Adiciona os t�tulos dos �lbuns do artista
		for (Album album : ((Artista) Acao.user).getAlbuns()) {
			
			combo.addItem(album.getTitulo());
		}
		
		//Retorna o combo box
		return combo;
	}
	
	public static JComboBox<String> getPlaylistBox() {
		
		//Verifica se o combo box j� foi instanciado
		if (playlistBox == null) {
			
			//Instancia o combo box
			playlistBox = new JComboBox<>();
			
			//Centraliza o texto dentro do combo
			((JLabel) playlistBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		//Atualiza o combo com as playlists do usu�rio
		atualizarPlaylistBox();
		
		//Retorna o combo
		return playlistBox;
	}
	
	public static Playlist getPlaylist(String titulo) {
		
		//Retorna a playlist com o t�tulo escolhido
		for (Playlist playlist : Acao.user.getPlaylists()) {
			
			if (playlist.getNome().equals(titulo)) {
				
				return playlist;
			}
		}
		
		return null;
	}
	
	public static void newPlaylist(String titulo) {
		
		//Adiciona uma nova playlist com o t�tulo enviado � lista de playlists
		Acao.user.getPlaylists().add(new Playlist(titulo));
		
		//Atualiza o combo box de playlists
		atualizarPlaylistBox();
	}
	
	public static void removePlaylist(String titulo) {	
		
		//Remove a playlist com o t�tulo enviado da lista de playlists do usu�rio
		Acao.user.getPlaylists().remove(Acao.user.getPlaylist(titulo));
		
		//Atualiza o combo box de playlists
		atualizarPlaylistBox();
	}
	
	private static void atualizarPlaylistBox() {
		
		//Remove os itens do combo
		playlistBox.removeAllItems();
		
		//Adiciona os t�tulos das playlists do usu�rio
		for (Playlist playlist : Acao.user.getPlaylists()) {
			
			playlistBox.addItem(playlist.getNome());
		}
		
		//Seleciona a �ltima playlist
		playlistBox.setSelectedIndex(Acao.user.getPlaylists().size() - 1);
		
		//Atualiza a tabela de m�sicas da playlist de acordo com a playlist selecionada
		atualizarTbMusicasPlaylist((String) playlistBox.getSelectedItem());
	}
	
	public static JScrollPane getTbTodasMusicas() {

		//Atualiza a tabela de m�sicas
		atualizarTbTodasMusicas();
		
		//Cria JTable
		JTable tabela = new JTable() {
			
			//Centraliza as informa��es das colunas
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			{
				render.setHorizontalAlignment(SwingConstants.CENTER);
			}
			
			@Override
			public TableCellRenderer getCellRenderer(int arg0, int arg1) {
				return render;
			}
		};
		
		//Configura a tabela
		tabela.setModel(tbTdMusicas);
		tabela.setDefaultEditor(Object.class, null);
		
		//Cria um JScrollPane com a tabela
		JScrollPane scroll = new JScrollPane(tabela);

		//Retorna o scroll
		return scroll;
	}
	
	public static JScrollPane getTbTodasMusicasResumida() {
		
		//Atualiza a tabela de m�sicas resumidas
		atualizarTbTodasMusicasResumidas();
		
		//Cria JTable
		JTable tabela = new JTable() {
			
			//Centralizaas informa��es das colunas
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			{
				render.setHorizontalAlignment(SwingConstants.CENTER);
			}
			
			@Override
			public TableCellRenderer getCellRenderer(int arg0, int arg1) {
				return render;
			}
		};
		
		//Configura a tabela
		tabela.setModel(tbTdMusicasResumidas);
		tabela.setDefaultEditor(Object.class, null);
		
		//Adiciona um listener para quando
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
		
		playlistAtual = playlist;
		atualizarTbMusicasPlaylist(playlist);
		
		JTable tabela = new JTable() {
			
			//Essa parte do c�digo arruma as colunas da tabela para as informa��es ficarem centralizadas
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
						
						if (tabela.getColumnCount() == 2) {
							
							tabela.addRow(new Object[] {musica.getNome(), musica.getArtista()});
						} else {
							
							tabela.addRow(musica.getInfo());
						}
					}
				}
			}
		}
	}
}