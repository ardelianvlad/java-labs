<jsp:useBean id="storage" scope="request" type="models.Storage"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<c:choose>
    <c:when test="${storage==null}">
        <div class="alert alert-danger" role="alert">There is no storage with id ${id}!</div>
    </c:when>
</c:choose>
<a class="btn btn-warning" role="button" href="/storage/edit/${id}">Edit</a>
<form action="/storage/delete/${id}" method=post style="display:inline;">
    <button class="btn btn-danger" type="submit" role="button">Delete</button>
</form>
<a class="btn btn-primary" role="button" href="/product/add/${id}">Add new product</a>
<p></p>
<h3 class="col-sm-9">${storage.name}</h3>
<p></p>
<ul class="list-group">
    <c:forEach var="product" items="${storage.products}">
        <li class="list-group-item"><a href="/product/${product.id}">${product.name}</a></li>
    </c:forEach>
</ul>
<jsp:include page="../includes/end.jsp"/>