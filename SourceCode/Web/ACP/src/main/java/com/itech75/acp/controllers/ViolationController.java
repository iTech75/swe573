package com.itech75.acp.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itech75.acp.dal.CommentDAL;
import com.itech75.acp.dal.ViolationDAL;
import com.itech75.acp.dal.ViolationDataDAL;
import com.itech75.acp.dal.ViolationMetaDAL;
import com.itech75.acp.dal.ViolationTypeDAL;
import com.itech75.acp.dal.VoteDAL;
import com.itech75.acp.entities.Violation;
import com.itech75.acp.entities.ViolationDTO;
import com.itech75.acp.entities.ViolationData;
import com.itech75.acp.entities.ViolationMeta;
import com.itech75.acp.entities.ViolationResult;
import com.itech75.acp.entities.ViolationType;
import com.itech75.acp.common.ResultBase;
import com.itech75.acp.common.ReturnStatusCodes;
import com.itech75.acp.common.Units;
import com.itech75.acp.common.WebHelper;
import com.itech75.acp.core.BusinessEngine;

/*
 * Violation related functionalities implemented 
 */
@Controller
@RequestMapping(value = "/violation")
public class ViolationController {

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ViolationResult post(@RequestBody final ViolationDTO violation) {
		return new ViolationResult(ViolationDAL.createViolation(violation), "");
	}
	
