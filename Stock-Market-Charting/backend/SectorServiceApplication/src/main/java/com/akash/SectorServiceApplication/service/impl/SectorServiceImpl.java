package com.akash.SectorServiceApplication.service.impl;

import java.util.List;
import java.util.Optional;

import com.akash.SectorServiceApplication.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.SectorServiceApplication.dao.SectorRepository;
import com.akash.SectorServiceApplication.dto.CompanyDto;
import com.akash.SectorServiceApplication.dto.SectorDto;
import com.akash.SectorServiceApplication.mapper.CompanyMapper;
import com.akash.SectorServiceApplication.mapper.SectorMapper;
import com.akash.SectorServiceApplication.model.Sector;
import com.akash.SectorServiceApplication.service.SectorService;

@Service
public class SectorServiceImpl implements SectorService {
    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private SectorMapper sectorMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public SectorDto save(SectorDto sectorDto)
    {
        Sector sector = sectorMapper.toSector(sectorDto);
        sector = sectorRepository.save(sector);
        return sectorMapper.toSectorDto(sector);
    }

    @Override
    public List<SectorDto> findAll()
    {
        List<Sector> sectors = sectorRepository.findAll();
        return sectorMapper.toSectorDtos(sectors);
    }

    @Override
    public SectorDto findById(String id)
    {
        Optional<Sector> sector = sectorRepository.findById(id);
        if(!sector.isPresent())
            return null;
        return sectorMapper.toSectorDto(sector.get());
    }

    @Override
    public void deleteById(String id) {
        sectorRepository.deleteById(id);
    }

    @Override
    public List<CompanyDto> getCompanies(String id)
    {
        Optional<Sector> sector = sectorRepository.findById(id);
        if(!sector.isPresent())
            return null;
        return companyMapper.toCompanyDtos(sector.get().getCompanies());
    }

    /* Feign Client Service */

    @Override
    public SectorDto addCompanyToSector(String sectorName, CompanyDto companyDto)
    {
        Sector sector = sectorRepository.findByName(sectorName);
        if(sector == null)
            return null;
        sector.getCompanies().add(companyMapper.toCompany(companyDto));
        sector = sectorRepository.save(sector);
        return sectorMapper.toSectorDto(sector);
    }
}
