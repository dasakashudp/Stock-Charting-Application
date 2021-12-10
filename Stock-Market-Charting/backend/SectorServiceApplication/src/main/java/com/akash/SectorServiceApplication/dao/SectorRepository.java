package com.akash.SectorServiceApplication.dao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.akash.SectorServiceApplication.model.Sector;

@Repository
public interface SectorRepository extends MongoRepository<Sector, String>
{
    public Sector findByName(String sectorName);
}
