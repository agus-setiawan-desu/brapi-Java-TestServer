/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.18).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.geno;

import io.swagger.model.Model202AcceptedSearchResponse;
import io.swagger.model.geno.ReferenceSetsListResponse;
import io.swagger.model.geno.ReferenceSetsSearchRequest;
import io.swagger.model.geno.ReferenceSetsSingleResponse;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-03-20T16:32:53.794Z[GMT]")
@Api(value = "referencesets", description = "the referencesets API")
public interface ReferenceSetsApi {

	@ApiOperation(value = "Gets a list of `ReferenceSets`.", nickname = "referencesetsGet", notes = "Gets a filtered list of `ReferenceSets`.", response = ReferenceSetsListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Reference Sets", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ReferenceSetsListResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/referencesets", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<ReferenceSetsListResponse> referenceSetsGet(
			@ApiParam(value = "The ID of the `ReferenceSet` to be retrieved.") @Valid @RequestParam(value = "referenceSetDbId", required = false) String referenceSetDbId,
			@ApiParam(value = "If set, return the reference sets for which the `accession` matches this string (case-sensitive, exact match).") @Valid @RequestParam(value = "accession", required = false) String accession,
			@ApiParam(value = "If set, return the reference sets for which the `assemblyId` matches this string (case-sensitive, exact match).") @Valid @RequestParam(value = "assemblyPUI", required = false) String assemblyPUI,
			@ApiParam(value = "If set, return the reference sets for which the `md5checksum` matches this string (case-sensitive, exact match).") @Valid @RequestParam(value = "md5checksum", required = false) String md5checksum,
			@ApiParam(value = "Used to request a specific page of data to be returned.  The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

	@ApiOperation(value = "Gets a `ReferenceSet` by ID.", nickname = "referencesetsReferenceSetDbIdGet", notes = "Gets a `ReferenceSet` by ID.", response = ReferenceSetsSingleResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Reference Sets", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "A successful response.", response = ReferenceSetsSingleResponse.class) })
	@RequestMapping(value = "/referencesets/{referenceSetDbId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<ReferenceSetsSingleResponse> referenceSetsReferenceSetDbIdGet(
			@ApiParam(value = "The ID of the `ReferenceSet` to be retrieved.", required = true) @PathVariable("referenceSetDbId") String referenceSetDbId, 
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

	@ApiOperation(value = "Gets a list of `ReferenceSet` matching the search criteria.", nickname = "searchReferencesetsPost", notes = "Gets a list of `ReferenceSet` matching the search criteria.", response = ReferenceSetsListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Reference Sets", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ReferenceSetsListResponse.class),
			@ApiResponse(code = 202, message = "Accepted", response = Model202AcceptedSearchResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/search/referencesets", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<ReferenceSetsListResponse> searchReferenceSetsPost(
			@ApiParam(value = "", required = true) @Valid @RequestBody ReferenceSetsSearchRequest body,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

	@ApiOperation(value = "Gets a list of `ReferenceSet` matching the search criteria.", nickname = "searchReferencesetsSearchResultsDbIdGet", notes = "Gets a list of `ReferenceSet` matching the search criteria.", response = ReferenceSetsListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "Reference Sets", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ReferenceSetsListResponse.class),
			@ApiResponse(code = 202, message = "Accepted", response = Model202AcceptedSearchResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/search/referencesets/{searchResultsDbId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<ReferenceSetsListResponse> searchReferenceSetsSearchResultsDbIdGet(
			@ApiParam(value = "Permanent unique identifier which references the search results", required = true) @PathVariable("searchResultsDbId") String searchResultsDbId,
			@ApiParam(value = "Used to request a specific page of data to be returned.  The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException;

}
