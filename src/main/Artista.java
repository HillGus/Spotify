package main;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Artista extends User {

	private ArrayList<Album> albuns = new ArrayList<>();
	private ArrayList<Musica> musicas = new ArrayList<>();
	
	public Artista(String nome, String senha) {
		
		super(nome, senha, 1);
	}
	
	public ArrayList<Musica> getMusicas() {
		
		return this.musicas;
	}
	
	public ArrayList<Album> getAlbuns() {
		
		return this.albuns;
	}
	
	public JScrollPane getMusicasScroll() {
		
		DefaultTableModel modelo = new DefaultTableModel();
		
		modelo.addColumn("Título");
		modelo.addColumn("Álbum");
		modelo.addColumn("Duração");
		
		for (Musica musica : musicas) {
			
			modelo.addRow(new Object[] {musica.getNome(), musica.getAlbum(), musica.getDuracao()});
		}
		
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
		
		tabela.setModel(modelo);
		tabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
	
	public JScrollPane getAlbumMusicasScroll(String nomeAlbum) {
		
		DefaultTableModel modelo = new DefaultTableModel();
		
		modelo.addColumn("Título");
		modelo.addColumn("Duração");
		
		for (Album album : albuns) {
			
			if (album.getTitulo().equals(nomeAlbum)) {
				
				for (Musica musica : album.getMusicas()) {
					
					modelo.addRow(new Object[] {musica.getNome(), musica.getDuracao()});
				}
			}
		}
		
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
		
		tabela.setModel(modelo);
		tabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
}
