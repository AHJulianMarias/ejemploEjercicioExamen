package ejemplo.Modelo;

public class libro {

	private String titulo;
	private String autor;
	private String pais;
	private String categoria;

	public libro(String titulo, String autor, String pais, String categoria) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.pais = pais;
		this.categoria = categoria;
	}

	public libro() {
		super();
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public String getPais() {
		return pais;
	}

	public String getCategoria() {
		return categoria;
	}

}