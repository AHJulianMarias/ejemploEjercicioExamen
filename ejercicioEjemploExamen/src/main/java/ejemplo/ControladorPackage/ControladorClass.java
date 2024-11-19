package ejemplo.ControladorPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ejemplo.Modelo.Modelo;
import ejemplo.Vista.Ventana;

public class ControladorClass {
	private Modelo modelo;
	private Ventana ventana;

	public ControladorClass(Modelo modelo, Ventana ventana) {
		super();
		this.modelo = modelo;
		this.ventana = ventana;
		ventana.btnExcelBBDD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modelo.introducirDatosBBDD()) {
					System.out.println("Se han introducido los datos en la base de datos correctamente");
				}

			}
		});
		ventana.btnBBDD_XML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelo.persistirMemoria();
			}
		});

	}

}
