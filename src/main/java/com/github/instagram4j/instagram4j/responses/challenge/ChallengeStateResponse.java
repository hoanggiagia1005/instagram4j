package com.github.instagram4j.instagram4j.responses.challenge;

import com.github.instagram4j.instagram4j.responses.IGResponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChallengeStateResponse extends IGResponse {
    private String step_name;
    private StepData step_data;
    private String action;
    private long user_id;
    private String nonce_code;

    @Getter
    @Setter
    public static class StepData {
        private String choice;
        private String fb_access_token;
        private String big_blue_token;
        private boolean google_oauth_token;
        private String phone_number;
        private String email;
        private String security_code;
        private int resend_delay;
        private int sms_resend_delay;
        private String contact_point;
        private String form_type;
        private String phone_number_preview;
        private String country;
        private long enrollment_time;
        private String enrollment_date;
        private double latitude;
        private double longitude;
        private String city;
        private String platform;
        private String user_agent;
    }
}
