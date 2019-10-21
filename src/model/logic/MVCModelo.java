package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import model.data_structures.ArbolRN;
import model.data_structures.Feature;
import model.data_structures.Iterador;

/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo<K extends Comparable<K>, V> {
	/**
	 * Atributos del modelo del mundo
	 */
	private ArbolRN arbol;
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 * @throws FileNotFoundException 
	 */
	public MVCModelo() throws FileNotFoundException
	{
		String jsonruta = "./data/bogota_cadastral.json";
		JsonReader jsonreader = null;
		Gson gson = new Gson();
		jsonreader = new JsonReader(new FileReader(jsonruta));
		Feature[] lista = gson.fromJson(jsonruta, Feature[].class);
	}
	
	public void consultarZonaPorId(K llave)
	{
		String valor = (String) arbol.get(llave);
		String[] valores = valor.split(",");
		System.out.println("La información de la zona es: nombre - " + valores[0] + ", perímetro - " + valores[1] + ", area - " + valores[2] + ", número de puntos - " + valores[3] );
	}
	
	public void consultarZonaPorRandoId(K lo, K hi)
	{
		Iterador<K> it = (Iterador<K>) arbol.keys(lo, hi);
		while(it.hasNext())
		{
			K llave =it.Next();
			String valor = (String) arbol.get(llave);
			String[] valores = valor.split(",");
			System.out.println("La información de la zona es: nombre - " + valores[0] + ", perímetro - " + valores[1] + ", area - " + valores[2] + ", número de puntos - " + valores[3] );
		}
	}
}
