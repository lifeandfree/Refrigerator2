package ru.innopolis.refrigerator.core.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecipeCategory", propOrder = {

})
@Entity
@Table(name = "\"RecipeCategory\"")
public class RecipeCategory implements Serializable {

	public RecipeCategory() {
	}

	public RecipeCategory(String name) {
		this.name = name;
	}

	@XmlElement(required = true)
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@XmlElement(required = true)
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RecipeCategory{" + "id=" + id + ", name='" + name + '\'' + '}';
	}
}
