<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Jasper Report Generation</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon"
	href="<c:url value="/resources/images/sudarshan-logo.png"/>" />
<link rel="shortcut icon"
	href="<c:url value="/resources/images/ico/favicon.ico"/>"
	type="image/x-icon">

<style type="text/css">
.page-title {
	font-family: 'Open Sans', sans-serif;
	font-style: normal;
	font-weight: 600;
	font-size: 24px;
	color: #4C4C4C;
}

.mt-1 {
	margin-top: 1rem !important;
}

.mb-2, .my-2 {
	margin-bottom: 0.5rem !important;
}
</style>

</head>

<body>
	<div id="fullpage">

		<div class="header">
			<%@include file="../views/common/welcome-header.jspf"%>
		</div>

		<div>
			<%@include file="../views/common/message.jspf"%>
		</div>

		<%@include file="../views/header-content.jsp"%>


		<div class="container">
			<div class="row">

				<div class="col-7">
					<div class="page-title">Select the Below Option to Generate
						the Report</div>
				</div>

			</div>
		</div>

		<div class="container">
			<div class="row">


				<form:form
					action="${pageContext.request.contextPath}/generateReport"
					method="post" modelAttribute="userDetail">

					<div class="col-12 row" style="margin-top: 25px;">

						<div class="col-12">
							<div class="border-bottom mt-1 mb-2"></div>
						</div>


						<div class="col-sm-6">

							<label for="usrName">User Name</label> <input id="usrName"
								name="usrName" class="form-control required" required="required"></input>
							<label for="usrNatId">User National Id</label> <input
								id="usrNatId" name="usrNatId" class="form-control required"
								required="required"></input> <label for="usrDob">User
								Date of Birth</label> <input id="usrDob" type="date" name="usrDob"
								class="form-control" required="required"></input> <label
								for="usrDob">Email</label> <input id="usrEmail" name="usrEmail"
								class="form-control required" required="required"></input> <label
								for="userContact">Contact</label> <input id="userContact"
								name="userContact" class="form-control required"
								required="required"></input>

								<label for="address1">Primary Address</label>
								<textarea id="address[0].addName" name="address[0].addName"
									class="form-control required" required="required"></textarea>
									
								<input type="hidden" name="address[0].addType" id="address[0].addType"
										value="PRIMARY">
										
								<input type="hidden" name="address[0].slNo" id="address[0].slNo"
										value="1">				
										
								<input type="hidden" name="address[1].addType" id="address[1].addType"
										value="SECONDARY">
								<input type="hidden" name="address[1].slNo" id="address[1].slNo"
										value="2">	
								
								<label for="address2">Secondary Address</label>
								<textarea id="address[1].addName" name="address[1].addName"
									class="form-control required" required="required"></textarea>
									
								
						</div>


						<div class="col-sm-12">
							<div class="border-bottom mt-1 mb-2"></div>
						</div>

						<div class="col-sm-12">
							<button type="submit" class="btn btn-info">Submit</button>
							<button type="reset" class="btn btn-danger">Cancel</button>
							<c:if test="${isDisabled !=null && isDisabled}">
								<a href="${pageContext.request.contextPath}/"
									class="btn btn-info">Go Back</a>
							</c:if>
						</div>

					</div>

				</form:form>


				<c:if test="${isDisabled !=null && isDisabled}">

					<div class="container" style="margin-top: 40px;">
						<div class="row">

							<div class="col-7">
								<div class="page-title">Generated CSV File</div>
							</div>

							<form:form action="${pageContext.request.contextPath}/download"
								method="post" modelAttribute="userDetail">
								<div>

									<input type="hidden" name="imagePath" id="imagePath"
										value="${userDetail.imagePath}">
									<div class="col-sm-12">
										<button type="submit" class="btn btn-success">Click
											Here to Download</button>
									</div>
								</div>
							</form:form>

						</div>
					</div>
				</c:if>

			</div>
		</div>

	</div>

	<div>
		<div class="col-lg-12 col-sm-12 col-xs-12 copyrights">
			<%@include file="../views/login-footer.jsp"%>
		</div>
	</div>

</body>
</html>