package rwa.sara.hikevents.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import rwa.sara.hikevents.model.EventsSearchOptions;
import rwa.sara.hikevents.model.entity.EventEntity;
import rwa.sara.hikevents.model.entity.EventEntity_;

public class EventsSearchSpecification implements Specification<EventEntity> {
	private static final long serialVersionUID = 191695976266556755L;

	private final EventsSearchOptions eventSearchOptions;
	
	@Autowired
	public EventsSearchSpecification(EventsSearchOptions eventSearchOptions) {
		this.eventSearchOptions = eventSearchOptions;
	}

	@Override
	public Predicate toPredicate(Root<EventEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		final List<Predicate> predicates = new ArrayList<>();
		final Path<String> title = root.get(EventEntity_.getTitle());
		final Path<String> location = root.get(EventEntity_.getLocation());
		
		final String titleFilter = eventSearchOptions.getTitleFilter();
		if (titleFilter != null && !titleFilter.trim().isEmpty()){
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(title), "%" + titleFilter.toLowerCase() + "%"));
		}
		
		final String locationFilter = eventSearchOptions.getLocationFilter();
		if (locationFilter != null && !locationFilter.trim().isEmpty()){
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(location), "%" + locationFilter.toLowerCase() + "%"));
		}
		
		return null;
	}

}
