package com.spstudio.repository;

import org.springframework.data.repository.CrudRepository;

import com.spstudio.entity.Marker;

public interface TravelMarkerRepository extends CrudRepository<Marker, Integer> {

}
