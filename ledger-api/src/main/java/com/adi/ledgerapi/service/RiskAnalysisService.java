package com.adi.ledgerapi.service;

import com.adi.ledgerapi.dto.TransferRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Service
public class RiskAnalysisService {

    private final RestClient restClient;

    public RiskAnalysisService(RestClient restClient) {
        this.restClient = restClient;
    }

    public boolean isTransferApproved(TransferRequest request) {
        try {
            Map<String, Object> response = restClient.post()
                    .uri("/api/v1/analyze-transfer")
                    .body(request)
                    .retrieve()
                    .body(Map.class);

            if (response != null && Boolean.TRUE.equals(response.get("approved"))) {
                return true;
            }
            System.out.println("Transfer blocked by Python: " + response.get("reason"));
            return false;

        } catch (Exception e) {
            System.err.println("Failed to contact Python Risk Service: " + e.getMessage());
            return false;
        }
    }
}