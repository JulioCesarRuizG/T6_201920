package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import com.google.gson.JsonObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model.data_structures.ArbolRN;
import model.data_structures.Iterador;
import model.data_structures.Properties;

/**
 * Definicion del modelo del mundo
 */

public class MVCModelo<K extends Comparable<K>, V> {
	/**
	 * Atributos del modelo del mundo
	 */
	private ArbolRN<K,V> arbol;
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 * @throws FileNotFoundException 
	 */
	public MVCModelo() throws FileNotFoundException
	{
//		cargaJson();
		cargaProvisional();
	}
	
	public void cargaJson()
	{
		try
		{
			String jsonruta = "./data/bogota_cadastral.json";
			FileReader read = new FileReader(jsonruta);
			JSONParser parser = new JSONParser();
			JSONArray ar = (JSONArray) parser.parse(read);
			for(Object o : ar)
			{
				JSONObject ob = (JSONObject) o;
				JsonObject ob2 = (JsonObject) o;
				JSONObject multi = (JSONObject) ob.get("geometry");
				JSONArray coor1 = (JSONArray) multi.get("coordinates");
				for(Object niv1 : coor1)
				{
					JSONArray coor2 = (JSONArray) niv1;
					for(Object niv2 : coor2)
					{
						JSONArray coor3 = (JSONArray) niv2;
						String latandlon = coor3.get(0).toString();
						String[] sep = latandlon.split(",");
						double lon = Double.parseDouble(sep[0]);
						double lat = Double.parseDouble(sep[1]);
					}
				}
				
				JsonObject props = ob2.get("properties").getAsJsonObject();
				int cartodb_id = Integer.parseInt(props.get("cartodb_id").toString());
				String scacodigo = props.get("scacodigo").toString();
				int scatipo = Integer.parseInt(props.get("scatipo").toString());
				String scanombre = props.get("scanombre").toString();
				double shape_leng = Double.parseDouble(props.get("shape_leng").toString());
				double shape_area = Double.parseDouble(props.get("shape_area").toString());
				int MOVEMENT_ID = Integer.parseInt(props.get("MOVEMENT_ID").toString());
				String DISPLAY_NAME = props.get("DISPLAY_NAME").toString();
				
				Properties p = new Properties(cartodb_id, scacodigo, scatipo, scanombre, shape_leng, MOVEMENT_ID, DISPLAY_NAME);
				System.out.println("Se ha cargado correctamente el archivo json");
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargaProvisional()
	{
		try
		{
			String ruta = "./data/bogota_cadastral2.txt";
			FileReader reader = new FileReader(ruta);
			BufferedReader bf = new BufferedReader(reader);
			String linea = bf.readLine();
			while(linea != null)
			{
//				if(linea.contains("properties"))
//				{
//					linea.
//				}
//				linea = bf.readLine()
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void consultarZonaPorId(K llave)
	{
		String valor = (String) arbol.get(llave);
		String[] valores = valor.split(",");
		System.out.println("La información de la zona es: nombre - " + valores[0] + ", perímetro - " + valores[1] + ", area - " + valores[2] + ", número de puntos - " + valores[3] );
	}
	
	public void consultarZonaPorRandoId(K lo, K hi)
	{
		Iterator<K> it = arbol.keys(lo, hi).iterator();
		while(it.hasNext())
		{
			K llave =it.next();
			String valor = (String) arbol.get(llave);
			String[] valores = valor.split(",");
			System.out.println("La información de la zona es: nombre - " + valores[0] + ", perímetro - " + valores[1] + ", area - " + valores[2] + ", número de puntos - " + valores[3] );
		}
	}
}