package com.ea.shop.rest;

import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.service.CampaignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping("/campaigns")
@RestController
@Api(value = "campaignController")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Campaign by Search Criteria", response = Iterable.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
    ResponseEntity<Page<CampaignDTO>> findCampaign(CampaignDTO campaignDTO, @ApiIgnore Pageable pageable) {
        Page<CampaignDTO> output = campaignService.findCampaign(campaignDTO, pageable);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Campaign by Id", response = Iterable.class)
    ResponseEntity<CampaignDTO> findCampaignById(@PathVariable Long id) {
        CampaignDTO output = campaignService.findCampaignById(id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Save Campaign", response = Iterable.class)
    ResponseEntity<CampaignDTO> save(@RequestBody CampaignDTO campaignDTO) {
        CampaignDTO output = campaignService.save(campaignDTO);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
