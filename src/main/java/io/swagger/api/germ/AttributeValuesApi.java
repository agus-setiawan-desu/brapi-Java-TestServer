/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.18).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.germ;

import io.swagger.model.Model202AcceptedSearchResponse;
import io.swagger.model.germ.GermplasmAttributeValueListResponse;
import io.swagger.model.germ.GermplasmAttributeValueNewRequest;
import io.swagger.model.germ.GermplasmAttributeValueSearchRequest;
import io.swagger.model.germ.GermplasmAttributeValueSingleResponse;
import io.swagger.annotations.*;

import org.brapi.test.BrAPITestServer.exceptions.BrAPIServerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-03-20T16:33:36.513Z[GMT]")
@Api(value = "attributevalues", description = "the attributevalues API")
public interface AttributeValuesApi {

	@ApiOperation(value = "Get the details for a specific Germplasm Attribute", nickname = "attributevaluesAttributeValueDbIdGet", notes = "Get the details for a specific Germplasm Attribute", response = GermplasmAttributeValueSingleResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Germplasm Attribute Values", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = GermplasmAttributeValueSingleResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/attributevalues/{attributeValueDbId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<GermplasmAttributeValueSingleResponse> attributevaluesAttributeValueDbIdGet(
			@ApiParam(value = "The unique id for an attribute value", required = true) @PathVariable("attributeValueDbId") String attributeValueDbId,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

	@ApiOperation(value = "Update an existing Germplasm Attribute Value", nickname = "attributevaluesAttributeValueDbIdPut", notes = "Update an existing Germplasm Attribute Value", response = GermplasmAttributeValueSingleResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Germplasm Attribute Values", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = GermplasmAttributeValueSingleResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/attributevalues/{attributeValueDbId}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<GermplasmAttributeValueSingleResponse> attributevaluesAttributeValueDbIdPut(
			@ApiParam(value = "The unique id for an attribute value", required = true) @PathVariable("attributeValueDbId") String attributeValueDbId,
			@ApiParam(value = "") @Valid @RequestBody GermplasmAttributeValueNewRequest body,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

	@ApiOperation(value = "Get the Germplasm Attribute Values", nickname = "attributevaluesGet", notes = "Get the Germplasm Attribute Values", response = GermplasmAttributeValueListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Germplasm Attribute Values", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = GermplasmAttributeValueListResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/attributevalues", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<GermplasmAttributeValueListResponse> attributevaluesGet(
			@ApiParam(value = "The unique id for an attribute value") @Valid @RequestParam(value = "attributeValueDbId", required = false) String attributeValueDbId,
			@ApiParam(value = "The unique id for an attribute") @Valid @RequestParam(value = "attributeDbId", required = false) String attributeDbId,
			@ApiParam(value = "The human readable name for an attribute") @Valid @RequestParam(value = "attributeName", required = false) String attributeName,
			@ApiParam(value = "Get all attributes associated with this germplasm") @Valid @RequestParam(value = "germplasmDbId", required = false) String germplasmDbId,
			@ApiParam(value = "Search for Germplasm by an external reference") @Valid @RequestParam(value = "externalReferenceID", required = false) String externalReferenceID,
			@ApiParam(value = "Search for Germplasm by an external reference") @Valid @RequestParam(value = "externalReferenceSource", required = false) String externalReferenceSource,
			@ApiParam(value = "Used to request a specific page of data to be returned.  The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

	@ApiOperation(value = "Create new Germplasm Attribute Values", nickname = "attributevaluesPost", notes = "Create new Germplasm Attribute Values", response = GermplasmAttributeValueListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Germplasm Attribute Values", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = GermplasmAttributeValueListResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/attributevalues", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<GermplasmAttributeValueListResponse> attributevaluesPost(
			@ApiParam(value = "") @Valid @RequestBody List<GermplasmAttributeValueNewRequest> body,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

	@ApiOperation(value = "Submit a search request for Germplasm Attribute Values", nickname = "searchAttributevaluesPost", notes = "Search for a set of Germplasm Attribute Values based on some criteria          See Search Services for additional implementation details.", response = GermplasmAttributeValueListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Germplasm Attribute Values", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = GermplasmAttributeValueListResponse.class),
			@ApiResponse(code = 202, message = "Accepted", response = Model202AcceptedSearchResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/search/attributevalues", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<GermplasmAttributeValueListResponse> searchAttributevaluesPost(
			@ApiParam(value = "") @Valid @RequestBody GermplasmAttributeValueSearchRequest body,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

	@ApiOperation(value = "Get the results of a Germplasm Attribute Values search request", nickname = "searchAttributevaluesSearchResultsDbIdGet", notes = "Get the results of a Germplasm Attribute Values search request  See Search Services for additional implementation details.", response = GermplasmAttributeValueListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Germplasm Attribute Values", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = GermplasmAttributeValueListResponse.class),
			@ApiResponse(code = 202, message = "Accepted", response = Model202AcceptedSearchResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = String.class) })
	@RequestMapping(value = "/search/attributevalues/{searchResultsDbId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<GermplasmAttributeValueListResponse> searchAttributevaluesSearchResultsDbIdGet(
			@ApiParam(value = "Permanent unique identifier which references the search results", required = true) @PathVariable("searchResultsDbId") String searchResultsDbId,
			@ApiParam(value = "Used to request a specific page of data to be returned.  The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

}
