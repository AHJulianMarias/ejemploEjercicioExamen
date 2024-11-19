package ejemplo.Modelo.jaxbClases;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class Autor {

	private String idAutor;
	private String nombre;
	private String pais;

	public Autor() {
		super();
	}

	public Autor(String idAutor, String nombre, String pais) {
		super();
		this.idAutor = idAutor;
		this.nombre = nombre;
		this.pais = pais;
	}

	@XmlAttribute(name = "id")
	public String getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(String idAutor) {
		this.idAutor = idAutor;
	}

	@XmlElement(name = "Nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlAttribute(name = "pais")
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}
