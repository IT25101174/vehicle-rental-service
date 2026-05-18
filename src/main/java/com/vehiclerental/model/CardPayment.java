package com.vehiclerental.model;

public class CardPayment extends Payment {

    private String cardNumber;
    private String transactionId;

    public CardPayment(int id, int bookingId, double amount, String date, String status, String cardNumber, String transactionId) {
        super(id, bookingId, amount, date, status, "card");
        this.cardNumber = cardNumber;
        this.transactionId = transactionId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // Encapsulated information hiding: mask the 16-digit card
    public String getMaskedCardNumber() {
        if (cardNumber == null || cardNumber.isEmpty()) return "****";
        String clean = cardNumber.replace("-", "").replace(" ", "");
        if (clean.length() < 4) return "****";
        return "**** **** **** " + clean.substring(clean.length() - 4);
    }

    @Override
    public String generateReceipt() {
        return "💳 Card Payment processed successfully via Secure Gateway. Card: " + getMaskedCardNumber() + " (Transaction Ref: " + transactionId + ")";
    }
}
