package com.customer.api.controller;

import com.customer.api.dto.ResponseApi;
import com.customer.api.entity.Region;
import com.customer.api.service.RegionService;
import com.customer.exception.ExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/region") public class RegionController
{

    @Resource
    RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> getRegions() throws Exception
    {
        return new ResponseEntity<>(regionService.getRegions(), HttpStatus.OK);
    }

    @GetMapping("/{region_id}")
    public ResponseEntity<Region> getRegion(@PathVariable int region_id)
    {
        return new ResponseEntity<>(regionService.getRegion(region_id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseApi> createRegion(@Valid @RequestBody Region region, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(regionService.createRegion(region), HttpStatus.OK);
    }

    @PutMapping("/{region_id}")
    public ResponseEntity<ResponseApi> updateRegion(
            @PathVariable int region_id, @Valid @RequestBody Region region, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(regionService.updateRegion(region_id, region), HttpStatus.OK);
    }

    @DeleteMapping("/{region_id}")
    public ResponseEntity<ResponseApi> deleteRegion(@PathVariable int region_id)
    {
        return new ResponseEntity<>(regionService.deleteRegion(region_id), HttpStatus.OK);
    }
}
