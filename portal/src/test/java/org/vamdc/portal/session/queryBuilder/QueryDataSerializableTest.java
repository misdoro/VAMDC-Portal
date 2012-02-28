package org.vamdc.portal.session.queryBuilder;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vamdc.portal.session.queryBuilder.fields.AbstractField;
import org.vamdc.portal.session.queryBuilder.fields.RangeField;
import org.vamdc.portal.session.queryBuilder.forms.AtomsForm;
import org.vamdc.portal.session.queryBuilder.forms.CollisionsForm;
import org.vamdc.portal.session.queryBuilder.forms.CollisionsForm.SpeciesFacade;
import org.vamdc.portal.session.queryBuilder.forms.Form;
import org.vamdc.portal.session.queryBuilder.forms.MoleculesForm;
import org.vamdc.portal.session.queryBuilder.forms.ParticlesForm;
import org.vamdc.portal.session.queryBuilder.forms.TransitionsForm;

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
	public void testSerializeAtomsForm() throws IOException, ClassNotFoundException{
		addAtomsForm("Fe");
		veryfyCopyQueryEquality();
	}
	
	@Test
	public void testSerializeMoleculesForm() throws IOException, ClassNotFoundException{
		Form form = new MoleculesForm();
		form.getFields().iterator().next().setValue("Carbon monoxide");
		queryData.addForm(form);
		
		veryfyCopyQueryEquality();
		
	}
	
	@Test
	public void testSerializeParticlesForm() throws IOException, ClassNotFoundException{
		Form form = new ParticlesForm();
		form.getFields().iterator().next().setValue("electron");
		queryData.addForm(form);
		
		veryfyCopyQueryEquality();	
	}
	
	@Test
	public void testSerializeTransitionForm() throws IOException, ClassNotFoundException{
		addTransitionsForm();
		
		veryfyCopyQueryEquality();	
	}
	
	@Test
	public void testSerializeMultiSpeciesTransition() throws IOException, ClassNotFoundException{
		addTransitionsForm();
		
		addAtomsForm("Fe");
		addAtomsForm("Co");
		addAtomsForm("Ni");
		
		veryfyCopyQueryEquality();
	}
	
	@Test
	public void testSerializeCollisionsForm() throws IOException, ClassNotFoundException{
		addCollisionsForm();

		veryfyCopyQueryEquality();	
	}
	
	@Test
	public void testSerializeMultiSpeciesCollisions() throws IOException, ClassNotFoundException{
		CollisionsForm form = addCollisionsForm();
		
		addAtomsForm("Fe");
		addAtomsForm("Ni");
		addAtomsForm("Co");

		int i=0;
		for (SpeciesFacade face:form.getSpecies()){
			face.setRole("reactant"+(i++));
		}
		
		veryfyCopyQueryEquality();	
	}

	private void addAtomsForm(String element) {
		Form form = new AtomsForm();
		form.getFields().iterator().next().setValue(element);
		queryData.addForm(form);
	}
	
	private CollisionsForm addCollisionsForm() {
		CollisionsForm form = new CollisionsForm();
		Iterator<AbstractField> fields = form.getFields().iterator();
		fields.next();
		fields.next();
		fields.next().setValue("inel");
		queryData.addForm(form);
		return form;
	}
	
	private void addTransitionsForm() {
		Form form = new TransitionsForm();
		RangeField wavelength = (RangeField) form.getFields().iterator().next();
		wavelength.setHiValue("5010");
		wavelength.setLoValue("5008");
		queryData.addForm(form);
	}

	private void veryfyCopyQueryEquality() throws IOException,
			ClassNotFoundException {
		String query = queryData.getQueryString();
		System.out.println("Initial query: "+query);
		
		byte[] serial = serializeObject(queryData);
		System.out.println("Serialized into "+serial.length+" bytes");
		
		QueryData copy = (QueryData) deSerializeObject(serial);
		
		System.out.print("Copy query: "+copy.getQueryString());
		
		assertTrue(query.equals(copy.getQueryString()));
		System.out.println(" OK");
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
	
	@After
	public void clearTest(){
		queryData = null;
	}
}