	/*
	 * Lists all violations which complies the given rule (i.e. last 5 violations) 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listViolations(@RequestParam String query, HttpSession session) {
		ModelAndView model = new ModelAndView("violations");
		
		int userid = session.getAttribute("userid") != null ? (int)session.getAttribute("userid") : 0;
		model.addObject("violations", ViolationDAL.getViolations(query, userid));
		return model;
	}

	@RequestMapping(value="/types", method=RequestMethod.GET)
	public @ResponseBody List<ViolationType> types()
	{
		List<ViolationType> result = ViolationTypeDAL.getViolationTypes();
		result.add(0, new ViolationType("0", "Please select a type"));
		return result;
	}
	
	@RequestMapping(value="/metas/{id}", method=RequestMethod.GET)
	public @ResponseBody List<ViolationMeta> metas(@PathVariable String id)
	{
		List<ViolationMeta> result = ViolationMetaDAL.getViolationMetaList(id);
		result.add(0, ViolationMeta.CreateDefaultSelectItem());
		return result;
	}
	
	@RequestMapping(value="/meta/{id}", method=RequestMethod.GET)
	public @ResponseBody ViolationMeta getMeta(@PathVariable int id)
	{
		return ViolationMetaDAL.getViolationMeta(id);
	}
	
	/*
	 * View a selected violation by the user, path variable 'id' defines the violation to be loaded.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)	
	public ModelAndView viewViolation(@PathVariable int id, HttpSession session) throws Exception {
		ModelAndView model = new ModelAndView("violation");
		Violation violation = null;
		violation = ViolationDAL.getViolation(id);
		session.setAttribute("violation", violation);
		return model;
	}

	@RequestMapping(value = "nearby", method = RequestMethod.GET)	
	public ModelAndView nearby(HttpSession session) throws Exception {
		ModelAndView model = new ModelAndView("nearby");
		return model;
	}

	@RequestMapping(value = "findnearby", method = RequestMethod.GET)	
	public ModelAndView findNearby(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView("nearby");
		String location = request.getParameter("violationLocation");
		if(location == null || location.equals("")){
			WebHelper.showWarning(model, "Please click on a location on the map");
		}
		else{
			List<Violation> result = BusinessEngine.findNearbyViolations(location);
			model.addObject("violations", result);
			if(result.isEmpty()){
				WebHelper.showMessage(model, "No nearby violation found to selected location");
			}
		}
		return model;
	}

	/*
	 * Enables edit view for a selected violation. Any edition will be forwarded to 'save' method by the form.
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, HttpSession session) {
		Violation violation = (Violation) session.getAttribute("violation");
		ModelAndView model = new ModelAndView("editviolation");
		if(violation != null){
			addControlVariablesToView(model, "0", "0");
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
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Violation violation = (Violation) session.getAttribute("violation");
		if(violation == null){
			return new ModelAndView("home");
		}
		
		String violationType = request.getParameter("violationType");
		String selectedControlType = request.getParameter("controlType");
		String operation = request.getParameter("operation");
		ModelAndView model = null;
		
		if(operation != null){
			switch (operation) {
			
			case "add":
				model = addOperationForSave(request, violation, violationType, selectedControlType);
				break;
				
			case "save":
				model = saveOperationForSave(request, violation);
				break;
				
			case "cancel":
				cancelOperationForSave(request, response, violation);
				break;

			default:
				break;
			}
		}
		return model;
	}

	private ModelAndView addOperationForSave(HttpServletRequest request, Violation violation, String violationType,
			String selectedControlType) {
		ModelAndView model;
		model = new ModelAndView("editviolation");
		addControlVariablesToView(model, violationType, selectedControlType);
		
		ResultBase<ViolationData> result = createDataFromRequest(request);
		if(result.getStatusCode() == ReturnStatusCodes.SUCCESS){
			ViolationDataDAL.readDescriptionsForIdFields(result.getResult());			
			violation.getViolationData().add(result.getResult());
			WebHelper.showMessage(model, result.getMessage());
		}
		else{
			WebHelper.showError(model, result.getMessage());
		}
		return model;
	}
	
	private ModelAndView saveOperationForSave(HttpServletRequest request, Violation violation) {
		ModelAndView model;
		model = new ModelAndView("violation");
		
		violation.setDescription(request.getParameter("violationDescription"));
		violation.setTitle(request.getParameter("violationTitle"));
		String violationLocation = request.getParameter("violationLocation");
		String[] latLon = violationLocation.split(",");
		violation.setLatitude(Double.parseDouble(latLon[0]));
		violation.setLongitude(Double.parseDouble(latLon[1]));
		violation.setSeverity(BusinessEngine.calculateViolationSeverity(violation));
		
		ViolationDAL.save(violation);
		WebHelper.showSuccess(model, "Violation saved...");
		return model;
	}

	private void cancelOperationForSave(HttpServletRequest request, HttpServletResponse response, Violation violation) {
		try {
			String url = String.format("%s/violation/%d", request.getContextPath(), violation.getId());
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 
	 */
	private ResultBase<ViolationData> createDataFromRequest(HttpServletRequest request) {
		String buffer;
		int metaId = 0;
		Units unit = Units.MM;
		
		if(request.getParameterMap().containsKey("controlType")){
			metaId = Integer.parseInt(request.getParameter("controlType"));
		}
		else{
			return ResultBase.sendError("There is an unexpected state in the request, please try again!");
		}
		
		ViolationMeta violationMeta = ViolationMetaDAL.getViolationMeta(metaId);
		String value = null;
		switch (violationMeta.getType()) {
		case 1:
			value = request.getParameter("newControlValueCheckbox");
			value = (value != null && value.toLowerCase().equals("on")) ? "Exists" : "None";
			break;
		default:
			value = request.getParameter("newControlValue");
			break;
		}
		
		if(value.equals(violationMeta.getExpectedValue())){
			return ResultBase.sendError("There is no violation, check your numbers, please!");
		}
		
		buffer = request.getParameter("newUnit");
		if(buffer != null){
			buffer = buffer.toUpperCase();
			unit = Units.valueOf(buffer);
		}
		
		ViolationData data = new ViolationData(0, 0, metaId, value, unit);
		data.setSeverity(BusinessEngine.calculateViolationControlSeverity(violationMeta, data));
		return ResultBase.sendSuccess(data, "New control added to the violation");
	}

	@RequestMapping(value="removecontrol/{controlid}", method = RequestMethod.GET)
	public ModelAndView removeControl(@PathVariable int controlid, HttpServletRequest request, HttpSession session) {
		Violation violation = (Violation) session.getAttribute("violation");
		ModelAndView model = new ModelAndView("editviolation");
		
		boolean result = violation.removeViolationData(controlid);
		if(result){
			WebHelper.showSuccess(model, "Control is removed from the violation, click save to persist change!");
		}
		else{
			WebHelper.showError(model, "Could not remove the item, please try again!");
		}
		model.addObject("violation", violation);
		
		addControlVariablesToView(model, "0", "0");
		
		return model;
	}

	private void addControlVariablesToView(ModelAndView model, String violationType, String selectedControlType) {
		model.addObject("violationType", (violationType != null && !violationType.equals("")) ? violationType : "0");
		model.addObject("selectedControlType", (selectedControlType != null && !selectedControlType.equals("")) ? selectedControlType : "0");
	}
	
