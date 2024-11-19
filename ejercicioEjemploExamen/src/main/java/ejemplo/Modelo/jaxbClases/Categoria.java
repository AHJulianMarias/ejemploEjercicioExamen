package ejemplo.Modelo.jaxbClases;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "nombreCategoria", "listaLibros" })
public class Categoria {
	private String id;
	private String nombreCategoria;
	private ArrayList<LibroJAXB> listaLibros = new ArrayList<LibroJAXB>();

	public Categoria() {
		super();
	}

	public Categoria(String id, String nombreCategoria) {
		super();
		this.id = id;
		this.nombreCategoria = nombreCategoria;
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
	public ArrayList<LibroJAXB> getListaLibros() {
		return listaLibros;
	}

	public void setListaLibros(ArrayList<LibroJAXB> listaLibros) {
		this.listaLibros = listaLibros;
	}

	public void anadirLibro(LibroJAXB libro) {
		if (this.listaLibros == null) {
			this.listaLibros = new ArrayList<>(); // Inicializar si es nulo
		}
		this.listaLibros.add(libro);
	}
}
