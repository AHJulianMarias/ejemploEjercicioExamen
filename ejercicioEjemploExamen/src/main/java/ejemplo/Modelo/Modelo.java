package ejemplo.Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Utilidades.Utilidades;
import ejemplo.Modelo.jaxbClases.Autor;
import ejemplo.Modelo.jaxbClases.Biblioteca;
import ejemplo.Modelo.jaxbClases.Categoria;
import ejemplo.Modelo.jaxbClases.Libro;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class Modelo {

	public final static String FICHEROTRABAJO = "DatosBiblioteca_Expandido.xlsx";
	public final static String FICHEROTRABAJOOUT = "extraccion.xml";

	private static Conexion conexionbbdd;

	public static boolean introducirDatosBBDD() {
		conexionbbdd = Conexion.getInstance();

		if (!Conexion.isConectado()) {
			System.out.println("Error: No se pudo conectar a la base de datos.");
			return false;
		}
		ArrayList<libro> listaLibros = devolverLibros();
		if (introducirAutores(listaLibros)) {
			if (introducirCategorias(listaLibros)) {
				if (introducirLibros(listaLibros)) {
					return true;
				}
			}

		}

		return false;

	}

	private static boolean introducirCategorias(ArrayList<libro> listaLibros) {
		HashSet<String> categorias = devolverNoDuplicadas(listaLibros);
		try {
			PreparedStatement sentencia;
			sentencia = conexionbbdd.getConexionMySQL()
					.prepareStatement("INSERT INTO categorias (nombre_categoria) Values (?)");
			for (String cat : categorias) {
				sentencia.setString(1, cat);
				sentencia.executeUpdate();
			}
			sentencia.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	private static boolean introducirAutores(ArrayList<libro> listaLibros) {

		try {
			PreparedStatement sentencia;
			sentencia = conexionbbdd.getConexionMySQL()
					.prepareStatement("INSERT INTO autores (nombre, pais) Values (?,?)");
			for (libro lib : listaLibros) {
				sentencia.setString(1, lib.getAutor());
				sentencia.setString(2, lib.getPais());
				sentencia.executeUpdate();
			}
			sentencia.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	private static boolean introducirLibros(ArrayList<libro> listaLibros) {
		ResultSet resultIdAutor;
		int idAutor = 0;
		int idCat = 0;
		ResultSet resultIdCat;
		PreparedStatement sentenciaSelectIDAutor = null;
		PreparedStatement sentenciaSelectIDCategoria = null;
		PreparedStatement sentenciaInsert = null;

		try {
			for (libro l : listaLibros) {
				// coges el idautor
				sentenciaSelectIDAutor = conexionbbdd.getConexionMySQL()
						.prepareStatement("SELECT id_autor FROM autores WHERE nombre = ? AND pais = ?");

				sentenciaSelectIDAutor.setString(1, l.getAutor().toString());
				sentenciaSelectIDAutor.setString(2, l.getPais().toString());
				resultIdAutor = sentenciaSelectIDAutor.executeQuery();
				if (resultIdAutor.next()) {
					idAutor = resultIdAutor.getInt(1);
				}
				// coges el id categoria
				sentenciaSelectIDCategoria = conexionbbdd.getConexionMySQL()
						.prepareStatement("SELECT id_categoria FROM categorias WHERE nombre_categoria = ?");
				sentenciaSelectIDCategoria.setString(1, l.getCategoria().toString());
				resultIdCat = sentenciaSelectIDCategoria.executeQuery();
				if (resultIdCat.next()) {
					idCat = resultIdCat.getInt(1);
				}

				// haces insert
				sentenciaInsert = conexionbbdd.getConexionMySQL()
						.prepareStatement("INSERT INTO libros (titulo, id_autor,id_categoria) Values (?,?,?)");
				sentenciaInsert.setString(1, l.getTitulo().toString());
				sentenciaInsert.setInt(2, idAutor);
				sentenciaInsert.setInt(3, idCat);
				sentenciaInsert.executeUpdate();
			}
			// cierras las sentencias
			sentenciaInsert.close();
			sentenciaSelectIDCategoria.close();
			sentenciaSelectIDAutor.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	private static ArrayList<libro> devolverLibros() {
		ArrayList<libro> listaLibros = new ArrayList<libro>();
		Workbook wb;
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File(Utilidades.RUTADATOS + FICHEROTRABAJO)));
			Sheet hoja1 = wb.getSheetAt(0);
			int numFila = 1;
			Row fila = hoja1.getRow(numFila);
			while (fila != null) {
				listaLibros.add(new libro(fila.getCell(0).toString(), fila.getCell(1).toString(),
						fila.getCell(2).toString(), fila.getCell(3).toString()));
				fila = hoja1.getRow(numFila++);
			}
			wb.close();
			if (listaLibros.size() > 0) {
				return listaLibros;
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
//EN CASO DE PROBLEMA DEVUELVE NULO
		return null;
	}

	private static HashSet<String> devolverNoDuplicadas(ArrayList<libro> listaLibros) {

		HashSet<String> hashSetCategorias = new HashSet<String>();
		for (libro l : listaLibros) {
			hashSetCategorias.add(l.getCategoria());
		}
		return hashSetCategorias;

	}

	private static boolean sacarAutoresBBDD() {
		ArrayList<Autor> listaAutores = new ArrayList<Autor>();
		ResultSet resultAutores;
		PreparedStatement sentenciaSelectAutores = null;
		PreparedStatement sentenciaSelectLibros = null;
		try {
			sentenciaSelectAutores = conexionbbdd.getConexionMySQL()
					.prepareStatement("SELECT id_autor,nombre,pais FROM autores");
			resultAutores = sentenciaSelectAutores.executeQuery();
			while (resultAutores.next()) {
				listaAutores.add(new Autor(String.valueOf(resultAutores.getInt("id_autor")),
						resultAutores.getString("nombre"), resultAutores.getString("pais")));
			}
			sentenciaSelectAutores.close();
			sentenciaSelectLibros = conexionbbdd.getConexionMySQL()
					.prepareStatement("SELECT id_autor,nombre,pais FROM autores");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

//	private static ArrayList<Categoria> obtenerCategoriasDesdeBD() {
//
//		conexionbbdd = Conexion.getInstance();
//		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
//		PreparedStatement sentenciaSelectAutores = null;
//		ResultSet rs;
//		try {
//			// Asumiendo que tienes una conexión a la base de datos configurada
//			String query = "SELECT c.id_categoria, c.nombre_categoria, l.id_libro, l.titulo, a.id_autor, a.nombre, a.pais "
//					+ "FROM categorias c " + "JOIN libros l ON c.id_categoria = l.id_categoria "
//					+ "JOIN autores a ON l.id_autor = a.id_autor";
//			sentenciaSelectAutores = conexionbbdd.getConexionMySQL().prepareStatement(query);
//			rs = sentenciaSelectAutores.executeQuery();
//			HashMap<Integer, Categoria> categoriaMap = new HashMap<>();
//
//			while (rs.next()) {
//				// Recuperar datos de la categoría
//				int idCategoria = rs.getInt("id_categoria");
//				String nombreCategoria = rs.getString("nombre_categoria");
//
//				Categoria categoria = categoriaMap.get(idCategoria);
//				if (categoria == null) {
//					categoria = new Categoria();
//					categoria.setId(String.valueOf(idCategoria));
//					categoria.setnombreCategoria(nombreCategoria);
//					categoriaMap.put(idCategoria, categoria);
//				}
//
//				// Crear autor
//				int idAutor = rs.getInt("id_autor");
//				String nombreAutor = rs.getString("nombre");
//				String paisAutor = rs.getString("pais");
//
//				Autor autor = new Autor();
//				autor.setIdAutor(String.valueOf(idAutor));
//				autor.setNombre(nombreAutor);
//				autor.setPais(paisAutor);
//
//				// Crear libro
//				int idLibro = rs.getInt("id_libro");
//				String titulo = rs.getString("titulo");
//
//				Libro libro = new Libro();
//				libro.setId(String.valueOf(idLibro));
//				libro.setTitulo(titulo);
//				libro.setAutor(autor);
//
//				// Agregar libro a la categoría
//				categoria.anadirLibro(libro);
//			}
//
//			categorias.addAll(categoriaMap.values());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return categorias;
//	}

	public static void persistirMemoria() {
		try {
			ArrayList<Categoria> categorias = obtenerCategoriasDesdeBD2();
			Biblioteca biblioteca = new Biblioteca();
			biblioteca.setListaCategoria(categorias);

			// Crear el contexto JAXB para la clase Biblioteca
			JAXBContext contexto = JAXBContext.newInstance(Biblioteca.class);

			// Crear un Marshaller para convertir el objeto en XML
			Marshaller marshaller = contexto.createMarshaller();

			// Formatear el XML para que sea legible
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Escribir el XML en un archivo
			marshaller.marshal(biblioteca, new File(Utilidades.RUTADATOS + FICHEROTRABAJOOUT));

			// También puedes imprimirlo en consola (opcional)
			System.out.println("Archivo XML generado correctamente:");
			marshaller.marshal(biblioteca, System.out);

		} catch (JAXBException e) {
			System.err.println("Error al generar el archivo XML: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static ArrayList<Categoria> obtenerCategoriasDesdeBD2() {
		ArrayList<Autor> listaAutores = new ArrayList<Autor>();
		ArrayList<Libro> listaLibros = new ArrayList<Libro>();
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();

		PreparedStatement sentenciaSelectAutores = null;
		PreparedStatement sentenciaSelectLibros = null;
		PreparedStatement sentenciaSelectCategorias = null;
		PreparedStatement sentenciaSelectId_CatId_Libro = null;
		ResultSet rs;

		String idAutor_libro;
		conexionbbdd = Conexion.getInstance();

		try {
			sentenciaSelectAutores = conexionbbdd.getConexionMySQL().prepareStatement("SELECT * FROM autores");
			rs = sentenciaSelectAutores.executeQuery();
			while (rs.next()) {
				listaAutores.add(
						new Autor(String.valueOf(rs.getInt("id_autor")), rs.getString("nombre"), rs.getString("pais")));
			}
			System.out.println("Lista de autores completada");
			sentenciaSelectLibros = conexionbbdd.getConexionMySQL().prepareStatement("SELECT * FROM libros");
			rs = sentenciaSelectLibros.executeQuery();
			while (rs.next()) {
				idAutor_libro = String.valueOf(rs.getInt("id_autor"));
				for (Autor a : listaAutores) {
					if (a.getIdAutor().equals(idAutor_libro)) {
						listaLibros.add(new Libro(rs.getString("id_libro"), rs.getString("titulo"), a));
					}
				}

			}
			sentenciaSelectCategorias = conexionbbdd.getConexionMySQL().prepareStatement("SELECT * FROM categorias");
			rs = sentenciaSelectCategorias.executeQuery();
			while (rs.next()) {
				categorias.add(
						new Categoria(String.valueOf(rs.getInt("id_categoria")), rs.getString("nombre_categoria")));

			}
			sentenciaSelectId_CatId_Libro = conexionbbdd.getConexionMySQL().prepareStatement(
					"SELECT id_libro,categorias.id_categoria FROM libros INNER JOIN categorias ON libros.id_categoria = categorias.id_categoria ");
			rs = sentenciaSelectId_CatId_Libro.executeQuery();
			while (rs.next()) {
				for (Categoria cat : categorias) {
					for (Libro l : listaLibros) {
						if (String.valueOf(rs.getInt("id_libro")).equals(l.getId())
								&& String.valueOf(rs.getInt("id_categoria")).equals(cat.getId())) {
							cat.anadirLibro(l);
						}
					}
				}

			}

			System.out.println("Lista de categorias completada");

			return categorias;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
