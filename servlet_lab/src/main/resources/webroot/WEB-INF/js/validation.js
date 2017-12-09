$(document).ready(function () {

    $(this).find("textarea").on("input change propertychange", function () {
        var errorMessage = $(this).attr("title") || "Please match the requested format.";
        var pattern = $(this).attr("pattern");
        if (typeof pattern !== typeof undefined && pattern !== false) {
            var patternRegex = new RegExp("^" + pattern.replace(/^\^|\$$/g, '') + "$", "g");

            hasError = !$(this).val().match(patternRegex);

            if (typeof this.setCustomValidity === "function") {
                this.setCustomValidity(hasError ? errorMessage : "");
            }
            else {
                $(this).toggleClass("error", !!hasError);
                $(this).toggleClass("ok", !hasError);
            }
        }

    });


});