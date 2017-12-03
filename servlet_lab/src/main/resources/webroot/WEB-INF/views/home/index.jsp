<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../includes/start.jsp"/>

<a class="btn btn-primary" href="/storage/add/">Add new storage</a>

<c:choose>
    <c:when test="${storages.size() == 0}">
        <div class="alert alert-danger" role="alert">There are no storage. Add one to begin.</div>
    </c:when>
    <c:otherwise>
        <h3>Current storage:</h3>
        <ul>
            <c:forEach items="${storages}" var="storage">
                <li><a href="/storage/${storage.id}">${storage.name}</a></li>
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>

<jsp:include page="../includes/end.jsp"/>