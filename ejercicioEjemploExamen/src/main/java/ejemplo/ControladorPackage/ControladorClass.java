package ejemplo.ControladorPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ejemplo.Modelo.Modelo;
import ejemplo.Vista.Ventana;

public class ControladorClass {
	private Modelo modelo;
	private Ventana ventana;
	private boolean conectado;

	public ControladorClass(Modelo modelo, Ventana ventana) {
		super();
		this.modelo = modelo;
		this.ventana = ventana;
		this.conectado = modelo.conectadoABBDD();
		iniciarVentanas();
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

	private void iniciarVentanas() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (this.conectado) {
			this.ventana.lblNewLabel.setText("Te has conectado a la base de datos");

		} else {
			this.ventana.lblNewLabel.setText("No te has conectado a la base de datos");
		}

	}

}
