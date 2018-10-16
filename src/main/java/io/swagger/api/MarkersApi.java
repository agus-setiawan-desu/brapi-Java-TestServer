/**
 * NOTE: This class is auto generated by the swagger code generator program (1.0.12-1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.MarkerResponse;
import io.swagger.model.MarkersResponse2;
import io.swagger.annotations.*;

import org.brapi.test.BrAPITestServer.exceptions.BrAPIServerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-04T21:50:05.517Z")

@Api(value = "markers", description = "the markers API")
public interface MarkersApi {

    @ApiOperation(value = "Markers Search (/markers)", nickname = "markersGet", notes = " Scope: CORE.  Status: ACCEPTED. Implemented by: Germinate See <a href=\"#introduction/search-services\">Search Services</a> for additional implementation details. Other service requests use the servers internal `markerDbId`. This service returns marker records that provide the markerDbId. For the requested name or synonym, returns an array (possibly empty) of marker records that match the search criteria. - If there is none, an empty array is returned. - If there is one or more than one match, returns an array of all marker records that match the search criteria. ", response = MarkersResponse2.class, tags={ "Markers","Deprecated", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = MarkersResponse2.class) })
    @RequestMapping(value = "/markers",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<MarkersResponse2> markersGet(@ApiParam(value = "The name or synonym.") @Valid @RequestParam(value = "name", required = false) String name,@ApiParam(value = "Possible values are 'case_insensitive', 'exact' (case sensitive), 'wildcard' (which is case insensitive). Wildcard uses both '*' and '%' for any number of characters and '?' for one character matching. Default is exact.") @Valid @RequestParam(value = "matchMethod", required = false) String matchMethod,@ApiParam(value = "Whether to include synonyms in the output.") @Valid @RequestParam(value = "include", required = false) String include,@ApiParam(value = "The type of the marker.") @Valid @RequestParam(value = "type", required = false) String type,@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,@ApiParam(value = "Which result page is requested. The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page) throws BrAPIServerException;


    @ApiOperation(value = "Marker Details by id", nickname = "markersMarkerDbIdGet", notes = "<strong>Status</strong>: ACCEPTED  <strong>Implemented By</strong>:", response = MarkerResponse.class, tags={ "Markers", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = MarkerResponse.class) })
    @RequestMapping(value = "/markers/{markerDbId}",
        method = RequestMethod.GET)
    ResponseEntity<MarkerResponse> markersMarkerDbIdGet(@ApiParam(value = "the internal id of the marker",required=true) @PathVariable("markerDbId") String markerDbId);

}
