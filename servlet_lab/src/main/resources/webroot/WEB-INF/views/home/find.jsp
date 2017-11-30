<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<h4>Storages: </h4>
<ul>
    <%--@elvariable id="storages" type="java.util.List<models.Storage>"--%>
    <c:forEach items="${storages}" var="storage">
        <li><a href="/storage/${storage.id}">${storage.name}</a></li>
    </c:forEach>
</ul>
<h4>Products: </h4>
<ul>
    <%--@elvariable id="faculties" type="java.util.List<models.Faculty>"--%>
    <c:forEach items="${products}" var="product">
        <li><a href="/product/${product.id}">${product.name}</a></li>
    </c:forEach>
</ul>
<jsp:include page="../includes/end.jsp"/>