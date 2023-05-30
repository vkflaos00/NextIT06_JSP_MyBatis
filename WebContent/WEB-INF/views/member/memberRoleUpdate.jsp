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
	<c:if test="${ bne eq null and de eq null }">
		<h3>회원권한 수정 성공</h3>
		<div>
			<p>정상적으로 회원권한이 수정 되었습니다. 확인을 클릭하시면 회원목록 게시판으로 이동합니다.</p>
			<div class="btn-area">
				<button type="button" onclick="location.href='${pageContext.request.contextPath}/member/memberList.do'">확인</button>
			</div>
		</div>
	</c:if>
	
<!--회원권한수정09-2		실패처리  -->
	<c:if test="${ bne ne null or de ne null }">
		<h3>회원권한 수정 실패</h3>
		<div>
			<p>회원권한 수정에 실패하였습니다. 전산실에 문의 부탁드립니다. 042-719-8850</p>
			<div class="btn-area">
				<button type="button" onclick="history.back()">뒤로가기</button>
			</div>
		</div>
	</c:if>
</div>
</body>
</html>