/**
 * NOTE: This class is auto generated by the swagger code generator program (1.0.12-1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.StudyTypesResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-06-04T21:50:05.517Z")

@Api(value = "studytypes", description = "the studytypes API")
public interface StudytypesApi {

    @ApiOperation(value = "List study types", nickname = "studytypesGet", notes = " Call to retrieve the list of study types. Scope: PHENOTYPING. Implementation target date: PAG2016 <a href=\"https://test-server.brapi.org/brapi/v1/studytypes\"> test-server.brapi.org/brapi/v1/studytypes</a>", response = StudyTypesResponse.class, tags={ "Studies", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = StudyTypesResponse.class) })
    @RequestMapping(value = "/studytypes",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<StudyTypesResponse> studytypesGet(@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,@ApiParam(value = "Which result page is requested. The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page);

}
