<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Pagina dei Risultati</title>
	 </head>
	 
	<body class="d-flex flex-column h-100">
	 
		<!-- Fixed navbar -->
		<jsp:include page="../navbar.jsp"></jsp:include>
	 
	
		<!-- Begin page content -->
		<main class="flex-shrink-0">
		  <div class="container">
		  
		  
				<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>	
				  
		  		<div class="alert alert-success alert-dismissible fade show  ${successMessage==null?'d-none':'' }" role="alert">
				  ${successMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
				<div class="alert alert-danger alert-dismissible fade show d-none" role="alert">
				  Esempio di operazione fallita!
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
				<div class="alert alert-info alert-dismissible fade show d-none" role="alert">
				  Aggiungere d-none nelle class per non far apparire
				   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
		  
		  
		  
		  		<div class='card'>
				    <div class='card-header'>
				        <h5>Lista dei risultati</h5> 
				    </div>
				    <div class='card-body'>
				    	<a class="btn btn-primary " href="${pageContext.request.contextPath}/satellite/insert">Add New</a>
				    
				        <div class='table-responsive'>
				            <table class='table table-striped ' >
				                <thead>
				                    <tr>
			                         	<th>denominazione</th>
				                        <th>codice</th>
				                        <th>data di lancio</th>
				                        <th>Data di rientro</th>
				                        <th>Stato</th>
				                        <th>Azioni</th>
				                    </tr>
				                </thead>
				                <tbody>
				                	<c:forEach items="${impiegato_list_attribute }" var="impiegatoItem">
										<tr>
											<td>${impiegatoItem.denominazione }</td>
											<td>${impiegatoItem.codice }</td>
											<td><fmt:formatDate type = "date" value = "${impiegatoItem.dataLancio }" /></td>
											<td><fmt:formatDate type = "date" value = "${impiegatoItem.dataRientro }" /></td>
											<td>${impiegatoItem.stato }</td>
											<td>
												<a class="btn  btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/satellite/show/${impiegatoItem.id }">Visualizza</a>
												<a class="btn  btn-sm btn-outline-primary ml-2 mr-2" href="${pageContext.request.contextPath}/satellite/update/${impiegatoItem.id }">Edit</a>
												<c:if test="${impiegatoItem.dataRientro != null}">
													<a class="btn btn-outline-danger btn-sm" href="${pageContext.request.contextPath}/satellite/delete/${impiegatoItem.id }">Delete</a>
												</c:if>
												<div class="btn-group">
													<div>
														<c:if test="${impiegatoItem.dataLancio == null}">
															<form method="post" action="${pageContext.request.contextPath}/satellite/lancia">
																<button type="submit" name="submit" value="submit" id="submit" class="btn btn-outline-primary btn-sm">Lancia</button>
																<input type="hidden" name="idSatellite" value="${impiegatoItem.id }">
															</form>
														</c:if>
													</div>
													<div style="margin-left: 3px">
														<c:if test="${impiegatoItem.dataRientro == null}">
															<form method="post" action="${pageContext.request.contextPath}/satellite/rientro">
																<button type="submit" name="submit" value="submit" id="submit" class="btn btn-outline-primary btn-sm">Rientra</button>
																<input type="hidden" name="idSatellite" value="${impiegatoItem.id }">
															</form>
														</c:if>
													</div>
												
												</div>
												
											</td>
										</tr>
									</c:forEach>
				                </tbody>
				            </table>
				        </div>
				        
				        <div class="col-12">
							<a href="${pageContext.request.contextPath}/satellite/search" class='btn btn-outline-secondary' style='width:80px'>
					            <i class='fa fa-chevron-left'></i> Back 
					        </a>
						</div>
				   
					<!-- end card-body -->			   
			    </div>
			<!-- end card -->
			</div>	
		 
		   
		 <!-- end container -->  
		  </div>
		  
		</main>
		
		<!-- Footer -->
		<jsp:include page="../footer.jsp" />
		
	</body>
</html>