	@RequestMapping(value = "addcomment")
	public ModelAndView addcomment(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		ModelAndView model = new ModelAndView("violation");
		Violation violation = (Violation)session.getAttribute("violation");
		if(session != null && session.getAttribute("userid") != null){
			int userid = (int)session.getAttribute("userid");
			String content = request.getParameter("newComment");
			if(CommentDAL.addComment(violation.getId(), content, userid)){
				WebHelper.showSuccess(model, "Your comment added!");
				String url = String.format("%s/violation/%d#comments", request.getContextPath(), violation.getId());
				response.sendRedirect(url);
			}
			else{
				WebHelper.showError(model, "Error occured, please try later!");			
			}
		}
		else{
			model = new ModelAndView("login");
		}
		
		return model;
	}
	
	@RequestMapping(value = "vote")
	public ModelAndView vote(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		ModelAndView model = new ModelAndView("violation");
		Violation violation = (Violation)session.getAttribute("violation");
		if(session != null && session.getAttribute("userid") != null){
			int userid = (int)session.getAttribute("userid");
			int vote = Integer.parseInt(request.getParameter("vote"));
			String operation = request.getParameter("operation");
			switch (operation) {
			case "vote":
				if(VoteDAL.addVote(violation.getId(), vote, userid)){
					WebHelper.showSuccess(model, "Your vote added!");
					String url = String.format("%s/violation/%d", request.getContextPath(), violation.getId());
					response.sendRedirect(url);
				}
				else{
					WebHelper.showError(model, "Error occured, please try later!");			
				}
				break;

			case "deletevote":
				if(VoteDAL.deleteVoteForViolation(violation.getId(), userid)){
					violation.clearVoteForUser();
					WebHelper.showSuccess(model, "Your vote deleted!");
					String url = String.format("%s/violation/%d", request.getContextPath(), violation.getId());
					response.sendRedirect(url);
				}
				else{
					WebHelper.showError(model, "Error occured, please try later!");			
				}
				break;

			default:
				break;
			}
		}
		else{
			model = new ModelAndView("login");
		}
		
		return model;
	}
	
	@RequestMapping(value = "fix", method = RequestMethod.POST)
	public ModelAndView fix(@RequestParam("photo") MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		ModelAndView model = new ModelAndView("violation");
		Violation violation = (Violation)session.getAttribute("violation");
		if(session != null && session.getAttribute("userid") != null){
			int userid = (int)session.getAttribute("userid");
			byte[] photo = file.getBytes();
			ResultBase<?> result = BusinessEngine.fixViolation(violation, photo, userid);
			if(result.getStatusCode() == ReturnStatusCodes.SUCCESS){
				String url = String.format("%s/violation/%d", request.getContextPath(), violation.getId());
				response.sendRedirect(url);
			}
			else{
				WebHelper.showError(model, result.getMessage());
			}
		}
		else{
			model = new ModelAndView("login");
		}
		return model;
	}	
	
	@RequestMapping(value = "changephoto", method = RequestMethod.POST)
	public ModelAndView changephoto(@RequestParam("photo") MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		ModelAndView model = new ModelAndView("violation");
		Violation violation = (Violation)session.getAttribute("violation");
		if(session != null && session.getAttribute("userid") != null){
			byte[] photo = file.getBytes();
			boolean result = ViolationDAL.changeViolationImage(violation.getId(), photo);
			if(result){
				String url = String.format("%s/violation/%d", request.getContextPath(), violation.getId());
				response.sendRedirect(url);
			}
			else{
				WebHelper.showError(model, "Could not change the image please try again!");
			}
		}
		else{
			model = new ModelAndView("login");
		}
		return model;
	}	
	
	@RequestMapping(value = "approvereject", method = RequestMethod.POST)
	public ModelAndView approve(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		ModelAndView model = new ModelAndView("violation");
		Violation violation = (Violation)session.getAttribute("violation");
		String operation = request.getParameter("operation");
		if(session != null && session.getAttribute("userid") != null && operation != null){
			int userid = (int)session.getAttribute("userid");
			ResultBase<?> result = null;
			if(operation.equals("approve")){
				result = BusinessEngine.approveFix(violation, userid);
			}
			else if(operation.equals("reject")){
				result = BusinessEngine.rejectFix(violation, userid);
			}
			else{
				return model;
			}
			if(result.getStatusCode() == ReturnStatusCodes.SUCCESS){
				String url = String.format("%s/violation/%d", request.getContextPath(), violation.getId());
				response.sendRedirect(url);
			}
			else{
				WebHelper.showError(model, result.getMessage());
			}
		}
		else{
			model = new ModelAndView("login");
		}
		return model;
	}	
}