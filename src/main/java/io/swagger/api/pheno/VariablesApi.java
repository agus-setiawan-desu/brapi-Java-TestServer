/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.18).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.pheno;

import io.swagger.model.pheno.ObservationVariableListResponse;
import io.swagger.model.pheno.ObservationVariableNewRequest;
import io.swagger.model.pheno.ObservationVariableSingleResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-03-20T16:32:22.556Z[GMT]")
@Api(value = "variables", description = "the variables API")
public interface VariablesApi {

	@ApiOperation(value = "Get the Observation Variables", nickname = "variablesGet", notes = "Call to retrieve a list of observationVariables available in the system.", response = ObservationVariableListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Observation Variables", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ObservationVariableListResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/variables", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<ObservationVariableListResponse> variablesGet(
			@ApiParam(value = "Variable's unique ID") @Valid @RequestParam(value = "observationVariableDbId", required = false) String observationVariableDbId,
			@ApiParam(value = "Variable's trait class (phenological, physiological, morphological, etc.)") @Valid @RequestParam(value = "traitClass", required = false) String traitClass,
			@ApiParam(value = "The unique ID of a studies to filter on") @Valid @RequestParam(value = "studyDbId", required = false) String studyDbId,
			@ApiParam(value = "Search for Germplasm by an external reference") @Valid @RequestParam(value = "externalReferenceID", required = false) String externalReferenceID,
			@ApiParam(value = "Search for Germplasm by an external reference") @Valid @RequestParam(value = "externalReferenceSource", required = false) String externalReferenceSource,
			@ApiParam(value = "Used to request a specific page of data to be returned.  The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization);

	@ApiOperation(value = "Get the details for a specific Observation Variable", nickname = "variablesObservationVariableDbIdGet", notes = "Retrieve variable details", response = ObservationVariableSingleResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Observation Variables", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = ObservationVariableSingleResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = String.class) })
	@RequestMapping(value = "/variables/{observationVariableDbId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<ObservationVariableSingleResponse> variablesObservationVariableDbIdGet(
			@ApiParam(value = "string id of the variable", required = true) @PathVariable("observationVariableDbId") String observationVariableDbId,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization);

	@ApiOperation(value = "Update an existing Observation Variable", nickname = "variablesObservationVariableDbIdPut", notes = "Update an existing Observation Variable", response = ObservationVariableSingleResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Observation Variables", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = ObservationVariableSingleResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = String.class) })
	@RequestMapping(value = "/variables/{observationVariableDbId}", produces = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<ObservationVariableSingleResponse> variablesObservationVariableDbIdPut(
			@ApiParam(value = "string id of the variable", required = true) @PathVariable("observationVariableDbId") String observationVariableDbId,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization);

	@ApiOperation(value = "Add new Observation Variables", nickname = "variablesPost", notes = "Add new Observation Variables to the system.", response = ObservationVariableListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Observation Variables", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ObservationVariableListResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/variables", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<ObservationVariableListResponse> variablesPost(
			@ApiParam(value = "") @Valid @RequestBody List<ObservationVariableNewRequest> body,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization);

}