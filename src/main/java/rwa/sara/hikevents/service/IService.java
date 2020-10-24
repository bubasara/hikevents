package rwa.sara.hikevents.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rwa.sara.hikevents.exception.ResourceNotFoundException;

@Service
public interface IService<T> {

	public List<T> getAll();
	public Optional<T> get(int id);
	public Optional<T> update(T obj);
	public boolean delete(int id);
	
}
