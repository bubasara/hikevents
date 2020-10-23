package rwa.sara.hikevents.model.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

//used for search specification
@StaticMetamodel(EventEntity.class)
public class EventEntity_ {

	public static volatile SingularAttribute<EventEntity, String> title;
	public static volatile SingularAttribute<EventEntity, String> location;
	
	public static SingularAttribute<EventEntity, String> getTitle() {
		return title;
	}

	public static SingularAttribute<EventEntity, String> getLocation() {
		return location;
	}
}
