/**
 * 
 */
package com.star.sud.web.service;

import com.star.sud.web.dto.UserDetail;
import com.star.sud.web.status.ReportStatus;

/**
 * @author Sudarshan
 *
 */
public interface IGenerateJasperService {

	/**
	 * @param userDetail
	 * @return 
	 */
	ReportStatus generateBasicReport(UserDetail userDetail);

	/**
	 * @param imagePath
	 */
	void downloadReportFile(String imagePath);

}
