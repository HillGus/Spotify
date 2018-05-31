package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Acao {
	
	// ArrayList com login e senha dos usuários
	ArrayList<Object[]> usuarios = new ArrayList<>();
	private int level = 1;
		
	public static User user;
	
	public Acao() {
		
		Manager.addUser("Hill", "Bill", 1);
		Manager.addUser("Nathan", "Gay", 0);
	}
	
	public void login() {

		// Criando JFrame
		Frame frmLogin = new Frame("Login");
		frmLogin.setPadding(15);

		// Componentes
		JLabel lblNome = new JLabel("Usuário");
		lblNome.setBounds(0, 5, 50, 15);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(0, 40, 50, 15);

		JTextField edtNome = new JTextField();
		edtNome.setBounds(50, 0, 150, 25);

		JPasswordField edtSenha = new JPasswordField();
		edtSenha.setBounds(50, 35, 150, 25);

		JButton btnLogar = new JButton("Confirmar");
		btnLogar.setBounds(60, 75, 100, 25);
		btnLogar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				User userA = Manager.getUser(edtNome.getText(), String.valueOf(edtSenha.getPassword()));
				
				if (userA != null) {
					
					user = userA;
					level = userA.getLevel();
					frmLogin.dispose();
					Principal();
				}
			}
		});
		
		frmLogin.add(lblNome);
		frmLogin.add(lblSenha);
		frmLogin.add(edtNome);
		frmLogin.add(edtSenha);
		frmLogin.add(btnLogar);
		
		frmLogin.setVisible(true);
	}

	public void Principal() {
		
		Frame frmPrincipal = new Frame("Principal");
		frmPrincipal.setPadding(15);
		
		//Componentes
		JButton btnTdsMusicas = new JButton("Todas As Músicas");
		btnTdsMusicas.setBounds(0, 0, 200, 35);
		btnTdsMusicas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				TodasMusicas();				
			}
		});
		
		JButton btnPlaylists = new JButton("Playlists");
		btnPlaylists.setBounds(0, 50, 200, 35);
		btnPlaylists.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Playlists();
			}
		});
		
		frmPrincipal.add(btnTdsMusicas);
		frmPrincipal.add(btnPlaylists);
		
		if (level == 1) {
			
			JButton btnArtista = new JButton("Artista");
			btnArtista.setBounds(0, 100, 200, 35);
			btnArtista.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					Artista();
				}
			});
			
			frmPrincipal.add(btnArtista);
		}
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(0, level == 0 ? 100 : 150, 200, 35);
		btnSair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frmPrincipal.dispose();
				login();
			}
		});
		
		frmPrincipal.add(btnSair);
		
		frmPrincipal.setVisible(true);
	}
	
	private void TodasMusicas() {
		
		Frame frmMusicas = new Frame("Todas As Músicas");
		frmMusicas.setPadding(15);
		frmMusicas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextField edtPesquisa = new JTextField();
		edtPesquisa.setBounds(0, 0, 960, 25);
		edtPesquisa.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbTdMusicas, Manager.musicas);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {

				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbTdMusicas, Manager.musicas);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lblInfoPesquisa = new JLabel("?");
		lblInfoPesquisa.setBounds(985, 5, 15, 15);
		lblInfoPesquisa.setToolTipText("<html><p>Use este campo para pesquisar suas músicas por:<br/>"
									 + " Nome, Gênero, Artista, Album ou Duração</p></html>");
		
		JScrollPane scrollMusicas = Manager.getTbTodasMusicas();
		scrollMusicas.setBounds(0, 40, 1000, 250);
		
		frmMusicas.add(lblInfoPesquisa);
		frmMusicas.add(edtPesquisa);
		frmMusicas.add(scrollMusicas);
		
		frmMusicas.setVisible(true);
	}

	private void Playlists() {
		
		Frame frmPlaylists = new Frame("Playlists");
		frmPlaylists.setPadding(15);
		frmPlaylists.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblPlaylists = new JLabel("Playlists");
		lblPlaylists.setBounds(0, 5, 50, 15);
		
		JComboBox<String> cbxPlaylists = Manager.getPlaylistBox();
		cbxPlaylists.setBounds(65, 0, 350, 25);
		cbxPlaylists.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				Manager.atualizarTbMusicasPlaylist((String) cbxPlaylists.getSelectedItem());
			}
		});
		
		JButton btnNewPlaylist = new JButton("Nova Playlist");
		btnNewPlaylist.setBounds(430, 0, 150, 25);
		btnNewPlaylist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String titulo = "";
				
				while (titulo.equals("")) {
					
					titulo = JOptionPane.showInputDialog("Informe o nome da nova playlist");
				}
				
				Manager.newPlaylist(titulo);
			}
		});
		
		JButton btnRemPlaylist = new JButton("Remover Playlist");
		btnRemPlaylist.setBounds(595, 0, 150, 25);
		btnRemPlaylist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				Manager.removePlaylist((String) cbxPlaylists.getSelectedItem());
			}
		});
		
		JTextField edtPesquisa = new JTextField();
		edtPesquisa.setBounds(0, 40, 960, 25);
		edtPesquisa.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbMusicasPlaylist, 
						Manager.getPlaylist((String) cbxPlaylists.getSelectedItem()).getMusicas());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {

				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbMusicasPlaylist, 
						Manager.getPlaylist((String) cbxPlaylists.getSelectedItem()).getMusicas());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lblInfoPesquisa = new JLabel("?");
		lblInfoPesquisa.setBounds(985, 45, 15, 15);
		lblInfoPesquisa.setToolTipText("<html><p>Use este campo para pesquisar suas músicas por:<br/>"
									 + " Nome, Gênero, Artista, Album ou Duração</p></html>");
		
		JScrollPane scrollMusicas = Manager.getTbMusicasPlaylist("");
		scrollMusicas.setBounds(0, 80, 1000, 250);
		
		JButton btnAddMusica = new JButton("Adicionar Música");
		btnAddMusica.setBounds(0, 345, 150, 25);
		btnAddMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				addMusicaOnPlaylist();
			}
		});
		
		JButton btnRemMusica = new JButton("Remover Música");
		btnRemMusica.setBounds(165, 345, 150, 25);
		btnRemMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Manager.getPlaylist((String) cbxPlaylists.getSelectedItem()).remMusica(Manager.selecMusica);				
			}
		});
		
		frmPlaylists.add(btnNewPlaylist);
		frmPlaylists.add(btnRemPlaylist);
		frmPlaylists.add(btnAddMusica);
		frmPlaylists.add(btnRemMusica);
		frmPlaylists.add(edtPesquisa);
		frmPlaylists.add(lblInfoPesquisa);
		frmPlaylists.add(lblPlaylists);
		frmPlaylists.add(cbxPlaylists);
		frmPlaylists.add(scrollMusicas);
		
		frmPlaylists.setVisible(true);
	}
	
	private void addMusicaOnPlaylist() {
		
		Frame frm = new Frame("Todas As Músicas");
		frm.setPadding(15);
		frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextField edtPesquisa = new JTextField();
		edtPesquisa.setBounds(0, 0, 205, 25);
		edtPesquisa.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbTdMusicasResumidas, Manager.musicas);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {

				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbTdMusicasResumidas, Manager.musicas);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});

		JLabel lblInfoPesquisa = new JLabel("?");
		lblInfoPesquisa.setBounds(220, 5, 10, 15);
		lblInfoPesquisa.setToolTipText("<html><p>Use este campo para pesquisar suas músicas por:<br/>"
									 + " Nome, Gênero, Artista, Album ou Duração</p></html>");
		
		JScrollPane scrollMusicasResumidas = Manager.getTbTodasMusicasResumida();
		scrollMusicasResumidas.setBounds(0, 40, 240, 300);
		
		frm.add(edtPesquisa);
		frm.add(lblInfoPesquisa);
		frm.add(scrollMusicasResumidas);
		
		frm.setVisible(true);
	}
	
	private void Artista() {
		
		Frame frmArtista = new Frame("Artista");
		frmArtista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmArtista.setPadding(15);
		
		JButton btnAlbuns = new JButton("Álbuns");
		btnAlbuns.setBounds(0, 0, 200, 35);
		
		JButton btnMusicas = new JButton("Músicas");
		btnMusicas.setBounds(0, 50, 200, 35);
		
		frmArtista.add(btnMusicas);
		frmArtista.add(btnAlbuns);
		
		frmArtista.setVisible(true);
	}
	
	private void addMusica() {
		
		Frame frmAddMusica = new Frame("Adicionar Música");
		frmAddMusica.setPadding(15);
		
		JLabel lblNome = new JLabel("Título");
		lblNome.setBounds(0, 5, 50, 15);
		
		JTextField edtNome = new JTextField();
		edtNome.setBounds(65, 0, 150, 25);
		
		JLabel lblGenero = new JLabel("Gênero");
		lblGenero.setBounds(0, 45, 50, 15);
		
		JTextField edtGenero = new JTextField();
		edtGenero.setBounds(65, 40, 150, 25);
		
		JComboBox<String> cbxAlbum = Manager.getAlbumBox();
		cbxAlbum.setBounds(0, 80, 215, 25);
		
		frmAddMusica.add(lblNome);
		frmAddMusica.add(lblGenero);
		frmAddMusica.add(edtNome);
		frmAddMusica.add(edtGenero);
		frmAddMusica.add(cbxAlbum);
		
		frmAddMusica.setVisible(true);
	}
	
}