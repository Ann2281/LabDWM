<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page import="domain.Role"%>

<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<title>Должности</title>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Roles</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<!-- jQuery -->
	<script defer src="js/jquery-3.6.4.js"></script>
	<!-- Bootstrap JS + Popper JS -->
	<script defer src="js/bootstrap.min.js"></script>
</head>

<body>
	<div class="container-fluid">
		<jsp:include page="/views/header.jsp" />
		<div class="container-fluid">
			<div class="row justify-content-start ">
				<div class="col-6 border bg-light px-4">
					<h3>Список должностей</h3>
					<table class="table">
						<thead>
							<th scope="col">Код</th>
							<th scope="col">Должность</th>
						</thead>
						<tbody>
							<c:forEach var="role" items="${roles}">
								<tr>
									<td>${role.getId()}</td>
									<td>${role.getNamerole()}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<div class="col-6 border px-4">
					<form method="POST" action="">
						<h3>Редактировать должность</h3> <br> <br>
						<div class="mb-3 row"> 
							<label for="idrole" class="col-sm-3 col-form-label"> Код должности</label>
							<div class="col-sm-6"> 
								<input type="text" class="form-control" readonly value="${roleEdit.getId()}" /> 
							</div>
						</div>
						<div class="mb-3 row"> <br> 
							<label for="inputRole" class="col-sm-3 col-form-label">Должность</label>
							<div class="col-sm-6"> 
								<input type="text" name="inputRole" class="form-control" value="${roleEdit.getNamerole()}" id="personRole" /> 
							</div>
						</div>
						<p> <br> <br> <br> 
							<button type="submit" class="btn btn-primary">Редактировать</button> 
							<a href='<c:url value="/roles" />' role="button" class="btn btn-secondary">Отменить</a> 
							<br> 
						</p>
					</form>
				</div>
			</div>
		</div>
		<jsp:include page="/views/footer.jsp" /> </div>
</body>

</html>