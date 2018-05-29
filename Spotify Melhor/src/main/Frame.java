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
		
		super(titulo);
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setPadding(int padding) {
		
		this.padding = padding;
	}

	@Override
	public void setSize(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		super.setSize(width + 16, height + 39);
	}
	
	public void setVisible(boolean visivel) {
		
		this.setSize(width + padding * 2, height + padding * 2);
		
		super.setVisible(visivel);
		
		setLocationRelativeTo(null);
	}
	
	public void add(JComponent comp) {
		
		resizeIfNeeded(comp);
		
		int x = (int) comp.getLocation().getX(), y = (int) comp.getLocation().getY();
		
		comp.setLocation((int)x + padding, (int)y + padding);
		
		super.add(comp);
	}
	
	public void add(JComponent comp, String key) {
		
		componentes.put(key, comp);
		arrayComponentes.add(comp);
		
		resizeIfNeeded(comp);
		
		int x = (int) comp.getLocation().getX(), y = (int) comp.getLocation().getY();
		
		comp.setLocation((int)x + padding, (int)y + padding);
		
		add(comp);
	}
	
	public JComponent get(String key) {
		
		return componentes.get(key);
	}
	
	public <T> ArrayList<T> get(Class<T> classe) {
		
		ArrayList<T> comps = new ArrayList<>();
		
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
		
		this.width = this.width < x + width ? x + width : this.width;
		this.height = this.height < y + height ? y + height : this.height;
	}
}