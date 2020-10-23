package rwa.sara.hikevents.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rwa.sara.hikevents.exception.DuplicateResourceException;
import rwa.sara.hikevents.exception.ResourceNotFoundException;
import rwa.sara.hikevents.exception.ResourceType;
import rwa.sara.hikevents.model.EventsSearchOptions;
import rwa.sara.hikevents.model.entity.EventEntity;
import rwa.sara.hikevents.model.entity.UserEntity;
import rwa.sara.hikevents.repository.EventRepository;
import rwa.sara.hikevents.service.IService;
import rwa.sara.hikevents.specification.EventsSearchSpecification;

@Service
public class EventService implements IService<EventEntity>{

	public final EventRepository eventRepository;

	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public List<EventEntity> searchEvents(final EventsSearchOptions eventSearchOptions){
		EventsSearchSpecification eventSearchSpecification = new EventsSearchSpecification(eventSearchOptions);
		
		return eventRepository.findAll(eventSearchSpecification);
	}
	
	public Optional<EventEntity> findByTitle(String title){
		return eventRepository.findByTitle(title);
	}
	
	public Optional<List<EventEntity>> findByLocation(String location){
		return eventRepository.findByLocation(location);
	}
	
	public Optional<List<EventEntity>> findByStartDate(Date startDate){
		return eventRepository.findByStartDate(startDate);
	}
	
	public List<EventEntity> findByHost(UserEntity host){
		return eventRepository.findByHost(host);
	}
	
	public Optional<List<EventEntity>> findByPriceLessThan(int price){
		return eventRepository.findByPriceLessThan(price);
	}
	
	public Optional<EventEntity> insert(EventEntity eventEntity) throws DuplicateResourceException {
		if(!eventRepository.findByTitle(eventEntity.getTitle()).isPresent()) {
			throw new DuplicateResourceException(ResourceType.EVENT, "Event with title: " + eventEntity.getTitle() + " already exists.");
		} else {
			return Optional.of(eventRepository.save(eventEntity));
		}
	}
	
	@Override
	public List<EventEntity> getAll() {
		return eventRepository.findAll();
	}

	@Override
	public Optional<EventEntity> get(int id) throws ResourceNotFoundException {
		return Optional.of(eventRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(ResourceType.EVENT, "Event with id: " + id + " not found.")));
	}

	@Override
	public Optional<EventEntity> update(EventEntity eventEntity) throws ResourceNotFoundException {
		Optional.of(eventRepository.findById(eventEntity.getId()).orElseThrow(
				() -> new ResourceNotFoundException(ResourceType.EVENT, "Event with id: " + eventEntity.getId() + " not found.")));
		return Optional.of(eventRepository.save(eventEntity));
	}

	@Override
	public boolean delete(int id) throws ResourceNotFoundException {
		if(!eventRepository.existsById(id)) {
			throw new ResourceNotFoundException(ResourceType.EVENT, "Event with id: " + id + " not found.");
		} else {
			eventRepository.deleteById(id);
			return true;
		}
	}

}
