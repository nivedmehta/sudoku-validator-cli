package com.game;

/**
 * This class validates a sudoku solution
 */
public class SudokuValidatorApplication {

    public static void main(String[] args) throws FileConversionException {
        SudokuValidator sudokuValidator = new SudokuValidator();
        FileToArrayConvertor fileToArrayConvertor = new FileToArrayConvertor();

        validateSolution(args, sudokuValidator, fileToArrayConvertor);
    }

    private static void validateSolution(String[] args, SudokuValidator sudokuValidator, FileToArrayConvertor fileToArrayConvertor) throws FileConversionException {
        int[][] values = fileToArrayConvertor.convert(args);
        ValidationResult validationResult = sudokuValidator.validateSolution(values);

        if (validationResult.getValidationResultType() == ValidationResultType.VALID) {
            System.out.println("Valid solution");
        } else {
            String errorMsg = String.format("%s:%s", validationResult.getValidationResultType(), validationResult.getErrorMap().isPresent() ? validationResult.getErrorMap().get() : "Invalid File");
            throw new RuntimeException(errorMsg);
        }
    }

}
