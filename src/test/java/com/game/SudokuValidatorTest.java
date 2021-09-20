package com.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SudokuValidatorTest {

    private SudokuValidator sudokuValidator;

    @BeforeEach
    public void setup(){
        sudokuValidator = new SudokuValidator();
    }

    @Test
    public void shouldValidateSolutionAsValid() {
        int[][] testInput = getValidTestInput();
        ValidationResult validationResult = sudokuValidator.validateSolution(testInput);
        Assertions.assertEquals(ValidationResultType.VALID, validationResult.getValidationResultType());
    }

    @Test
    public void shouldValidateSolutionAsInvalidValidRow() {
        int[][] testInput = getInvalidValidTestInputWithDuplicateRowData();
        ValidationResult validationResult = sudokuValidator.validateSolution(testInput);
        Assertions.assertEquals(ValidationResultType.INVALID, validationResult.getValidationResultType());
        Optional<Map<ValidationErrorType, List<String>>> errorMap = validationResult.getErrorMap();
        Assertions.assertTrue(errorMap.isPresent());
        System.out.println(errorMap.get());
        Assertions.assertEquals(1,errorMap.get().size());
        Assertions.assertNotNull(errorMap.get().get(ValidationErrorType.INVALID_SIZE));
    }

    @Test
    public void shouldValidateSolutionAsInvalidValidColumn() {
        int[][] testInput = getInvalidValidTestInputWithDuplicateColumnData();
        ValidationResult validationResult = sudokuValidator.validateSolution(testInput);
        Assertions.assertEquals(ValidationResultType.INVALID, validationResult.getValidationResultType());
        Optional<Map<ValidationErrorType, List<String>>> errorMap = validationResult.getErrorMap();
        Assertions.assertTrue(errorMap.isPresent());
        System.out.println(errorMap.get());
        Assertions.assertEquals(1,errorMap.get().size());
        Assertions.assertEquals(2, errorMap.get().get(ValidationErrorType.INVALID_SIZE).size());
    }

    @Test
    @Disabled
    public void shouldValidateSolutionAsInvalidValid3x3Box() {
        int[][] testInput = getInvalidTestInputWithIncorrect3x3Box();
        ValidationResult validationResult = sudokuValidator.validateSolution(testInput);
        Assertions.assertEquals(ValidationResultType.INVALID, validationResult.getValidationResultType());
        Optional<Map<ValidationErrorType, List<String>>> errorMap = validationResult.getErrorMap();
        Assertions.assertTrue(errorMap.isPresent());
        System.out.println(errorMap.get());
        Assertions.assertEquals(1,errorMap.get().size());
        Assertions.assertEquals(2, errorMap.get().get(ValidationErrorType.INVALID_SIZE).size());
    }

    private int[][] getValidTestInput() {
        return new int[][] {
                {6, 3, 9, 5, 7, 4, 1, 8, 2},
                {5, 4, 1, 8, 2, 9, 3, 7, 6},
                {7, 8, 2, 6, 1, 3, 9, 5, 4},
                {1, 9, 8, 4, 6, 7, 5, 2, 3},
                {3, 6, 5, 9, 8, 2, 4, 1, 7},
                {4, 2, 7, 1, 3, 5, 8, 6, 9},
                {9, 5, 6, 7, 4, 8, 2, 3, 1},
                {8, 1, 3, 2, 9, 6, 7, 4, 5},
                {2, 7, 4, 3, 5, 1, 6, 9, 8}
        };
    }

    private int[][] getInvalidValidTestInputWithDuplicateRowData() {
        return new int[][] {
                {6, 3, 9, 5, 7, 4, 1, 8, 2},
                {5, 4, 1, 8, 2, 9, 3, 7, 6},
                {7, 8, 2, 6, 1, 3, 9, 5, 4},
                {1, 9, 8, 4, 6, 7, 5, 2, 3},
                {3, 6, 5, 9, 8, 2, 4, 1, 7},
                {4, 2, 7, 1, 3, 5, 8, 6, 9},
                {9, 5, 6, 7, 4, 8, 2, 3, 1},
                {8, 1, 3, 2, 9, 6, 7, 4, 5},
                {8, 7, 4, 3, 5, 1, 6, 9, 8}
        };
    }

    private int[][] getInvalidValidTestInputWithDuplicateColumnData() {
        return new int[][] {
                {6, 3, 9, 5, 7, 4, 1, 8, 2},
                {5, 4, 1, 8, 2, 9, 3, 7, 6},
                {7, 8, 2, 6, 1, 3, 9, 5, 4},
                {1, 9, 8, 4, 6, 7, 5, 2, 3},
                {3, 6, 5, 9, 8, 2, 4, 1, 7},
                {4, 2, 7, 1, 3, 5, 8, 6, 9},
                {9, 5, 6, 7, 4, 8, 2, 3, 1},
                {8, 1, 3, 2, 9, 6, 7, 4, 5},
                {8, 7, 4, 3, 5, 1, 6, 9, 2}
        };
    }

    private int[][] getInvalidTestInputWithIncorrect3x3Box() {
        return new int[][] {
                {6, 3, 9, 5, 7, 4, 1, 8, 2},
                {5, 4, 1, 8, 6, 9, 3, 7, 1},
                {7, 8, 6, 2, 1, 3, 9, 5, 4},
                {1, 9, 8, 4, 2, 7, 5, 6, 3},
                {3, 2, 5, 9, 8, 6, 4, 1, 7},
                {4, 6, 7, 1, 3, 5, 8, 2, 9},
                {9, 5, 2, 7, 4, 8, 2, 3, 6},
                {8, 1, 3, 6, 9, 2, 7, 4, 5},
                {2, 7, 4, 3, 5, 1, 6, 9, 8}
        };
    }
}
