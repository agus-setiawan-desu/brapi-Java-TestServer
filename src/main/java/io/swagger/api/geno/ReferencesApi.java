/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.18).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api.geno;

import io.swagger.model.Model202AcceptedSearchResponse;
import io.swagger.model.geno.ReferenceBasesResponse;
import io.swagger.model.geno.ReferenceSingleResponse;
import io.swagger.model.geno.ReferencesListResponse;
import io.swagger.model.geno.ReferencesSearchRequest;
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
@Api(value = "references", description = "the references API")
public interface ReferencesApi {

	@ApiOperation(value = "Gets a filtered list of `Reference` objects.", nickname = "referencesGet", notes = "`GET /references` will return a filtered list of `Reference` JSON objects.", response = ReferencesListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "References", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ReferencesListResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/references", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<ReferencesListResponse> referencesGet(
			@ApiParam(value = "The ID of the `Reference` to be retrieved.") @Valid @RequestParam(value = "referenceDbId", required = false) String referenceDbId,
			@ApiParam(value = "The ID of the `ReferenceSet` to be retrieved.") @Valid @RequestParam(value = "referenceSetDbId", required = false) String referenceSetDbId,
			@ApiParam(value = "If set, return the reference sets for which the `accession` matches this string (case-sensitive, exact match).") @Valid @RequestParam(value = "accession", required = false) String accession,
			@ApiParam(value = "If specified, return the references for which the `md5checksum` matches this string (case-sensitive, exact match).") @Valid @RequestParam(value = "md5checksum", required = false) String md5checksum,
			@ApiParam(value = "If the reference is derived from a source sequence") @Valid @RequestParam(value = "isDerived", required = false) Boolean isDerived,
			@ApiParam(value = "The minimum length of the reference sequences to be retrieved.") @Valid @RequestParam(value = "minLength", required = false) Integer minLength,
			@ApiParam(value = "The maximum length of the reference sequences to be retrieved.") @Valid @RequestParam(value = "maxLength", required = false) Integer maxLength,
			@ApiParam(value = "Used to request a specific page of data to be returned.  The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization)
			throws BrAPIServerException;

	@ApiOperation(value = "Lists `Reference` bases by ID and optional range.", nickname = "referencesReferenceDbIdBasesGet", notes = "Lists `Reference` bases by ID and optional range.", response = ReferenceBasesResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "References", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "A successful response.", response = ReferenceBasesResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = String.class) })
	@RequestMapping(value = "/references/{referenceDbId}/bases", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<ReferenceBasesResponse> referencesReferenceDbIdBasesGet(
			@ApiParam(value = "The ID of the `Reference` to be retrieved.", required = true) @PathVariable("referenceDbId") String referenceDbId,
			@ApiParam(value = "The start position (0-based) of this query. Defaults to 0. Genomic positions are non-negative integers less than reference length. Requests spanning the join of circular genomes are represented as two requests one on each side of the join (position 0).") @Valid @RequestParam(value = "start", required = false) Integer start,
			@ApiParam(value = "The end position (0-based, exclusive) of this query. Defaults to the length of this `Reference`.") @Valid @RequestParam(value = "end", required = false) Integer end,
			@ApiParam(value = "The continuation token, which is used to page through large result sets. To get the next page of results, set this parameter to the value of `next_page_token` from the previous response.") @Valid @RequestParam(value = "pageToken", required = false) String pageToken,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization)
					throws BrAPIServerException;

	@ApiOperation(value = "Gets a `Reference` by ID.", nickname = "referencesReferenceDbIdGet", notes = "`GET /references/{reference_id}` will return a JSON version of `Reference`.", response = ReferenceSingleResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "References", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "A successful response.", response = ReferenceSingleResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class),
			@ApiResponse(code = 404, message = "Not Found", response = String.class) })
	@RequestMapping(value = "/references/{referenceDbId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<ReferenceSingleResponse> referencesReferenceDbIdGet(
			@ApiParam(value = "The ID of the `Reference` to be retrieved.", required = true) @PathVariable("referenceDbId") String referenceDbId,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization)
					throws BrAPIServerException;

	@ApiOperation(value = "Gets a list of `Reference` matching the search criteria.", nickname = "searchReferencesPost", notes = "`POST /references/search` must accept a JSON version of `SearchReferencesRequest` as the post body and will return a JSON version of `SearchReferencesResponse`.", response = ReferencesListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "References", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ReferencesListResponse.class),
			@ApiResponse(code = 202, message = "Accepted", response = Model202AcceptedSearchResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/search/references", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<ReferencesListResponse> searchReferencesPost(
			@ApiParam(value = "References Search request") @Valid @RequestBody ReferencesSearchRequest body,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization)
					throws BrAPIServerException;;

	@ApiOperation(value = "Gets a list of `Reference` matching the search criteria.", nickname = "searchReferencesSearchResultsDbIdGet", notes = "`POST /references/search` must accept a JSON version of `SearchReferencesRequest` as the post body and will return a JSON version of `SearchReferencesResponse`.", response = ReferencesListResponse.class, authorizations = {
			@Authorization(value = "AuthorizationToken") }, tags = { "References", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ReferencesListResponse.class),
			@ApiResponse(code = 202, message = "Accepted", response = Model202AcceptedSearchResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = String.class),
			@ApiResponse(code = 403, message = "Forbidden", response = String.class) })
	@RequestMapping(value = "/search/references/{searchResultsDbId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	ResponseEntity<ReferencesListResponse> searchReferencesSearchResultsDbIdGet(
			@ApiParam(value = "Permanent unique identifier which references the search results", required = true) @PathVariable("searchResultsDbId") String searchResultsDbId,
			@ApiParam(value = "Used to request a specific page of data to be returned.  The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@ApiParam(value = "HTTP HEADER - Token used for Authorization   <strong> Bearer {token_string} </strong>") @RequestHeader(value = "Authorization", required = false) String authorization)
					throws BrAPIServerException;;

}
