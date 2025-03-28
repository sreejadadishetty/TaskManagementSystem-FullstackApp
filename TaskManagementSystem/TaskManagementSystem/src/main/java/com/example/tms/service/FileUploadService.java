package com.example.tms.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.tms.entity.Role;
import com.example.tms.entity.User;

@Service
public class FileUploadService {

    public List<User> parseCSVFile(MultipartFile file) {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord record : csvParser) {
                User user = new User();
                
                
                if (!record.isMapped("name") || !record.isMapped("email") || !record.isMapped("password")) {
                    throw new RuntimeException("CSV file headers are incorrect!");
                }

                user.setName(record.get("name").trim());
                user.setEmail(record.get("email").trim());
                user.setPassword(record.get("password").trim());
                
                
                String roleValue = record.get("role").trim().toUpperCase();
                try {
                    user.setRole(Role.valueOf(roleValue)); 
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid role in CSV file: " + roleValue);
                }

                user.setEnabled(true);
                users.add(user); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
        return users;
    }
}
