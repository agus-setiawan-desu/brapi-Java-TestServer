package org.brapi.test.BrAPITestServer.controller;

import java.util.List;

import org.brapi.test.BrAPITestServer.model.rest.Program;
import org.brapi.test.BrAPITestServer.model.rest.metadata.GenericResults;
import org.brapi.test.BrAPITestServer.model.rest.metadata.GenericResultsDataList;
import org.brapi.test.BrAPITestServer.model.rest.metadata.MetaData;
import org.brapi.test.BrAPITestServer.service.ProgramService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProgramController  extends BrAPIController{

	private ProgramService programService;
	
	public ProgramController(ProgramService programService) {
		this.programService = programService;
	}

	@CrossOrigin
	@RequestMapping(value="programs", method= {RequestMethod.GET})
	public GenericResults<GenericResultsDataList<Program>> getPrograms(
			@RequestParam(required=false) String programName,
			@RequestParam(required=false) String abbreviation,
			@RequestParam(value = "pageSize", defaultValue = "1000") int pageSize,
			@RequestParam(value = "page", defaultValue = "0") int page) {
		MetaData metaData = generateMetaDataTemplate(page, pageSize);
		List<Program> programs = programService.getPrograms(programName, abbreviation, metaData);
		return GenericResults.withList(programs).withMetaData(metaData);
	}
}
