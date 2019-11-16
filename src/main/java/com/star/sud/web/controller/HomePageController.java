/**
 * 
 */
package com.star.sud.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.star.sud.web.dto.UserDetail;
import com.star.sud.web.service.IGenerateJasperService;
import com.star.sud.web.status.ReportStatus;
import com.star.sud.web.status.ReportStatus.STATUS;

/**
 * @author Sudarshan
 *
 */
@Controller
public class HomePageController {

	// Static Attributes
	//////////////////////
	private static final Logger log = Logger.getLogger(HomePageController.class);

	// Attributes
	/////////////////
	@Autowired
	@Qualifier("generateJasperService")
	private IGenerateJasperService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getLandingPage(Model model, @ModelAttribute UserDetail userDetail) {
		log.debug("getLandingPage");
		try {
			model.addAttribute("userDetail", userDetail);
		} catch (Exception e) {
			log.error("getLandingPage", e);
		}
		return "home";
	}

	@RequestMapping(value = "/generateReport", method = RequestMethod.POST)
	public String generateReport(Model model, @ModelAttribute UserDetail userDetail) {
		log.debug("generateReport");
		try {

			if (null == userDetail)
				throw new Exception("userDetail param is null or empty");

			ReportStatus status = service.generateBasicReport(userDetail);
			if (status != null && status.getStatus().equals(STATUS.SUCCESS)) {
				model.addAttribute("msgsuccess", "Successfully Generated the Report!!");
				model.addAttribute("isDisabled", Boolean.TRUE);
				userDetail.setImagePath(status.getResult());
			} else {
				model.addAttribute("msgdanger", "Failed to generate Report!, Please try again");
				model.addAttribute("isDisabled", Boolean.FALSE);
			}
		} catch (Exception e) {
			log.error("generateReport", e);
			model.addAttribute("msgdanger", "Failed to generate Report!, Please try again");
			model.addAttribute("isDisabled", Boolean.FALSE);
		}
		model.addAttribute("userDetail", userDetail);
		return "home";
	}

	@RequestMapping(value = "/download")
	public String downloadFile(Model model, @ModelAttribute UserDetail userDetail) {
		log.debug("downloadFile");
		try {

			if (userDetail != null && userDetail.getImagePath() != null)
				service.downloadReportFile(userDetail.getImagePath());
			else {
				model.addAttribute("msgdanger", "Failed to generate QR code!!");
				model.addAttribute("isDisabled", Boolean.FALSE);
			}
			model.addAttribute("userDetail", userDetail);

		} catch (Exception e) {
			log.error("downloadFile", e);
		}
		return "home";
	}

}
