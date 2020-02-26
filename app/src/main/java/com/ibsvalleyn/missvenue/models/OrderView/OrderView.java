
package com.ibsvalleyn.missvenue.models.OrderView;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderView {

    @SerializedName("payment_methods")
    @Expose
    private List<PaymentMethod> paymentMethods = null;
    @SerializedName("richContent")
    @Expose
    private List<RichContent> richContent = null;

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<RichContent> getRichContent() {
        return richContent;
    }

    public void setRichContent(List<RichContent> richContent) {
        this.richContent = richContent;
    }

}
