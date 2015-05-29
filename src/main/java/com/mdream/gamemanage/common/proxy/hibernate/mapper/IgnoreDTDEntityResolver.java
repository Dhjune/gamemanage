package com.mdream.gamemanage.common.proxy.hibernate.mapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringBufferInputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IgnoreDTDEntityResolver implements EntityResolver, Serializable{

	/**
	 * long
	 *TODO
	 */
	private static final long serialVersionUID = -2816709751996973079L;

	public InputSource resolveEntity(String publicId, String systemId)  
			   throws SAXException, IOException {  
			        return new InputSource(  
			        		new StringBufferInputStream(  
			                   "<?xml version='1.0' encoding='UTF-8'?>"  
			    ));  
			 }  

}
