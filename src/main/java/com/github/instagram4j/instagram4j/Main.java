package com.github.instagram4j.instagram4j;

import com.github.instagram4j.instagram4j.exceptions.IGResponseException;
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse;
import com.github.instagram4j.instagram4j.responses.challenge.ChallengeStateResponse;
import com.github.instagram4j.instagram4j.utils.IGChallengeUtils;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        try {
            IGClient instagram = IGClient.builder().username("tranthehoangk60@gmail.com").password("Hoang20012021").build();
            CompletableFuture<LoginResponse> instagramLoginResult = instagram.sendLoginRequest();

            LoginResponse test = instagramLoginResult.exceptionally(tr -> {
                LoginResponse loginResponse =
                        IGResponseException.IGFailedResponse.of(tr.getCause(), LoginResponse.class);
                if (loginResponse.getChallenge() != null) {
                    ChallengeStateResponse getChallengeResult = IGChallengeUtils.requestState(instagram, loginResponse.getChallenge()).join();
                    // If action is close
                    if (getChallengeResult.getAction().equals("close")) {
                        // Get challenge
                        getChallengeResult = IGChallengeUtils.resetChallenge(instagram, loginResponse.getChallenge()).join();
                    }

                    String stepName = getChallengeResult.getStep_name();
                    switch (stepName) {
                        case "select_verify_method":
                            System.out.println("Getting validation code for instagram account with username:");
                            if (!getChallengeResult.getStep_data().getEmail().equals("")) {
                                // Priority send verify code to email (2 choices: email, phone_number)
                                getChallengeResult.getStep_data().setChoice("1");
                            }
                            IGChallengeUtils.selectVerifyMethod(instagram, loginResponse.getChallenge(), getChallengeResult.getStep_data().getChoice(), false);
                            break;
                        case "delta_login_review": {
                            // 'This was me' option
                            LoginResponse response = IGChallengeUtils.selectVerifyMethodDelta(instagram, loginResponse.getChallenge(), "0", false).join();
                            break;
                        }
                        case "submit_phone": {
                            ChallengeStateResponse response = IGChallengeUtils.sendPhoneNumber(instagram, loginResponse.getChallenge(), getChallengeResult.getStep_data().getPhone_number()).join();
                        }
                    }
                }
                return loginResponse;
            }).join();
            System.out.println(test.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
