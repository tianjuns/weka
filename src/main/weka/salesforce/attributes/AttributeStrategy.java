package main.weka.salesforce.attributes;

import java.util.Enumeration;

import weka.core.Attribute;
import weka.core.FastVector;

import com.sforce.soap.partner.Field;
import com.sforce.ws.ConnectionException;

public abstract class AttributeStrategy {
	public int m_Index = -1;
	
	protected Field sField;
	public AttributeStrategy(Field f, int i) {
		sField = f;
		m_Index = i;
		this.setAttribute( this.buildAttribute() );
	}
	
	public Field getField(){
		return sField;
	}
	
	private Attribute m_attribute = null;
	public void setAttribute(Attribute attrib){ this.m_attribute = attrib; }
	public Attribute getAttribute(){
		if(this.m_attribute == null){
			this.m_attribute = this.buildAttribute();
		}
		return this.m_attribute; 
	}
	
	public AttributeStrategy withIndex(int i){ m_Index = i; return this; }
	public int getIndex(){ return m_Index; }
	public abstract Attribute buildAttribute();
		
	public Double getNumericValue(Object value){
		return (value == null ? 0.0 : Double.valueOf(value.toString()) );
	}
	
	public String getValue(Object value){
		return (value == null ? "?" : (String)value);
	};
	
	public boolean containsValue(Object value) throws ConnectionException{
		if(value == null || value.equals("")){
			return true;
		}
		
		Enumeration values = this.getAttribute().enumerateValues();
		while (values.hasMoreElements()){
			String s = (String) values.nextElement();
			if( s.toLowerCase().equals(value.toString().toLowerCase() )){
				return true;
			}
		}
		return false;
	}
	
	public Attribute appendNominalValue(String value) throws ConnectionException{
		Enumeration values = this.getAttribute().enumerateValues();
		FastVector attributeValues = new FastVector();
		while (values.hasMoreElements()){
			attributeValues.addElement( (String) values.nextElement() );
		}
		attributeValues.addElement( value );
		
		Attribute newAttrib = new Attribute( this.getAttribute().name(), attributeValues, this.getAttribute().index() );
		this.setAttribute(newAttrib);
		return newAttrib;
	}
}