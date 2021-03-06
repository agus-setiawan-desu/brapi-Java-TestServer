package org.brapi.test.BrAPITestServer.controller.pheno;

import io.swagger.model.Metadata;
import io.swagger.model.pheno.Image;
import io.swagger.model.pheno.ImageListResponse;
import io.swagger.model.pheno.ImageListResponseResult;
import io.swagger.model.pheno.ImageNewRequest;
import io.swagger.model.pheno.ImageSearchRequest;
import io.swagger.model.pheno.ImageSingleResponse;
import io.swagger.api.pheno.ImagesApi;

import org.brapi.test.BrAPITestServer.controller.core.BrAPIController;
import org.brapi.test.BrAPITestServer.exceptions.BrAPIServerException;
import org.brapi.test.BrAPITestServer.service.pheno.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-03-20T16:32:22.556Z[GMT]")
@Controller
public class ImagesApiController extends BrAPIController implements ImagesApi {

	private static final Logger log = LoggerFactory.getLogger(ImagesApiController.class);

	private final ImageService imageService;

	private final HttpServletRequest request;

	@Autowired
	public ImagesApiController(ImageService imageService, HttpServletRequest request) {
		this.imageService = imageService;
		this.request = request;
	}

	@CrossOrigin
	@Override
	public ResponseEntity<ImageListResponse> imagesGet(
			@Valid @RequestParam(value = "imageDbId", required = false) String imageDbId,
			@Valid @RequestParam(value = "imageName", required = false) String imageName,
			@Valid @RequestParam(value = "observationUnitDbId", required = false) String observationUnitDbId,
			@Valid @RequestParam(value = "observationDbId", required = false) String observationDbId,
			@Valid @RequestParam(value = "descriptiveOntologyTerm", required = false) String descriptiveOntologyTerm,
			@Valid @RequestParam(value = "externalReferenceID", required = false) String externalReferenceID,
			@Valid @RequestParam(value = "externalReferenceSource", required = false) String externalReferenceSource,
			@Valid @RequestParam(value = "page", required = false) Integer page,
			@Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestHeader(value = "Authorization", required = false) String authorization)
			throws BrAPIServerException {

		log.debug("Request: " + request.getRequestURI());
		validateAcceptHeader(request);
		Metadata metadata = generateMetaDataTemplate(page, pageSize);
		List<Image> data = imageService.findImages(imageDbId, imageName, observationUnitDbId, observationDbId,
				descriptiveOntologyTerm, externalReferenceID, externalReferenceSource, metadata);
		return responseOK(new ImageListResponse(), new ImageListResponseResult(), data, metadata);
	}

	@CrossOrigin
	@Override
	public ResponseEntity<ImageSingleResponse> imagesImageDbIdGet(@PathVariable("imageDbId") String imageDbId,
			@RequestHeader(value = "Authorization", required = false) String authorization)
			throws BrAPIServerException {

		log.debug("Request: " + request.getRequestURI());
		validateAcceptHeader(request);
		Image data = imageService.getImage(imageDbId);
		return responseOK(new ImageSingleResponse(), data);
	}

	@CrossOrigin
	@Override
	public ResponseEntity<byte[]> imagesImageDbIdContentGet(
			@PathVariable("imageDbId") String imageDbId,
			@PathVariable("imageName") String imageName, 
			@RequestHeader(value = "Authorization", required = false) String authorization) throws BrAPIServerException {
		byte[] data = imageService.getImageData(imageDbId);
		Image image = imageService.getImage(imageDbId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(image.getMimeType()));
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
	}

	@CrossOrigin
	@Override
	public ResponseEntity<ImageSingleResponse> imagesImageDbIdImageContentPut(
			@PathVariable("imageDbId") String imageDbId, @Valid @RequestBody byte[] body,
			@RequestHeader(value = "Authorization", required = false) String authorization)
			throws BrAPIServerException {

		log.debug("Request: " + request.getRequestURI());
		validateAcceptHeader(request);
		Image data = imageService.updateImageContent(imageDbId, request.getRequestURL().toString(), body);
		return responseOK(new ImageSingleResponse(), data);
	}

	@CrossOrigin
	@Override
	public ResponseEntity<ImageSingleResponse> imagesImageDbIdPut(@PathVariable("imageDbId") String imageDbId,
			@Valid @RequestBody ImageNewRequest body,
			@RequestHeader(value = "Authorization", required = false) String authorization)
			throws BrAPIServerException {

		log.debug("Request: " + request.getRequestURI());
		validateAcceptHeader(request);
		Image data = imageService.updateImage(imageDbId, body);
		return responseOK(new ImageSingleResponse(), data);
	}

	@CrossOrigin
	@Override
	public ResponseEntity<ImageListResponse> imagesPost(@Valid @RequestBody List<ImageNewRequest> body,
			@RequestHeader(value = "Authorization", required = false) String authorization)
			throws BrAPIServerException {

		log.debug("Request: " + request.getRequestURI());
		validateAcceptHeader(request);
		List<Image> data = imageService.saveImages(body);
		return responseOK(new ImageListResponse(), new ImageListResponseResult(), data);
	}

	@CrossOrigin
	@Override
	public ResponseEntity<ImageListResponse> searchImagesPost(@Valid @RequestBody ImageSearchRequest body,
			@RequestHeader(value = "Authorization", required = false) String authorization)
			throws BrAPIServerException {

		log.debug("Request: " + request.getRequestURI());
		validateAcceptHeader(request);
		Metadata metadata = generateMetaDataTemplate(body);
		List<Image> data = imageService.findImages(body, metadata);
		return responseOK(new ImageListResponse(), new ImageListResponseResult(), data, metadata);
	}

	@CrossOrigin
	@Override
	public ResponseEntity<ImageListResponse> searchImagesSearchResultsDbIdGet(
			@PathVariable("searchResultsDbId") String searchResultsDbId,
			@Valid @RequestParam(value = "page", required = false) Integer page,
			@Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestHeader(value = "Authorization", required = false) String authorization)
			throws BrAPIServerException {

		log.debug("Request: " + request.getRequestURI());
		validateAcceptHeader(request);
		return new ResponseEntity<ImageListResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

}
