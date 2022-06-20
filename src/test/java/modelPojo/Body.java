package modelPojo;

import java.util.List;

public class Body {
    private List<PaymentItem> items;

    public Body(List<PaymentItem> items) {
        this.items = items;
    }

    public void setItems(List<PaymentItem> items) {
        this.items = items;
    }

    public List<PaymentItem> getItems() {
        return items;
    }
}