/**
 * NOTE: This class is auto generated by the swagger code generator program (1.0.12-1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.StudiesResponse;
import io.swagger.model.StudySearchRequest;
import io.swagger.annotations.*;

import org.brapi.test.BrAPITestServer.exceptions.BrAPIServerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import java.util.ArrayList;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-04T21:50:05.517Z")

@Api(value = "studies-search", description = "the studies-search API")
public interface StudiesSearchApi {

    @ApiOperation(value = "Search Studies (GET)", nickname = "studiesSearchGet", notes = " Scope: PHENOTYPING. Status: ACCEPTED. Implementation target date: PAG2016. Implemented by: Germinate Used by: Flapjack, Cassavabase See <a href=\"#introduction/search-services\">Search Services</a> for additional implementation details. Get list of studies StartDate and endDate should be ISO8601 format for dates: YYYY-MM-DD <a href=\"https://test-server.brapi.org/brapi/v1/studies\"> test-server.brapi.org/brapi/v1/studies-search</a>", response = StudiesResponse.class, tags={ "Studies","Search Services", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = StudiesResponse.class) })
    @RequestMapping(value = "/studies-search",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<StudiesResponse> studiesSearchGet(@ApiParam(value = "Filter based on study type e.g. Nursery, Trial or Genotype.") @Valid @RequestParam(value = "studyType", required = false) String studyType,@ApiParam(value = "Program filter to only return studies associated with given program id.") @Valid @RequestParam(value = "programDbId", required = false) String programDbId,@ApiParam(value = "Filter by location") @Valid @RequestParam(value = "locationDbId", required = false) String locationDbId,@ApiParam(value = "Filter by season or year") @Valid @RequestParam(value = "seasonDbId", required = false) String seasonDbId,@ApiParam(value = "Filter by trial") @Valid @RequestParam(value = "trialDbId", required = false) String trialDbId,@ApiParam(value = "Filter by study DbId") @Valid @RequestParam(value = "studyDbId", required = false) String studyDbId,@ApiParam(value = "Filter studies where specified germplasm have been used/tested") @Valid @RequestParam(value = "germplasmDbIds", required = false) ArrayList<String> germplasmDbIds,@ApiParam(value = "Filter studies where specified observation variables have been measured") @Valid @RequestParam(value = "observationVariableDbIds", required = false) ArrayList<String> observationVariableDbIds,@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,@ApiParam(value = "Which result page is requested. The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Filter active status true/false.") @Valid @RequestParam(value = "active", required = false) Boolean active,@ApiParam(value = "Sort order. Name of the field to sort by.") @Valid @RequestParam(value = "sortBy", required = false) String sortBy,@ApiParam(value = "Sort order direction. Ascending/Descending.") @Valid @RequestParam(value = "sortOrder", required = false) String sortOrder) throws BrAPIServerException;


    @ApiOperation(value = "Search Studies (GET)", nickname = "studiesSearchPost", notes = " Scope: PHENOTYPING. Status: ACCEPTED. Implementation target date: PAG2016. Implemented by: Germinate Used by: Flapjack, Cassavabase See <a href=\"#introduction/search-services\">Search Services</a> for additional implementation details. Get list of studies StartDate and endDate should be ISO8601 format for dates: YYYY-MM-DD <a href=\"https://test-server.brapi.org/brapi/v1/studies\"> test-server.brapi.org/brapi/v1/studies-search</a>", response = StudiesResponse.class, tags={ "Studies","Search Services", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = StudiesResponse.class) })
    @RequestMapping(value = "/studies-search",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<StudiesResponse> studiesSearchPost(@ApiParam(value = "Study Search request"  )  @Valid @RequestBody StudySearchRequest studySearchRequest) throws BrAPIServerException;

}
