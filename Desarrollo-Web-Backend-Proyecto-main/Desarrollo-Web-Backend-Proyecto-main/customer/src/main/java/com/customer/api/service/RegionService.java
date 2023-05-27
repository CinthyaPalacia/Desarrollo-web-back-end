package com.customer.api.service;

import com.customer.api.dto.ResponseApi;
import com.customer.api.entity.Region;

import java.util.List;

public interface RegionService
{
	List<Region> getRegions() throws Exception;
	Region getRegion(Integer region_id);
	ResponseApi createRegion(Region region);
	ResponseApi updateRegion(Integer region_id, Region region);
	ResponseApi deleteRegion(Integer region_id);
}
