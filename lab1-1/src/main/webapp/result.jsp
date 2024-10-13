<%--
  Created by IntelliJ IDEA.
  User: tedy
  Date: 10.10.2024
  Time: 08:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Result</title>
</head>
<body>
    <c:forEach var="item" items = "${lines}" >
      <c:out value="${item}"/> <br/>
    </c:forEach>
</body>
</html>
