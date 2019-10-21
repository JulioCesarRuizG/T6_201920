package model.data_structures;

public class Feature {
	private MultiPolygon geometry;
	private Properties properties;
	
	public Feature(MultiPolygon pGeometry, Properties Ppropiedades)
	{
		geometry = pGeometry;
		properties = Ppropiedades;
	}
	
	public Properties darPropiedades()
	{
		return properties;
	}
	public MultiPolygon darMultiPolygon()
	{
		return geometry;
	}
}
