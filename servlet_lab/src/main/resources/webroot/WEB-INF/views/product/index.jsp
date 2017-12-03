<jsp:useBean id="product" scope="request" type="models.Product"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<c:choose>
    <c:when test="${product==null}">
        <div class="alert alert-danger" role="alert">There is no product with id ${id}!</div>
    </c:when>
    <c:otherwise>
        <a class="btn btn-warning" role="button" href="/product/edit/${id}">Edit</a>
        <form action="/product/delete/${id}" method=post style="display:inline;">
            <button class="btn btn-danger" type="submit" role="button">Delete</button>
        </form>
        <p></p>
        <dl class="row">
            <dt class="col-sm-3">Name:</dt>
            <dd class="col-sm-9">${product.name}</dd>
            <dt class="col-sm-3">Category:</dt>
            <dd class="col-sm-9">${product.category}</dd>
            <dt class="col-sm-3">Price:</dt>
            <dd class="col-sm-9">${product.price}</dd>
            <dt class="col-sm-3">Production date:</dt>
            <dd class="col-sm-9">${product.productionDate}</dd>
            <dt class="col-sm-3">Expiration date:</dt>
            <dd class="col-sm-9">${product.expiration}</dd>
        </dl>
    </c:otherwise>
</c:choose>
<jsp:include page="../includes/end.jsp"/>