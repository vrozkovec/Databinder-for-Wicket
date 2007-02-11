/*
 * Databinder: a simple bridge from Wicket to Hibernate
 * Copyright (C) 2006  Nathan Hamblen nathan@technically.us

 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.databinder.components;

import java.io.IOException;

import wicket.WicketRuntimeException;
import wicket.markup.html.form.upload.FileUploadField;
import wicket.model.IModel;

/**
 * Extension of FileUploadField optimized for Blob resources having a stream getter and setter.
 * This upload field can be bound to such an object with a compound property model. Whenever
 * a form is submitted with content for this field, the setter on the bound object with be called
 * with the InputStream of the submitted file. (The getter should always return null.) This 
 * allows for file uploads with no specific code for each upload component.
 * @author Nathan Hamblen
 */
public class StreamUploadField extends FileUploadField {
	/**
	 * Costructor to be used with compound property model.
	 * @param id component id, should resolve to a stream property
	 */
	public StreamUploadField(String id) {
		super(id);
	}

	/**
	 * Constructor to be used with PropertyModel or other model.
	 * @param id component id
	 * @param model should resolve to a stream setter
	 */
	public StreamUploadField(String id, IModel model) {
		super(id, model);
	}

	/**
	 * Sets the uploads inputstream to the resolved model object.
	 */
	@Override
	public void updateModel() {
		try {
			if (getFileUpload() != null)
				setModelObject(getFileUpload().getInputStream());
		} catch (IOException e) {
			throw new WicketRuntimeException("Error saving stream to object:", e);
		}
	}
}