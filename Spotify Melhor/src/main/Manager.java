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
	private static DefaultTableModel tbTodasMusicas;
	
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
		
		combo.addItem("Teste");
		
		return combo;
	}
	
	public static void newAlbum(String titulo) {
		
		albuns.add(new Album(titulo));
	}
	
	public static JScrollPane getTbTodasMusicas() {
		
		tbTodasMusicas = new DefaultTableModel();
		
		//Adicionando colunas
		tbTodasMusicas.addColumn("Título");
		tbTodasMusicas.addColumn("Gênero");
		tbTodasMusicas.addColumn("Artista");
		tbTodasMusicas.addColumn("Álbum");
		tbTodasMusicas.addColumn("Duração");
		
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
		
		tabela.setModel(tbTodasMusicas);
		tabela.setDefaultEditor(Object.class, null);
		tabela.getTableHeader().setBackground(Color.WHITE);		
		
		tabela.getColumn("Duração").setPreferredWidth(0);
		
		JScrollPane scroll = new JScrollPane(tabela);
		
		return scroll;
	}
	
	private static void atualizarTbTodasMusicas() {
		
		tbTodasMusicas.setRowCount(0);
		
		for (Musica musica : musicas) {
			
			tbTodasMusicas.addRow(musica.getInfo());
		}
	}
}