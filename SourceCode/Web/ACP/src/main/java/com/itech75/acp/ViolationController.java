package com.itech75.acp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itech75.acp.DAL.ViolationDAL;
import com.itech75.acp.DAL.ViolationMetaDAL;
import com.itech75.acp.Entities.Violation;
import com.itech75.acp.Entities.ViolationData;
import com.itech75.acp.Entities.ViolationMeta;
import com.itech75.acp.Entities.ViolationResult;

import Common.Units;

@Controller
@RequestMapping(value = "/violation")
public class ViolationController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("violations");
		model.addObject("violations", ViolationDAL.getViolations());
		return model;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable int id, HttpSession session) {
		ModelAndView model = new ModelAndView("violation");
		Violation violation = ViolationDAL.getViolation(id);
		session.setAttribute("violation", violation);		
		model.addObject("violation", violation);
		return model;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, HttpSession session) {
		/*
		 * Get violation from session
		 */
		Violation violation = (Violation) session.getAttribute("violation");
		
		int violationType = violation.getType();
		ModelAndView model = new ModelAndView("editviolation");

		model.addObject("violation", violation);
		model.addObject("violationType", violationType);
		model.addObject("selectedControlType", 0);
		return model;
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request, HttpSession session) {
		/*
		 * Get violation from session
		 */
		Violation violation = (Violation) session.getAttribute("violation");
		
		String violationType = request.getParameter("violationType");
		String selectedControlType = request.getParameter("controlType");
		String addControl = request.getParameter("addControl");
		String saveViolation = request.getParameter("saveViolation");
		String metaDescription = request.getParameter("metaDescription");
		ModelAndView model = new ModelAndView("editviolation");
		
		if(addControl != null && addControl.equals("1")){
			ViolationData data = createDataFromRequest(request);
			data.setViolationMetaDescription(metaDescription);
			violation.getViolationData().add(data);
		}

		if (violationType!=null && !violationType.isEmpty()) {
			int id = Integer.parseInt(violationType);
			int controlTypeId = 0;
			if(selectedControlType != null && selectedControlType.trim() != ""){
				controlTypeId = Integer.parseInt(selectedControlType);
			}
			
			String selectedMetaDescription = "";
			List<ViolationMeta> metaList = ViolationMetaDAL.getViolationMetaList(id);
			for (ViolationMeta meta : metaList) {
				if(meta.getId() == controlTypeId){
					selectedMetaDescription = meta.getDescription();
				}
			}
			
			violation.setDescription(request.getParameter("violationDescription"));
			violation.setTitle(request.getParameter("violationTitle"));
			violation.setType(id);
			
			if(saveViolation != null && saveViolation.equals("1")){
				ViolationDAL.save(violation);
			}

			model.addObject("violation", violation);
			model.addObject("violationType", violationType);
			model.addObject("violationMetaList", metaList);
			model.addObject("selectedControlType", selectedControlType);
			model.addObject("selectedMetaDescription", selectedMetaDescription);
		}
		
		return model;
	}
	
	private ViolationData createDataFromRequest(HttpServletRequest request) {
		String buffer;
		int controlId = 0;
		Units unit = Units.MM;
		
		buffer = request.getParameter("controlType");
		if(buffer != null){
			controlId = Integer.parseInt(buffer);
		}
		
		String value = request.getParameter("newControlValue");
		
		buffer = request.getParameter("newUnit");
		if(buffer != null){
			buffer = buffer.toUpperCase();
			unit = Units.valueOf(buffer);
		}
		
		return new ViolationData(0, 0, controlId, value, unit);
	}
	
