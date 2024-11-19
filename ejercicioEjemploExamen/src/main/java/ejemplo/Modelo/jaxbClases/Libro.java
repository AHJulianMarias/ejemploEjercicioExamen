package ejemplo.Modelo.jaxbClases;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class Libro {
	private String id;
	private String Titulo;
	private Autor autor;

	public Libro(String id, String titulo, Autor autor) {
		super();
		this.id = id;
		Titulo = titulo;
		this.autor = autor;
	}

	public Libro() {
		super();
	}

	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "Titulo")
	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		Titulo = titulo;
	}

	@XmlElement(name = "Autor")
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

}
