<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@	taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath }/images/nextit_log.jpg" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/member.css">
</head>
<body>
<div class="container">
	<c:if test="${bne eq null and bde eq null and de eq null }">
		<h3>회원등록 성공</h3>
		<div class="alert alert-success">
			<p>정상적으로 회원 등록 되었습니다. 확인 클릭하시면 로그인 페이지로 이동합니다.</p>
			<div class="btn-area">
				<button type="button" 
					onclick="location.href='${pageContext.request.contextPath}/login/login.do'">확인</button>
			</div>
		</div>
	</c:if>
	<c:if test="${bde ne null }">
		<h3>회원등록 실패</h3>
		<div class="alert alert-warning">
			<p>회원등록에 실패하였습니다. 이미 사용중인 아이디입니다. 다른 아이디를 사용해주세요</p>
			<div class="btn-area">
				<button type="button" onclick="history.back();">뒤로가기</button> 
			</div>
		</div>	
	</c:if>
	
	<c:if test="${bne ne null or de ne null }">
		<h3>회원등록 실패</h3>
		<div class="alert alert-warning">
			<p>회원등록에 실패하였습니다. 전산실에 문의 부탁드립니다. 042-719-8850</p>
			<div class="btn-area">
				<button type="button" onclick="history.back();">뒤로가기</button> 
			</div>
		</div>	
	</c:if>
</div>
</body>
</html>