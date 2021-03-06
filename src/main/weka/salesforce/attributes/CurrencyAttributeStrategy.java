package main.weka.salesforce.attributes;
import weka.core.Attribute;

import com.sforce.soap.partner.Field;

public class CurrencyAttributeStrategy extends AttributeStrategy{
	
	public CurrencyAttributeStrategy(Field f, int i) {
		super(f, i);
	}
	
	@Override
	public Attribute buildAttribute() {
		return new Attribute( sField.getName(), this.getIndex() );
	}
}