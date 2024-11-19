package ejemplo;

import ejemplo.ControladorPackage.ControladorClass;
import ejemplo.Modelo.Modelo;
import ejemplo.Vista.Ventana;

public class app {

	public static void main(String[] args) {

		ControladorClass controlador = new ControladorClass(new Modelo(), new Ventana());

	}

}
