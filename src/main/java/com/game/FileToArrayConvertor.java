package com.game;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileToArrayConvertor {

    private static final int ARRAY_SIZE = 9;

    public int[][] convert(String[] args) throws FileConversionException {
        validateArgument(args);
        String filePath = args[0];
        validateFileIsReadable(filePath);

        try {
            Stream<String> lines = Files.lines(Paths.get(filePath));
            return lines.map(line -> {
                line = line.trim();
                validateLine(line, filePath);
                return Arrays.stream(line.split(",")).mapToInt(value -> Integer.parseInt(value.trim())).toArray();
            }).limit(ARRAY_SIZE).toArray(int[][]::new);
        } catch (Exception e) {
            throw new FileConversionException("Invalid File @" + filePath, e);
        }
    }

    private void validateLine(String line, String filePath) {
        boolean matches = line.matches("^([0-9][,][\\s]*){8}[0-9]$");
        if (!matches) {
            throw new RuntimeException("Invalid File content. Expected format 1,2,3,4,5,6,7,8,9 while got content in file [" + filePath + "] as [" + line + "]");
        }
    }

    private void validateFileIsReadable(String filePath) throws FileConversionException {
        boolean readable = Files.isReadable(Paths.get(String.valueOf(filePath)));
        if (!readable) {
            throw new FileConversionException("Invalid input arguments. File [" + filePath + "] not found or readable.");
        }
    }

    private void validateArgument(String[] args) throws FileConversionException {
        if (args.length != 1) {
            throw new FileConversionException("Invalid input arguments. Required command validate.bat 'fileName'");
        }
    }
}
