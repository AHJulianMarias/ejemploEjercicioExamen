package testPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Utilidades.Utilidades;
import ejemplo.Modelo.libro;

public class testExcel {
	public final static String FICHEROTRABAJO = "DatosBiblioteca_Expandido.xlsx";
	private static int COLUMNA_TITULO = 0;
	private static int COLUMNA_NOMBRE = 1;
	private static int COLUMNA_PAIS_AUTOR = 2;
	private static int COLUMNA_CATEGORIA = 3;

	public static void main(String[] args) {
		System.out.println("Recogiendo datos no duplicados");
//		HashSet<String> categorias = devolverNoDuplicadas(COLUMNA_CATEGORIA);
//		HashSet<String> titulos = devolverNoDuplicadas(COLUMNA_TITULO);
//		HashSet<String> paises = devolverNoDuplicadas(COLUMNA_PAIS_AUTOR);
//		HashSet<String> nombres = devolverNoDuplicadas(COLUMNA_NOMBRE);

		ArrayList<libro> listaLibro = devolverLibros();
		System.out.println(listaLibro);
//		if (categorias != null) {
//			System.out.println("Categorias");
//			System.out.println(categorias);
//		} else {
//			System.out.println("Error cogiendo categorias");
//		}
//		if (titulos != null) {
//			System.out.println("titulos");
//			System.out.println(titulos);
//		} else {
//			System.out.println("Error cogiendo titulos");
//		}
//		if (paises != null) {
//			System.out.println("paises");
//			System.out.println(paises);
//		} else {
//			System.out.println("Error cogiendo paises");
//		}
//		if (nombres != null) {
//			System.out.println("nombres");
//			System.out.println(nombres);
//		} else {
//			System.out.println("Error cogiendo nombres");
//		}

	}

	private static HashMap<String, String> devolverNoDuplicadasMapa(int numeroColumnaNombre, int numeroColumnaPais) {
		// declaras el workbook para que poder usar el excel
		Workbook wb;
		try {
			// declaras cual es el excel
			wb = new XSSFWorkbook(new FileInputStream(new File(Utilidades.RUTADATOS + FICHEROTRABAJO)));
			// declaras el hashset que vas a devolver

			HashMap<String, String> elementosNoDuplicados = new HashMap<String, String>();

			// nos colocamos en la primera hoja
			Sheet hoja1 = wb.getSheetAt(0);
			int numFila = 1;
			// cogemos la primera fila
			Row fila = hoja1.getRow(numFila);
			while (fila != null) {
				// bucle mientras fila sea diferente a nulo
				// numero de columna se pasa por parametro para no tener que crear 4 metodos
				// diferentes
				if (fila.getCell(numeroColumnaNombre) != null) {
					elementosNoDuplicados.put(fila.getCell(numeroColumnaNombre).toString(),
							fila.getCell(numeroColumnaPais).toString());
				}
				// pasamos a la siguiente fila
				fila = hoja1.getRow(numFila++);

			}
			// cerramos
			wb.close();
			if (elementosNoDuplicados.size() > 0) {
				// retornamos el hashset
				return elementosNoDuplicados;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
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

	private static HashSet<String> devolverNoDuplicadas(int numeroColumna) {
		// declaras el workbook para que poder usar el excel
		Workbook wb;
		try {
			// declaras cual es el excel
			wb = new XSSFWorkbook(new FileInputStream(new File(Utilidades.RUTADATOS + FICHEROTRABAJO)));
			// declaras el hashset que vas a devolver

			HashSet<String> elementosNoDuplicados = new HashSet<String>();

			// nos colocamos en la primera hoja
			Sheet hoja1 = wb.getSheetAt(0);
			int numFila = 1;
			// cogemos la primera fila
			Row fila = hoja1.getRow(numFila);
			while (fila != null) {
				// bucle mientras fila sea diferente a nulo
				// numero de columna se pasa por parametro para no tener que crear 4 metodos
				// diferentes
				if (fila.getCell(numeroColumna) != null) {
					elementosNoDuplicados.add(fila.getCell(numeroColumna).toString());
				}
				// pasamos a la siguiente fila
				fila = hoja1.getRow(numFila++);

			}
			// cerramos
			wb.close();
			if (elementosNoDuplicados.size() > 0) {
				// retornamos el hashset
				return elementosNoDuplicados;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
