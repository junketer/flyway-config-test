package com.oag.wdf.master.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement(name = "LDL")
public class DupLegSet {

	private static JAXBContext jaxbContext;

	private static Marshaller jaxBMarshaller;

	static {
		try {
			jaxbContext = JAXBContext.newInstance(DupLegSet.class);
			jaxBMarshaller = jaxbContext.createMarshaller();

			// jaxBMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxBMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Set<DupLeg> dupLegSet;

	public DupLegSet() {
		this.dupLegSet = new TreeSet<DupLeg>();
	}

	public Set<DupLeg> getDupLegSet() {
		return dupLegSet;
	}

	@XmlElement(name = "DL")
	public void setDupLegSet(Set<DupLeg> dupLegSet) {
		this.dupLegSet = dupLegSet;
	}

	public String asString() throws JAXBException {

		StringWriter sw = new StringWriter();

		synchronized (jaxBMarshaller) {
			jaxBMarshaller.marshal(this, sw);
		}
		return sw.toString();
	}

}
