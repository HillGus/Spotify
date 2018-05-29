package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Acao {
	
	// ArrayList com login e senha dos usuários
	ArrayList<Object[]> usuarios = new ArrayList<>();
	private String usuario;
	private int level = 1;
		
	public Acao() {
		
		addMusica();
		
		//Adiciona um usuário à lista de usuários
		usuarios.add(new Object[] {"Hill", "Bill", 1});
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
		
		frmPrincipal.setVisible(true);
	}
	
	private void TodasMusicas() {
		
		Frame frmMusicas = new Frame("Todas As Músicas");
		frmMusicas.setPadding(15);
		frmMusicas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextField edtPesquisa = new JTextField();
		edtPesquisa.setBounds(0, 0, 960, 25);
		
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
		
		//Centraliza os items da caixa
		((JLabel) cbxAlbum.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		frmAddMusica.add(lblNome);
		frmAddMusica.add(lblGenero);
		frmAddMusica.add(edtNome);
		frmAddMusica.add(edtGenero);
		frmAddMusica.add(cbxAlbum);
		
		frmAddMusica.setVisible(true);
	}
	
	private void Playlists() {
		
		
	}
}