//	private String createControls(HttpServletRequest request) {
//		String violationType = request.getParameter("violationType");
//		int id = Integer.parseInt(violationType);
//		
//		StringBuilder builder = new StringBuilder();
//		List<ViolationMeta> list = ViolationMetaDAL.getViolationMetaList(id);
//		updateFromRequest(list, request);
//		for (ViolationMeta item : list) {
//			builder.append("<div class='row form-inline col-md-offset-1'>");
//			createControlFor(builder, item);
//			builder.append("</div>");
//		}
//		return builder.toString();
//	}
//
//	private List<ViolationData> updateFromRequest(List<ViolationMeta> list, HttpServletRequest request) {
//		List<ViolationData> result = new ArrayList<ViolationData>();
//		
//		for (ViolationMeta meta : list) {
//			String input_id = meta.getDescription().toLowerCase().replace(' ', '_');
//			String unitBoxName = input_id + "_ub";
//			
//			String value = request.getParameter(input_id);
//			if(value != null){
//				value = value.trim().toLowerCase();
//				String unit = request.getParameter(unitBoxName);
//				Units unitEnum = Units.MM;
//				if(unit != null){
//					unitEnum = Units.valueOf(unit);
//				}
//				//ViolationData data = new ViolationData();
//			}
//		}
//		
//		return result;
//	}
//
//	private void createControlFor(StringBuilder builder, ViolationMeta item) {
//		String input_id = item.getDescription().toLowerCase().replace(' ', '_');
//
//		switch (item.getType()) {
//		case 0: {
//			builder.append("<div class='input-group col-md-3'>");
//			builder.append(String.format("<label for='%s' class='control-label'>%s</label>", input_id, item.getDescription()));
//			builder.append(String.format("<input type='text' class='form-control' id='%s' name='%s'>", input_id, input_id));
//			builder.append("</div>");
//			createUnitSelectBox(builder, item, input_id);
//			createExpectedValue(builder, item, input_id);
//			break;
//		}
//		case 1: {
//			builder.append("<div class='checkbox col-md-3'>");
//			builder.append(String.format("<label for='%s'>", input_id));
//			builder.append(String.format("<input type='checkbox' id='%s' name='%s'>", input_id, input_id));
//			builder.append(String.format("%s</label>", item.getDescription()));
//			builder.append("</div>");
//			createUnitSelectBox(builder, item, input_id);
//			createExpectedValue(builder, item, input_id);
//			break;
//		}
//		}
//	}
//
//	private void createUnitSelectBox(StringBuilder builder, ViolationMeta item, String input_id) {
//		String unitBoxName = input_id + "_ub";
//		builder.append("<div class='input-group col-md-2 col-md-offset-1'>");
//		builder.append(String.format("<label for='%s' class='control-label'>Unit</label>", unitBoxName));
//		builder.append(String.format("<select class='form-control' id='%s' name='%s'>", unitBoxName, unitBoxName));
//		builder.append("<optgroup label='length'>");
//		builder.append("<option>mm</option>");
//		builder.append("<option>cm</option>");
//		builder.append("<option>m</option>");
//		builder.append("</optgroup>");
//		builder.append("<optgroup label='slope'>");
//		builder.append("<option>degree</option>");
//		builder.append("</optgroup>");
//		builder.append("<optgroup label='boolean'>");
//		builder.append("<option>TrueFalse</option>");
//		builder.append("</optgroup>");
//		builder.append("</select>");
//		builder.append("</div>");
//	}
//
//	private void createExpectedValue(StringBuilder builder, ViolationMeta item, String input_id) {
//		String expectValueName = input_id + "_ev";
//		
//		builder.append("<div class='input-group col-md-3 col-md-offset-1'>");
//		builder.append(String.format("<label for='%s' class='control-label'>", expectValueName));
//		builder.append("Expected value");
//		builder.append("</label>");
//		builder.append(String.format("<p class='form-control' id='%s'>%s</p>", expectValueName, item.getExpectedValue()));
//		builder.append("</div>");
//	}


	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ViolationResult post(@RequestBody final Violation violation) {
		return new ViolationResult(ViolationDAL.createViolation(violation), "");
	}

}
