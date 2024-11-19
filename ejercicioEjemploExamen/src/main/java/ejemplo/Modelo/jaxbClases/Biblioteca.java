package ejemplo.Modelo.jaxbClases;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;

@jakarta.xml.bind.annotation.XmlRootElement(name = "Biblioteca")
public class Biblioteca {
	private ArrayList<Categoria> listaCategoria;

	public Biblioteca(ArrayList<Categoria> listaCategoria) {
		super();
		this.listaCategoria = listaCategoria;
	}

	public Biblioteca() {
		super();
	}

	@XmlElement(name = "Categoria")
	public ArrayList<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(ArrayList<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

}
