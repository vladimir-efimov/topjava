<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>

    <table border=1>
           <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th>Excess</th>
                </tr>
            </thead>
            <tbody>
                       <!--
                        <td><fmt:formatDate pattern="yyyy-MMM-dd hh:mm" value="${meal.dateTime}" /></td>
                       -->
                <c:forEach items="${meals}" var="meal">
                    <tr>
                        <td><c:out value="${meal.dateTime}" /></td>
                        <td><c:out value="${meal.description}" /></td>
                        <td><c:out value="${meal.calories}" /></td>
                        <td><c:out value="${meal.excess}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
    </table>

</body>
</html>