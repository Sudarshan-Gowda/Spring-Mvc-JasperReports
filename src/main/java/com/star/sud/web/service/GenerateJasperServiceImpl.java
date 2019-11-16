/**
 * 
 */
package com.star.sud.web.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.star.sud.web.dto.Address;
import com.star.sud.web.dto.UserDetail;
import com.star.sud.web.status.ReportStatus;
import com.star.sud.web.status.ReportStatus.STATUS;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * @author Sudarshan
 *
 */
@Service("generateJasperService")
public class GenerateJasperServiceImpl implements IGenerateJasperService {
	
	//Static Attributes
	///////////////////////
	private static final Logger log = Logger.getLogger(GenerateJasperServiceImpl.class);
	
	//Attributes
	////////////////
	@Autowired
	protected HttpServletResponse response;

	public ReportStatus generateBasicReport(UserDetail userDetail) {
		log.debug("generateBasicReport");
		ReportStatus status = new ReportStatus();
		try {
			JasperReport report = (JasperReport) JRLoader
					.loadObject(ResourceUtils.getFile("classpath:User-Details.jasper"));

			if (userDetail == null)
				userDetail = new UserDetail();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("USR_NAME", userDetail.getUsrName() != null ? userDetail.getUsrName() : "-");
			params.put("USR_NAT_ID", userDetail.getUsrNatId() != null ? userDetail.getUsrNatId() : "-");
			params.put("USR_DOB", userDetail.getUsrDob() != null ? userDetail.getUsrDob() : "-");
			params.put("USR_EMAIL", userDetail.getUsrEmail() != null ? userDetail.getUsrEmail() : "-");
			params.put("USR_CONTACT", userDetail.getUserContact() != null ? userDetail.getUserContact() : "-");

			if (userDetail.getAddress() == null || userDetail.getAddress().size() <= 0)
				userDetail.setAddress(new ArrayList<Address>());

			JRBeanCollectionDataSource amendDataSource = new JRBeanCollectionDataSource(userDetail.getAddress());
			params.put("ADDRESS_LIST", amendDataSource);

			String filePath = getTempFilePath();

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			FileOutputStream out = new FileOutputStream(filePath);

			JasperPrint print = JasperFillManager.fillReport(report, params, amendDataSource);

			// new JREmptyDataSource()
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
			exporter.exportReport();
			out.write(os.toByteArray());
			out.close();
			status.setStatus(STATUS.SUCCESS);
			status.setResult(filePath);
		} catch (Exception e) {
			log.error("generateBasicReport", e);
			status.setStatus(STATUS.FAILED);
		}
		return status;
	}

	private static String getTempFilePath() throws Exception {
		File temp = File.createTempFile("User_Details_Report", ".pdf");
		String absolutePath = temp.getAbsolutePath();
		return absolutePath;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.star.sud.web.service.IGenerateJasperService#downloadReportFile(java.lang.
	 * String)
	 */
	public void downloadReportFile(String filePath) {
		ServletOutputStream os = null;
		try {

			if (null == filePath)
				throw new Exception("filePath para is null or empty");

			File file = new File(filePath);

			os = response.getOutputStream();

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + file.getName() + ".pdf");

			byte[] imgByArry = Files.readAllBytes(file.toPath());
			os.write(imgByArry);

		} catch (Exception e) {
			log.error("downloadCsvFile", e);
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (Exception ex) {
					log.error("error closing output stream");
				}
			}

		}
	}

}
