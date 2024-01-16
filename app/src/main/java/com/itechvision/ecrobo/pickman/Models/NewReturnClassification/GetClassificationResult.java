package com.itechvision.ecrobo.pickman.Models.NewReturnClassification;

import com.google.gson.annotations.SerializedName;

public class GetClassificationResult {

    @SerializedName("code")
    private String  code;

    @SerializedName("message")
    private String  message;

    @SerializedName("return_class")
    private String  return_class;

    @SerializedName("classification_g")
    private String  classification_g;

    @SerializedName("classification_b")
    private String  classification_b;


    public GetClassificationResult(String code, String message, String return_class, String classification_g, String classification_b) {
        this.code = code;
        this.message = message;
        this.return_class = return_class;
        this.classification_g = classification_g;
        this.classification_b = classification_b;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturn_class() {
        return return_class;
    }

    public void setReturn_class(String return_class) {
        this.return_class = return_class;
    }

    public String getClassification_g() {
        return classification_g;
    }

    public void setClassification_g(String classification_g) {
        this.classification_g = classification_g;
    }

    public String getClassification_b() {
        return classification_b;
    }

    public void setClassification_b(String classification_b) {
        this.classification_b = classification_b;
    }
}
