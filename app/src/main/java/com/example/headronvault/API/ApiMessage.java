package com.example.headronvault.API;
import com.google.gson.annotations.SerializedName;

public class ApiMessage
{
    @SerializedName("Message")
    public String message;
    @SerializedName("Cause")
    public String cause;
}
