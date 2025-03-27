import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MD5HashGenerator {
    public static void main(String[] args) {
        try {
            // Read JSON file
            byte[] jsonData = Files.readAllBytes(Paths.get("input.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);

            // Extract first_name and roll_number
            String firstName = rootNode.get("student").get("first_name").asText().toLowerCase();
            String rollNumber = rootNode.get("student").get("roll_number").asText().toLowerCase();

            // Concatenate and generate MD5 hash
            String inputString = firstName + rollNumber;
            String md5Hash = generateMD5Hash(inputString);

            // Write to output.txt
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
                writer.write(md5Hash);
            }

            System.out.println("MD5 Hash generated: " + md5Hash);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to generate MD5 hash
    private static String generateMD5Hash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        byte[] digest = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
