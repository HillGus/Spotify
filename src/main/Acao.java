package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Acao {
	
	// ArrayList com login e senha dos usu�rios
	ArrayList<Object[]> usuarios = new ArrayList<>();
	private String usuario;
	private int level = 1;
		
	public Acao() {
		
		//Adiciona um usu�rio � lista de usu�rios
		usuarios.add(new Object[] {"Hill", "Bill", 1});
	}
	
	public void login() {

		// Criando JFrame
		Frame frmLogin = new Frame("Login");
		frmLogin.setPadding(15);

		// Componentes
		JLabel lblNome = new JLabel("Usu�rio");
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
				
				int logado = confirmarLogin(edtNome.getText(), edtSenha.getText());
				
				if (logado > -1) {
					
					level = logado;
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

	private int confirmarLogin(String usuario, String senha) {
		
		for (Object[] dados : usuarios) {
			
			String usuarioA = dados[0].toString();
			String senhaA = dados[1].toString();
			
			if ((usuario.equals(usuarioA)) && (senha.equals(senhaA))) {
				
				this.usuario = usuario;
				
				//Retorna o nivel do usuario no sistema
				return (int) dados[2];
			}
		}
		
		return -1;
	}	

	public void Principal() {
		
		Frame frmPrincipal = new Frame("Principal");
		frmPrincipal.setPadding(15);
		
		//Componentes
		JButton btnTdsMusicas = new JButton("Todas As M�sicas");
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
			
			JButton btnArtista = new JButton("Autorais");
			btnArtista.setBounds(0, 50, 200, 35);
			btnArtista.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					Autorais();
				}
			});
			
			frmPrincipal.add(btnArtista);
		}
		
		frmPrincipal.setVisible(true);
	}
	
	private void TodasMusicas() {
		
		Frame frmMusicas = new Frame("Todas As M�sicas");
		frmMusicas.setPadding(15);
		frmMusicas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextField edtPesquisa = new JTextField();
		edtPesquisa.setBounds(0, 0, 960, 25);
		edtPesquisa.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbTdMusicas);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {

				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbTdMusicas);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lblInfoPesquisa = new JLabel("?");
		lblInfoPesquisa.setBounds(985, 5, 15, 15);
		lblInfoPesquisa.setToolTipText("<html><p>Use este campo para pesquisar suas m�sicas por:<br/>"
									 + " Nome, G�nero, Artista, Album ou Dura��o</p></html>");
		
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
		
		JScrollPane scrollMusicas = Manager.getTbMusicasPlaylist("");
		scrollMusicas.setBounds(0, 40, 1000, 250);
		
		frmPlaylists.add(scrollMusicas);
		
		frmPlaylists.setVisible(true);
	}
	
	private void Autorais() {
		
		
	}
	
	private void addMusica() {
		
		Frame frmAddMusica = new Frame("Adicionar M�sica");
		frmAddMusica.setPadding(15);
		
		JLabel lblNome = new JLabel("T�tulo");
		lblNome.setBounds(0, 5, 50, 15);
		
		JTextField edtNome = new JTextField();
		edtNome.setBounds(65, 0, 150, 25);
		
		JLabel lblGenero = new JLabel("G�nero");
		lblGenero.setBounds(0, 45, 50, 15);
		
		JTextField edtGenero = new JTextField();
		edtGenero.setBounds(65, 40, 150, 25);
		
		JComboBox<String> cbxAlbum = Manager.getAlbumBox();
		cbxAlbum.setBounds(0, 80, 215, 25);
		
		//Centraliza os items da caixa
		((JLabel) cbxAlbum.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		frmAddMusica.add(lblNome);
		frmAddMusica.add(lblGenero);
		frmAddMusica.add(edtNome);
		frmAddMusica.add(edtGenero);
		frmAddMusica.add(cbxAlbum);
		
		frmAddMusica.setVisible(true);
	}
	
}