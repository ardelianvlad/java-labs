<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="storage" scope="request" type="models.Storage"/>
<jsp:include page="../includes/start.jsp"/>
<c:if test="${error}">
    <div class="alert alert-danger" role="alert">
        Wrong input.
    </div>
</c:if>
<c:choose>
    <c:when test="${storage==null}">
        <div class="alert alert-danger" role="alert">There is no storage with id ${id}!</div>
    </c:when>
    <c:otherwise>
        <form action="/storage/edit/${id}" method="post">

            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </c:otherwise>
</c:choose>
<jsp:include page="../includes/end.jsp"/>
<script src="/static/js/validation.js"></script>