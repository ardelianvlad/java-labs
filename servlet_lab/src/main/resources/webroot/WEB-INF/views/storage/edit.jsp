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
            <div class="form-group row">
                <label for="name" class="col-sm-2 col-form-label">Name: </label>
                <div class="col-sm-10">
                    <input type="text" id="name" required pattern="^[A-Z].{0,100}$"
                           title="Name can contain only letters." class="form-control" name="name"
                           placeholder="Enter name" value="${storage.name}">
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </c:otherwise>
</c:choose>
<jsp:include page="../includes/end.jsp"/>