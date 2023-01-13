package com.ardev.idroid.ui.main.model;

import java.util.Objects;
import java.io.File;
import java.io.Serializable;
import java.util.UUID;

public class Editable implements Serializable {
	private String id;
	private File file;
	private boolean isPreview;
	
	public Editable( File file, boolean isPreview) {
	this.file = file;
	this.isPreview = isPreview;
	this.id = UUID.nameUUIDFromBytes(("id_" + file.getPath() + (this.isPreview ? "0" : "1")).getBytes()).toString();
	}
	
	public Editable( File file) {
	this.file = file;
this.id = UUID.nameUUIDFromBytes(("id_" + file.getPath() + (this.isPreview ? "0" : "1")).getBytes()).toString();

	}
	
	public String getId() {
	return id;
	}
	

	public File getFile() {
	return file;
	}

	public boolean isPreview() {
	return isPreview;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Editable editable = (Editable) o;
    
        return Objects.equals(editable.getId(), getId()) && Objects.equals(editable.getFile(), getFile()) && Objects.equals(editable.isPreview(), isPreview());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, file, isPreview);
    }
}