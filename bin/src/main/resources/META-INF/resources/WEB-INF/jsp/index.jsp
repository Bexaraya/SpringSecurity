<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head><title>Hello :: Spring Application</title></head>
  <body>
    <h1>Hello - Spring Application</h1>
    <p>Are you logged in ? <c:out value="${login}"/></p>
    <p>nickname: <c:out value="${nickname}"/></p>
    <p>message: <c:out value="${message}"/></p>
  </body>
</html>