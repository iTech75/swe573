package com.itech75.acp.controllers;

import java.sql.SQLException;
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

import com.itech75.acp.dal.ViolationDAL;
import com.itech75.acp.dal.ViolationMetaDAL;
import com.itech75.acp.dal.ViolationTypeDAL;
import com.itech75.acp.entities.Violation;
import com.itech75.acp.entities.ViolationData;
import com.itech75.acp.entities.ViolationMeta;
import com.itech75.acp.entities.ViolationResult;
import com.itech75.acp.entities.ViolationType;
import com.itech75.acp.common.Units;
import com.itech75.acp.common.WebHelper;

/*
 * Violation related functionalities implemented 
 */
@Controller
@RequestMapping(value = "/violation")
public class ViolationController {

	/*
	 * Lists all violations which complies the given rule (i.e. last 5 violations) 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listViolations() {
		ModelAndView model = new ModelAndView("violations");
		model.addObject("violations", ViolationDAL.getViolations());
		return model;
	}

	@RequestMapping(value="/types", method=RequestMethod.GET)
	public @ResponseBody List<ViolationType> types()
	{
		try {
			return ViolationTypeDAL.getViolationTypes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/metas/{id}", method=RequestMethod.GET)
	public @ResponseBody List<ViolationMeta> metas(@PathVariable String id)
	{
		return ViolationMetaDAL.getViolationMetaList(id);
	}
	
	/*
	 * View a selected violation by the user, path variable 'id' defines the violation to be loaded.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)	
	public ModelAndView viewViolation(@PathVariable int id, HttpSession session) {
		ModelAndView model = new ModelAndView("violation");
		Violation violation = null;
		try {
			violation = ViolationDAL.getViolation(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("violation", violation);		
		model.addObject("violation", violation);
		return model;
	}

	/*
	 * Enables edit view for a selected violation. Any edition will be forwarded to 'save' method by the form.
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, HttpSession session) {
		Violation violation = (Violation) session.getAttribute("violation");
		if(violation != null){
			String violationType = violation.getType();
			ModelAndView model = new ModelAndView("editviolation");
	
			model.addObject("violation", violation);
			model.addObject("violationType", violationType);
			model.addObject("selectedControlType", 0);
			return model;
		}
		return new ModelAndView("home");
	}

	/*
	 * Edit mode actions are forwarded to this method. Final save operation occurs only if the 
	 * 'saveViolation' form input is equals to "1" which is realized by the "Save Violation"
	 * button on the form.
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request, HttpSession session) {
		Violation violation = (Violation) session.getAttribute("violation");
		if(violation == null){
			return new ModelAndView("home");
		}
		
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
			String id = violationType;
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
			WebHelper.showSuccess(model, "Violation saved...");
		}
		
		return model;
	}
	
	/*
	 * 
	 */
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

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ViolationResult post(@RequestBody final Violation violation) {
		return new ViolationResult(ViolationDAL.createViolation(violation), "");
	}

	@RequestMapping(value="removecontrol/{controlid}", method = RequestMethod.GET)
	public ModelAndView removeControl(@PathVariable int controlid, HttpServletRequest request, HttpSession session) {
		/*
		 * Get violation from session
		 */
		Violation violation = (Violation) session.getAttribute("violation");
		
		String violationType = request.getParameter("violationType");
		String selectedControlType = request.getParameter("controlType");
		ModelAndView model = new ModelAndView("editviolation");
		if (violationType!=null && !violationType.isEmpty()) {
			String id = violationType;
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
			
			model.addObject("violation", violation);
			model.addObject("violationType", violationType);
			model.addObject("violationMetaList", metaList);
			model.addObject("selectedControlType", selectedControlType);
			model.addObject("selectedMetaDescription", selectedMetaDescription);
		}
		
		return model;
	}
}