package ejemplo.Modelo;

public class categoriaSimple {
	private String id_Categoria;
	private String nombre_Categoria;

	public String getId_Categoria() {
		return id_Categoria;
	}

	public void setId_Categoria(String id_Categoria) {
		this.id_Categoria = id_Categoria;
	}

	public String getNombre_Categoria() {
		return nombre_Categoria;
	}

	public void setNombre_Categoria(String nombre_Categoria) {
		this.nombre_Categoria = nombre_Categoria;
	}

	public categoriaSimple(String id_Categoria, String nombre_Categoria) {
		super();
		this.id_Categoria = id_Categoria;
		this.nombre_Categoria = nombre_Categoria;
	}

	public categoriaSimple() {
		super();
	}

}
