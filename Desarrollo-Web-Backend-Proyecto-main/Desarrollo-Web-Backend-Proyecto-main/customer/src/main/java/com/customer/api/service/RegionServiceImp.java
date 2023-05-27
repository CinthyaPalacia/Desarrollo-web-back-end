package com.customer.api.service;

import com.customer.api.dto.ResponseApi;
import com.customer.api.entity.Region;
import com.customer.api.repository.RegionRepository;
import com.customer.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service public class RegionServiceImp implements RegionService
{

    @Resource
    RegionRepository regionRepository;

    @Override
    public List<Region> getRegions()
    {
        return regionRepository.findByStatus(1);
    }

    @Override
    public Region getRegion(Integer region_id)
    {
        Region region = regionRepository.findByRegionId(region_id);
        if (region == null)
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "region does not exist");
        }
        else
        {
            return region;
        }
    }

    @Override
    public ResponseApi createRegion(Region region)
    {
        Region regionSaved = regionRepository.findByRegion(region.getRegion());
        if (regionSaved != null)
        {
            if (regionSaved.getStatus() == 0)
            {
                regionRepository.activateRegion(regionSaved.getRegion_id());
                return new ResponseApi("region has been activated");
            }
            else
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "region alredy exists");
            }
        }
        regionRepository.createRegion(region.getRegion());
        return new ResponseApi("region created");
    }

    @Override
    public ResponseApi updateRegion(Integer region_id, Region region)
    {
        Region regionSaved = regionRepository.findByRegionId(region_id);
        if (regionSaved == null)
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "region does not exist");
        }
        else
        {
            if (regionSaved.getStatus() == 0)
            {
                throw new ExceptionApi(HttpStatus.BAD_REQUEST, "region is not active");
            }
            else
            {
                regionSaved = regionRepository.findByRegion(region.getRegion());
                if (regionSaved != null)
                {
                    throw new ExceptionApi(HttpStatus.BAD_REQUEST, "region alredy exists");
                }
                regionRepository.updateRegion(region_id, region.getRegion());
                return new ResponseApi("region updated");
            }
        }
    }

    @Override
    public ResponseApi deleteRegion(Integer region_id)
    {
        Region regionSaved = regionRepository.findByRegionId(region_id);
        if (regionSaved == null)
        {
            throw new ExceptionApi(HttpStatus.NOT_FOUND, "region does not exist");
        }
        else
        {
            regionRepository.deleteById(region_id);
            return new ResponseApi("region removed");
        }
    }

}
