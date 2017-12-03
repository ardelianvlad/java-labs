<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<h4>Storages: </h4>
<ul>
    <c:forEach items="${storages}" var="storage">
        <li><a href="/storage/${storage.id}">${storage.name}</a></li>
    </c:forEach>
</ul>
<h4>Products: </h4>
<ul>
    <c:forEach items="${products}" var="product">
        <li><a href="/product/${product.id}">${product.name}</a></li>
    </c:forEach>
</ul>
<jsp:include page="../includes/end.jsp"/>