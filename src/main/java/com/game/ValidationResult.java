package com.game;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ValidationResult {

    private ValidationResultType validationResultType;
    private Optional<Map<ValidationErrorType, List<String>>> errorMap;

    public ValidationResult(ValidationResultType validationResultType) {
        this.validationResultType = validationResultType;
        this.errorMap = Optional.empty();
    }

    public ValidationResult(final ValidationResultType validationResultType, final Map<ValidationErrorType, List<String>> errorMap) {
        this.validationResultType = validationResultType;
        this.errorMap = Optional.of(Collections.unmodifiableMap(errorMap));
    }

    public ValidationResultType getValidationResultType() {
        return validationResultType;
    }

    public Optional<Map<ValidationErrorType, List<String>>> getErrorMap() {
        return errorMap;
    }
}
