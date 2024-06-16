package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LottoTicketGenerator {
    public static final int LOTTO_PRICE = 1000;

    public static List<LottoTicket> generateTickets(LottoTicketRequest request) {
        if (request.getPurchasePrice() % LOTTO_PRICE != 0 || request.getPurchasePrice() / LOTTO_PRICE <= 0) {
            throw new IllegalArgumentException("Please enter in increments of " + LOTTO_PRICE + " won.");
        }
        List<LottoTicket> tickets = new ArrayList<>(request.getManuallyCreatedTickets());
        Stream.generate(LottoTicket::new).limit((request.getPurchasePrice() / LOTTO_PRICE)-request.getManuallyCreatedTickets().size()).forEach(tickets::add);
        return tickets;
    }

}
