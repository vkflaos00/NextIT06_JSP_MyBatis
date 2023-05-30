<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NextIT</title>
<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath }/images/nextit_log.jpg"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/member.css">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

</head>
<body>

<div class="container">

	<c:if test="${ bne eq null and bpn eq null and bnf eq null and de eq null }">
		<h3>회원정보 수정 성공</h3>
		<div>
			<p>정상적으로 회원정보가 수정 되었습니다. 확인을 클릭하시면 매인페이지로 이동.</p>
			<div class="btn-area">
				<button type="button" onclick="location.href='${pageContext.request.contextPath}/home/home.do'">확인</button>
			</div>
		</div>
	</c:if>
	
	<c:if test="${ bpn ne null }">
		<h3>회원정보 수정  실패</h3>
		<div>
			<p>입력하신 패스워드가 올바르지 않습니다. 다시 입력 부탁드립니다.</p>
			<div class="btn-area">
				<button type="button" onclick="history.back()">뒤로가기</button>
			</div>
		</div>
	</c:if>
	
	<c:if test="${ bne ne null or bnf ne null or de ne null }">
		<h3>회원정보 수정  실패</h3>
		<div class="alert alert-warning">
			<p> 회원 등록에 실패하였습니다. 전산실에 문의 부탁드립니다. 042-719-8850</p>
			<div class="btn-area">
				<button type="button" onclick="history.back();">뒤로가기</button>
			</div>
		</div>
	</c:if>
</div>
</body>
</html>