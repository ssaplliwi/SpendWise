package com.example.spendwise.ai;

import com.example.spendwise.model.Transaction;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AIInterface {
    @POST("analyze")
    Call<String> getAdvice(@Body List<Transaction> transactions);
}