<jsp:useBean id="product" scope="request" type="models.Product"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>
<c:if test="${error}">
    <div class="alert alert-danger" role="alert">
        Wrong input.
    </div>
</c:if>
<c:choose>
    <c:when test="${product!=null}">
        <form action="/department/edit/${id}" method="post">

            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </c:when>
    <c:otherwise>
        <div class="alert alert-danger" role="alert">There is no product with id ${id}!</div>
    </c:otherwise>
</c:choose>
<jsp:include page="../includes/end.jsp"/>