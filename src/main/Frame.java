package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Map<String, JComponent> componentes = new HashMap<String, JComponent>();
	private ArrayList<JComponent> arrayComponentes = new ArrayList<>();
	
	private int padding = 0, width = 0, height = 0;
	
	public Frame() {
		
		this("");
	}
	
	public Frame(String titulo) {
		
		//Cria um JFrame
		super(titulo);
		
		//Configura o JFrame
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//Define a margem interna do frame
	public void setPadding(int padding) {
		
		this.padding = padding;
	}

	@Override
	public void setSize(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		//Define a dimensão do frame do tamanho que o pane interno fique com os parâmetros informados
		super.setSize(width + 16, height + 39);
	}
	
	public void setVisible(boolean visivel) {
		
		//Adiciona a margem interna ao frame
		this.setSize(width + padding * 2, height + padding * 2);
		
		super.setVisible(visivel);
		
		setLocationRelativeTo(null);
	}
	
	public void add(JComponent comp) {
		
		resizeIfNeeded(comp);
		
		//Obtem x e y do componente
		int x = (int) comp.getLocation().getX(), y = (int) comp.getLocation().getY();
		
		//define a localização do componente de acordo com a margem interna
		comp.setLocation((int)x + padding, (int)y + padding);
		
		super.add(comp);
	}
	
	public void add(JComponent comp, String key) {
		
		//Adiciona o componente no dicionário e no array
		componentes.put(key, comp);
		arrayComponentes.add(comp);
		
		resizeIfNeeded(comp);
		
		//Obtem x e y do componente
		int x = (int) comp.getLocation().getX(), y = (int) comp.getLocation().getY();
		
		//Define a localização do componente de acordo com a margem interna
		comp.setLocation((int)x + padding, (int)y + padding);
		
		add(comp);
	}
	
	public JComponent get(String key) {
		
		return componentes.get(key);
	}
	
	public <T> ArrayList<T> get(Class<T> classe) {
		
		//Cria um arrayList da classe informada
		ArrayList<T> comps = new ArrayList<>();
		
		//Adiciona os componentes que forem daquela classe ao arraylist
		for (JComponent comp : arrayComponentes) {
			
			try {
				comps.add(classe.cast(comp));
			} catch (Exception e) {}
		}
		
		return comps;
	}

	private void resizeIfNeeded(JComponent comp) {
		
		int x = (int) comp.getLocation().getX();
		int y = (int) comp.getLocation().getY();
		int width = (int) comp.getSize().getWidth();
		int height = (int) comp.getSize().getHeight();
		
		//Aumenta o tamanho do Frame caso ele não seja grande o suficiente para os componentes
		this.width = this.width < x + width ? x + width : this.width;
		this.height = this.height < y + height ? y + height : this.height;
	}
}