package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.table.DefaultTableModel;

public class Acao {
	
	// ArrayList com login e senha dos usuários
	ArrayList<Object[]> usuarios = new ArrayList<>();
	private int level = 1;
		
	public static User user;
	
	public Acao() {
		
		//Inicia os modelos de tabelas
		Manager.iniciarTabelas();
		
		//Inicia os usuários
		Manager.addUser("Hill", "Bill", 1);
		Manager.addUser("Nathan", "naruto", 0);
	}
	
	public Frame login() {

		// Criando janela
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
				
				//Obtém o usuário do sistema
				User userA = Manager.getUser(edtNome.getText(), String.valueOf(edtSenha.getPassword()));
				
				//Verifica se o usuário existe
				if (userA != null) {
					
					//Atribui o usuário local ao usuário obtido
					user = userA;
					
					//Atribui o level do usuário ao level local
					level = userA.getLevel();
					
					//Fecha a janela
					frmLogin.dispose();
					
					//Abre a janela principal
					Principal().setVisible(true);
				}
			}
		});
		
		//Adicionando componentes
		frmLogin.add(lblNome);
		frmLogin.add(lblSenha);
		frmLogin.add(edtNome);
		frmLogin.add(edtSenha);
		frmLogin.add(btnLogar);
		
		frmLogin.getRootPane().setDefaultButton(btnLogar);
		
		frmLogin.setVisible(true);
		
		//Retornando frame
		return frmLogin;
	}

	private Frame Principal() {
		
		//Cria janela
		Frame frmPrincipal = new Frame("Principal");
		frmPrincipal.setPadding(15);
		
		//Componentes
		JButton btnTdsMusicas = new JButton("Todas As Músicas");
		btnTdsMusicas.setBounds(0, 0, 200, 35);
		btnTdsMusicas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Fecha a janela principal
				frmPrincipal.dispose();
				
				//Abre a janela que mostra todas as músicas
				TodasMusicas().setVisible(true);				
			}
		});
		
		JButton btnPlaylists = new JButton("Playlists");
		btnPlaylists.setBounds(0, 50, 200, 35);
		btnPlaylists.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Fecha a janela principal
				frmPrincipal.dispose();
				
				//Abre a janela que mostra as playlist
				Playlists().setVisible(true);
			}
		});
		
		//Adicionando componentes
		frmPrincipal.add(btnTdsMusicas);
		frmPrincipal.add(btnPlaylists);
		
		//Verifica se o usuário logado é um artista
		if (level == 1) {
			
			JButton btnArtista = new JButton("Artista");
			btnArtista.setBounds(0, 100, 200, 35);
			btnArtista.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					//Fecha a janela principal
					frmPrincipal.dispose();
					
					//Abre a janela de gerenciamento do artista
					Artista().setVisible(true);
				}
			});
			
			//Adicionando componente
			frmPrincipal.add(btnArtista);
		}
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(0, level == 0 ? 100 : 150, 200, 35);
		btnSair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Fecha a janela principal
				frmPrincipal.dispose();
				
				//Abre a janela de login
				login();
			}
		});
		
		//Adicionando componente
		frmPrincipal.add(btnSair);
		
		//Retornando frame
		return frmPrincipal;
	}
	
	private Frame TodasMusicas() {

		//Cria janela
		Frame frmMusicas = new Frame("Todas As Músicas");
		
		//Configura janela
		frmMusicas.setPadding(15);
		frmMusicas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Adiciona listener para quando essa janela fechar abrir a janela principal
		frmMusicas.addWindowListener(new WindowClosingListener(Principal()));
		
		//Componentes
		
		//Obtendo componentes de filtragem de músicas
		JComponent[] compsPesquisa = getComponentesPesquisa(Manager.tbTdMusicas, Manager.musicas);
		
		compsPesquisa[0].setBounds(0, 0, 960, 25);
		compsPesquisa[1].setBounds(985, 5, 15, 15);
		
		JScrollPane scrollMusicas = Manager.getTbTodasMusicas();
		scrollMusicas.setBounds(0, 40, 1000, 250);
		
		//Adicionando componentes
		frmMusicas.add(compsPesquisa[0]);
		frmMusicas.add(compsPesquisa[1]);
		frmMusicas.add(scrollMusicas);
		
		//Retornando frame
		return frmMusicas;
	}

	private Frame Playlists() {
		
		//Criando janela
		Frame frmPlaylists = new Frame("Playlists");
		
		//Configurando janela
		frmPlaylists.setPadding(15);
		frmPlaylists.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Adicionando listener para quando essa janela fechar abrir a janela principal
		frmPlaylists.addWindowListener(new WindowClosingListener(Principal()));
		
		//Componentes
		JLabel lblPlaylists = new JLabel("Playlists");
		lblPlaylists.setBounds(0, 5, 50, 15);
		
		JComboBox<String> cbxPlaylists = Manager.getPlaylistBox();
		cbxPlaylists.setBounds(65, 0, 350, 25);
		cbxPlaylists.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				//Atualiza a tabela de playlists de acordo com a playlist selecionada
				Manager.atualizarTbMusicasPlaylist((String) cbxPlaylists.getSelectedItem());
			}
		});
		
		JButton btnNewPlaylist = new JButton("Nova Playlist");
		btnNewPlaylist.setBounds(430, 0, 150, 25);
		btnNewPlaylist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				//Obtendo título da nova playlist
				String titulo = "";
				
				while (titulo.equals("")) {
					
					titulo = JOptionPane.showInputDialog("Informe o nome da nova playlist");
				}
				
				//Adicionando nova playlist
				Manager.newPlaylist(titulo);
			}
		});
		
		JButton btnRemPlaylist = new JButton("Remover Playlist");
		btnRemPlaylist.setBounds(595, 0, 150, 25);
		btnRemPlaylist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//Removendo playlist selecionada
				Manager.removePlaylist((String) cbxPlaylists.getSelectedItem());
			}
		});
		
		JTextField edtPesquisa = new JTextField();
		edtPesquisa.setBounds(0, 40, 960, 25);
		edtPesquisa.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				//Obtendo playlist selecionada
				Playlist playlist = Manager.getPlaylist((String) cbxPlaylists.getSelectedItem());
				
				//Filtrando as músicas da tabela
				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbMusicasPlaylist, playlist.getMusicas());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {

				//Obtendo playlist selecionada
				Playlist playlist = Manager.getPlaylist((String) cbxPlaylists.getSelectedItem());
				
				//Filtrando as músicas da tabela
				Manager.filtrarTb(edtPesquisa.getText(), Manager.tbMusicasPlaylist, playlist.getMusicas());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lblInfoPesquisa = new JLabel("?");
		lblInfoPesquisa.setBounds(985, 45, 15, 15);
		lblInfoPesquisa.setToolTipText("<html><p>Use este campo para pesquisar suas músicas por:<br/>"
									 + " Nome, Gênero, Artista, Album ou Duração</p></html>");
		
		JScrollPane scrollMusicas = Manager.getTbMusicasPlaylist((String) cbxPlaylists.getSelectedItem());
		scrollMusicas.setBounds(0, 80, 1000, 250);
		
		JButton btnAddMusica = new JButton("Adicionar Música");
		btnAddMusica.setBounds(0, 345, 150, 25);
		btnAddMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Abrindo janela para adicionar músicas à playlist selecionada
				addMusicaOnPlaylist().setVisible(true);
			}
		});
		
		JButton btnRemMusica = new JButton("Remover Música");
		btnRemMusica.setBounds(165, 345, 150, 25);
		btnRemMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//Obtendo JTable
				JTable tabela = ((JTable) scrollMusicas.getViewport().getView());
				
				//removendo música selecionada da playlist selecionada
				Manager.getPlaylist((String) cbxPlaylists.getSelectedItem()).remMusica(tabela.getSelectedRow());				
			}
		});
		
		//Adicionando componentes
		frmPlaylists.add(btnNewPlaylist);
		frmPlaylists.add(btnRemPlaylist);
		frmPlaylists.add(btnAddMusica);
		frmPlaylists.add(btnRemMusica);
		frmPlaylists.add(edtPesquisa);
		frmPlaylists.add(lblInfoPesquisa);
		frmPlaylists.add(lblPlaylists);
		frmPlaylists.add(cbxPlaylists);
		frmPlaylists.add(scrollMusicas);
		
		//Retornando frame
		return frmPlaylists;
	}
	
	private Frame addMusicaOnPlaylist() {
		
		//Criando janela
		Frame frm = new Frame("Todas As Músicas");
		
		//Configurando janela
		frm.setPadding(15);
		frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Componentes
		
		//Obtendo componentes de pesquisa
		JComponent[] compsPesquisa = getComponentesPesquisa(Manager.tbTdMusicasResumidas, Manager.musicas);
		
		compsPesquisa[0].setBounds(0, 0, 205, 25);
		compsPesquisa[1].setBounds(220, 5, 10, 15);
		
		JScrollPane scrollMusicasResumidas = Manager.getTbTodasMusicasResumida();
		scrollMusicasResumidas.setBounds(0, 40, 240, 300);
		
		//Adicionando componentes
		frm.add(compsPesquisa[0]);
		frm.add(compsPesquisa[1]);
		frm.add(scrollMusicasResumidas);
		
		//Retornando frame
		return frm;
	}
	
	private Frame Artista() {
		
		//Criando janela
		Frame frmArtista = new Frame("Artista");
		
		//Configurando janela
		frmArtista.setPadding(15);
		frmArtista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Adicionando listener para quando essa janela fechar abrir a janela principal
		frmArtista.addWindowListener(new WindowClosingListener(Principal()));
		
		//Componentes
		JButton btnAlbuns = new JButton("Álbuns");
		btnAlbuns.setBounds(0, 0, 200, 35);
		btnAlbuns.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//Deixa janela atual invisível
				frmArtista.setVisible(false);
				
				//Abre janela de álbuns do artista
				albunsArtista().setVisible(true);
			}
		});
		
		JButton btnMusicas = new JButton("Músicas");
		btnMusicas.setBounds(0, 50, 200, 35);
		btnMusicas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Deixa janela atual invisível
				frmArtista.setVisible(false);
				
				//Abre janela de músicas do artista
				musicasArtista().setVisible(true);
			}
		});
		
		//Adicionando componentes
		frmArtista.add(btnMusicas);
		frmArtista.add(btnAlbuns);
		
		//Retornando frame
		return frmArtista;
	}
	
	private Frame musicasArtista() {
		
		//Criando janela
		Frame frm = new Frame("Suas Músicas");
		
		//Configurando janela
		frm.setPadding(15);
		frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
		
		//Adicionando listener para quando essa janela fechar abrir a janela do artista
		frm.addWindowListener(new WindowClosingListener(Artista()));
		
		//Componentes
		
		//Obtendo componentes de filtragem de músicas
		JComponent[] compsPesquisa = getComponentesPesquisa(((Artista) user).getModeloMusicas(), ((Artista) user).getMusicas());
		
		compsPesquisa[0].setBounds(0, 0, 320, 25);
		compsPesquisa[1].setBounds(335, 5, 15, 15);
		
		JScrollPane scrollMusicas = ((Artista) user).getMusicasScroll();
		scrollMusicas.setBounds(0, 40, 350, 200);
		
		JButton btnAddMusica = new JButton("Adicionar Música");
		btnAddMusica.setBounds(0, 255, 168, 25);
		btnAddMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//Abrindo janela para adicionar novas músicas
				addMusica().setVisible(true);
			}
		});
		
		JButton btnRemMusica = new JButton("Remover Música");
		btnRemMusica.setBounds(182, 255, 168, 25);
		btnRemMusica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Obtendo index selecionado
				int index = ((JTable) scrollMusicas.getViewport().getView()).getSelectedRow();
				
				//Removendo música selecionada
				((Artista) user).remMusica(index);
			}
		});
		
		//Adicionando componentes
		frm.add(btnAddMusica);
		frm.add(btnRemMusica);
		frm.add(compsPesquisa[0]);
		frm.add(compsPesquisa[1]);
		frm.add(scrollMusicas);
		
		//Retornando frame
		return frm;
	}
	
	private Frame addMusica() {
		
		//Criando janela
		Frame frmAddMusica = new Frame("Adicionar Música");
		
		//Configurando janela
		frmAddMusica.setPadding(15);
		frmAddMusica.setDefaultCloseOperation(Frame.DISPOSE_ON_CLOSE);
		
		//Componentes
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
		
		JLabel lblDuracao = new JLabel("Duração");
		lblDuracao.setBounds(0, 125, 50, 15);
		
		JTextField edtDuracao = new JTextField();
		edtDuracao.setBounds(65, 120, 150, 25);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(35, 160, 145, 25);
		btnAdicionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Obtendo informações da nova música
				String titulo = edtNome.getText();
				String genero = edtGenero.getText();
				String duracao = edtDuracao.getText();
				String album = (String) cbxAlbum.getSelectedItem();
				
				
				//Verificando se as informações são válidas
				if (genero.isEmpty()) {
					
					JOptionPane.showMessageDialog(null, "Gênero inválido!");
					return;
				}
				
				if (album.equals("Álbum")) {
					
					JOptionPane.showMessageDialog(null, "Álbum inválido!");
					return;
				}
				
				if (titulo.isEmpty()) {
					
					JOptionPane.showMessageDialog(null, "Título inválido!");
					return;
				}
				
				if (((Artista) user).getMusica(titulo) != null) {
					
					JOptionPane.showMessageDialog(null, "Música com esse título já existente!");
				}
				
				if (!duracao.matches("\\d*:\\d{2}")) {
					
					JOptionPane.showMessageDialog(null, "Duração inválida!");
					return;
				}
				
				//Adicionando nova música
				Manager.addMusica(titulo, genero, user.getNome(), album, Integer.parseInt(duracao.split(":")[0]), Integer.parseInt(duracao.split(":")[1]));
				
				//Fechando janela
				frmAddMusica.dispose();
			}
		});
		
		//Adicionando componentes
		frmAddMusica.add(btnAdicionar);
		frmAddMusica.add(lblNome);
		frmAddMusica.add(lblGenero);
		frmAddMusica.add(lblDuracao);
		frmAddMusica.add(edtNome);
		frmAddMusica.add(edtGenero);
		frmAddMusica.add(edtDuracao);
		frmAddMusica.add(cbxAlbum);
		
		frmAddMusica.getRootPane().setDefaultButton(btnAdicionar);
		
		//Retornando frame
		return frmAddMusica;
	}
	
	private Frame albunsArtista() {
		
		//Criando janela
		Frame frmAlbuns = new Frame("Seus Álbuns");
		
		//Configurando janela
		frmAlbuns.setPadding(15);
		frmAlbuns.setDefaultCloseOperation(Frame.DISPOSE_ON_CLOSE);
		
		//Adicionando listener para quando essa janela fecar abrir a janela do artista
		frmAlbuns.addWindowListener(new WindowClosingListener(Artista()));
		
		//Componentes
		JComboBox<String> combo = ((Artista) user).getAlbunsBox();
		combo.setBounds(0, 0, 350, 25);
		
		JScrollPane scrollMusicas = ((Artista) user).getAlbumMusicasScroll((String) combo.getSelectedItem());
		scrollMusicas.setBounds(0, 40, 350, 200);
		
		combo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				//Muda as músicas da tabela de acordo com o álbum selecionado
				((Artista) user).atualizarMusicasAlbum((String) combo.getSelectedItem());
			}
		});
		
		JButton btnAddAlbum = new JButton("Novo Álbum");
		btnAddAlbum.setBounds(0, 255, 168, 25);
		btnAddAlbum.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		
				//Obtendo titulo do novo álbum
				String titulo = "";
				
				while (titulo.isEmpty()) {
					
					titulo = JOptionPane.showInputDialog("Informe o nome do novo álbum");
				}
				
				//Adicionando novo álbum
				((Artista) user).newAlbum(titulo);
			}
		});
		
		JButton btnRemAlbum = new JButton("Excluir Álbum");
		btnRemAlbum.setBounds(182, 255, 168, 25);
		btnRemAlbum.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Removendo álbum escolhido
				((Artista) user).removeAlbum((String) combo.getSelectedItem());
			}
		});
		
		//Adicionando componentes
		frmAlbuns.add(btnAddAlbum);
		frmAlbuns.add(btnRemAlbum);
		frmAlbuns.add(combo);
		frmAlbuns.add(scrollMusicas);
		
		//Retornando frame
		return frmAlbuns;
	}
	
	private JComponent[] getComponentesPesquisa(DefaultTableModel modelo, ArrayList<Musica> conjuntoDeMusicas) {
		
		//Cria JTextField
		JTextField edtPesquisa = new JTextField();
		
		//Adiciona listener que "ouve" sempre que o texto muda
		edtPesquisa.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				//Filtra a tabela de acordo com o texto digitado
				Manager.filtrarTb(edtPesquisa.getText(), modelo, conjuntoDeMusicas);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {

				//Filtra a tabela de acordo com o texto digitado
				Manager.filtrarTb(edtPesquisa.getText(), modelo, conjuntoDeMusicas);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});

		//Cria JLabel de info
		JLabel lblInfoPesquisa = new JLabel("?");
		lblInfoPesquisa.setToolTipText("<html><p>Use este campo para pesquisar suas músicas por:<br/>"
									 + " Nome, Gênero, Artista, Album ou Duração</p></html>");
		
		//Retornando componentes
		return new JComponent[] {edtPesquisa, lblInfoPesquisa};
	}
}