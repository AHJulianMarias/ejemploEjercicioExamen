package ejemplo.Modelo.jaxbClases;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

public class Categoria {
	private String id;
	private String nombreCategoria;
	private ArrayList<Libro> listaLibros;

	public Categoria() {
		super();
	}

	public Categoria(String id, String nombreCategoria, ArrayList<Libro> listaLibros) {
		super();
		this.id = id;
		this.nombreCategoria = nombreCategoria;
		this.listaLibros = listaLibros;
	}

	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "NombreCategoria")
	public String getnombreCategoria() {
		return nombreCategoria;
	}

	public void setnombreCategoria(String nombre) {
		this.nombreCategoria = nombre;
	}

	@XmlElementWrapper(name = "Libros")
	@XmlElement(name = "Libro")
	public ArrayList<Libro> getListaLibros() {
		return listaLibros;
	}

	public void setListaLibros(ArrayList<Libro> listaLibros) {
		this.listaLibros = listaLibros;
	}

	public void anadirLibro(Libro libro) {
		if (this.listaLibros == null) {
			this.listaLibros = new ArrayList<>(); // Inicializar si es nulo
		}

	}
}
