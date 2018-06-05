/**
 * NOTE: This class is auto generated by the swagger code generator program (1.0.12-1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.CallsResponse;
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

@Api(value = "calls", description = "the calls API")
public interface CallsApi {

    @ApiOperation(value = "Call search", nickname = "callsGet", notes = "<strong>Implementation Notes</strong> Having a consistent structure for the path string of each call is very important for teams to be able to connect and find errors. Read more on <a href=\"https://github.com/plantbreeding/API/issues/144\">Github</a>. Here are the rules for the path of each call that should be returned <ul>          <li>Every word in the call path should match the documentation exactly, both in spelling and capitalization. Note that path strings are all lower case, but path parameters are camel case.</li>           <li>Each path should start relative to '/' and therefore should not include '/'</li>   <li>No leading or trailing slashes ('/') </li>   <li>Path parameters are wrapped in curly braces ('{}'). The name of the path parameter should be spelled exactly as it is specified in the documentation.</li>         </ul> <table>   <tr>     <th>Examples</th>   </tr>   <tr>     <td><strong>GOOD</strong></td>     <td>\"call\": \"germplasm/{germplasmDbId}/markerprofiles\"</td>   </tr>    <tr>     <td>BAD</td>     <td>\"call\": \"germplasm/{<strong>id</strong>}/markerprofiles\"</td>   </tr>    <tr>     <td>BAD</td>     <td>\"call\": \"germplasm/{germplasmDbId}/marker<strong>P</strong>rofiles\"</td>   </tr>   <tr>     <td>BAD</td>     <td>\"call\": \"germplasm/{germplasm<strong>dbid</strong>}/markerprofiles\"</td>   </tr>    <tr>     <td>BAD</td>     <td>\"call\": \"<strong>brapi/v1</strong>/germplasm/{germplasmDbId}/markerprofiles\"</td>   </tr>   <tr>     <td>BAD</td>     <td>\"call\": \"<strong>/g</strong>ermplasm/{germplasmDbId}/markerprofile<strong>s/</strong>\"</td>   </tr>    <tr>     <td>BAD</td>     <td>\"call\": \"germplasm/<strong>&lt</strong>germplasmDbId<strong>&gt</strong>/markerprofiles\"</td>   </tr>  </table>  <a href=\"https://test-server.brapi.org/brapi/v1/calls\"> test-server.brapi.org/brapi/v1/calls</a>", response = CallsResponse.class, tags={ "Calls", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = CallsResponse.class) })
    @RequestMapping(value = "/calls",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<CallsResponse> callsGet(@ApiParam(value = "The data format supported by the call. Example: `json`") @Valid @RequestParam(value = "datatype", required = false) String datatype,@ApiParam(value = "The size of the pages to be returned. Default is `1000`.") @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,@ApiParam(value = "Which result page is requested. The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`.") @Valid @RequestParam(value = "page", required = false) Integer page);

}
