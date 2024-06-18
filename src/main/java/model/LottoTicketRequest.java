package model;

import java.util.ArrayList;
import java.util.List;

public class LottoTicketRequest {
    private final List<LottoTicket> manuallyCreatedTickets;
    private final int purchasePrice;

    public LottoTicketRequest(int purchasePrice) {
        this(new ArrayList<>(), purchasePrice);
    }

    public LottoTicketRequest(List<LottoTicket> manuallyCreatedTickets, int purchasePrice) {
        if(purchasePrice<0){
            throw new IllegalArgumentException("The purchase price cannot be negative");
        }
        if(manuallyCreatedTickets.size()>purchasePrice/LottoTicketGenerator.LOTTO_PRICE){
            throw new IllegalArgumentException("purchase price is not enough");
        }
        this.manuallyCreatedTickets = manuallyCreatedTickets;
        this.purchasePrice = purchasePrice;
    }

    public List<LottoTicket> getManuallyCreatedTickets() {
        return manuallyCreatedTickets;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }
}
