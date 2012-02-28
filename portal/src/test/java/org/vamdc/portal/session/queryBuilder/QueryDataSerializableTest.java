package org.vamdc.portal.session.queryBuilder;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vamdc.portal.session.queryBuilder.forms.AtomsForm;
import org.vamdc.portal.session.queryBuilder.forms.Form;

public class QueryDataSerializableTest {

	private QueryData queryData;
	
	@Before
	public void construct(){
		queryData = new QueryData();
	}
	
	@Test
	public void testSerializeEmpty() throws IOException{
	    serializeObject(queryData);
	}
	
	@Test
	public void testSerializeOneForm() throws IOException, ClassNotFoundException{
		Form iron = new AtomsForm();
		iron.getFields().iterator().next().setValue("Fe");
		queryData.addForm(iron);
		String query = queryData.getQueryString();
		
		byte[] serial = serializeObject(queryData);
		System.out.println("Serialized into "+serial.length+" bytes");
		
		QueryData copy = (QueryData) deSerializeObject(serial);
		
		assertTrue(query.equals(copy.getQueryString()));
		System.out.println("query was "+query+" and was OK");
		
	}

	private byte[] serializeObject(Object object) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream oos = new ObjectOutputStream(out);
	    oos.writeObject(object);
	    oos.close();
	    assertTrue(out.toByteArray().length > 0);
	    return out.toByteArray();
	}
	
	private Object deSerializeObject(byte[] serialized) throws IOException, ClassNotFoundException{
		InputStream in = new ByteArrayInputStream(serialized);
	    ObjectInputStream ois = new ObjectInputStream(in);
	    return ois.readObject();
	}
	
	
}
