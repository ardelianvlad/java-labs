<%@ page import="java.time.LocalDate" %>
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
        <form action="/product/edit/${id}" method="post">
            <div class="form-group row">
                <label for="name" class="col-sm-2 col-form-label">Name: </label>
                <div class="col-sm-10">
                    <input type="text" id="name" required pattern="([A-Z][A-Za-z1-9 ]{0,100}?)"
                           title="Name can contain only letters and numbers." class="form-control" name="name"
                           placeholder="Enter name" value="${product.name}">
                </div>
            </div>
            <div class="form-group row">
                <label for="category" class="col-sm-2 col-form-label">Category: </label>
                <div class="col-sm-10">
                    <select class="form-control" id="category" name="category">
                        <c:forEach items="${categories}" var="category">
                            <c:choose>
                                <c:when test="${category==product.category}">
                                    <option selected>${category}</option>
                                </c:when>
                                <c:otherwise>
                                    <option>${category}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label for="production_date" class="col-sm-2 col-form-label">Production date: </label>
                <div class="col-sm-10">
                    <input type="date" id="production_date" max="<%=LocalDate.now()%>" required class="form-control"
                           name="production_date" value="${product.productionDate}">
                </div>
            </div>
            <div class="form-group row">
                <label for="expiration_date" class="col-sm-2 col-form-label">Expiration date: </label>
                <div class="col-sm-10">
                    <input type="date" id="expiration_date" required class="form-control"
                           name="expiration_date" value="${product.expiration}">
                </div>
            </div>
            <div class="form-group row">
                <label for="price" class="col-sm-2 col-form-label">Price: </label>
                <div class="col-sm-10">
                    <input type="text" id="price" required pattern="(\d*[\.,]\d*)|(\d*)"
                           title="Price can contain only numbers." class="form-control" name="price"
                           placeholder="Enter price" value="${product.price}">
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </c:when>
    <c:otherwise>
        <div class="alert alert-danger" role="alert">There is no product with id ${id}!</div>
    </c:otherwise>
</c:choose>
<jsp:include page="../includes/end.jsp"/>
<script src="/static/js/validation.js"></script>
<script>
    var compareDateCheck = function() {
        var startDate = Date.parse($('#production_date').val());
        var endDate = Date.parse($('#expiration_date').val());
        console.log(startDate, endDate);
        if (startDate > endDate) {
            $('#alert').html('<div class="alert alert-danger" role="alert">Production date must be less than expiration date.</div>');
        }
    };

    $(document).ready(function () {
        $('#production_date').change(compareDateCheck);
        $('#expiration_date').change(compareDateCheck);
    });
</script>