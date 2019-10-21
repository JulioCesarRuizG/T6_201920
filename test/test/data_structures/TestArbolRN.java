package test.data_structures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.ArbolRN;
import model.data_structures.Iterador;

public class TestArbolRN<K extends Comparable<K>, V> {
	
	private ArbolRN arbol;
	@Before
	public void setUp1()
	{
		arbol = new ArbolRN<K, V>();
		arbol.put(1, "uno");
		arbol.put(2, "dos");
		arbol.put(3, "tres");
		arbol.put(4, "cuatro");
		arbol.put(5, "cinco");
	}
	
	@Test
	public void TestContains()
	{
		setUp1();
		try
		{
			assertEquals("Debería encontrar la llave", true, arbol.contains(5));
		}
		catch (Exception e) {
			fail("Error al ejecutar el método contains");
		}
	}
	@Test
	public void TestDeleteMin()
	{
		setUp1();
		try
		{
			arbol.deleteMin();
			assertEquals("Debería eliminar la llave 1", false, arbol.contains(1));
		}
		catch (Exception e) {
			fail("Error al ejecutar el método deleteMin");
		}
	}
	@Test
	public void TestDeleteMax()
	{
		setUp1();
		try
		{
			arbol.deleteMax();
			assertEquals("Debería eliminar la llave 5", false, arbol.contains(5));
		}
		catch (Exception e) {
			fail("Error al ejecutar el método deleteMax");
		}
	}
	@Test
	public void TestDelete()
	{
		setUp1();
		try
		{
			arbol.delete(5);
			assertEquals("Debería eliminar la llave 5", false, arbol.contains(5));
		}
		catch (Exception e) {
			fail("Error al ejecutar el método delete");
		}
	}
	@Test
	public void TestHeight()
	{
		setUp1();
		try
		{
			assertEquals("No es el valor adecuado", 2, arbol.height());
		}
		catch (Exception e) {
			fail("Error al ejecutar el método height");
		}
	}
	@Test
	public void TestSize()
	{
		setUp1();
		try
		{
			assertEquals("No es el valor adecuado", 5, arbol.size(1,5));
		}
		catch (Exception e) {
			fail("Error al ejecutar el método size");
		}
	}
	@Test
	public void TestKeys()
	{
		setUp1();
		try
		{
			Iterador<K> it =(Iterador<K>) arbol.keys(1,5);
			assertEquals("No es el elemento esperado", 1, it.Next());
		}
		catch (Exception e) {
			fail("Error al ejecutar el método keys");
		}
	}
	
}
