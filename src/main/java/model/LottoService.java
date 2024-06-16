package model;

import java.util.List;

public class LottoService {

    private WinningLotto winningLotto;

    public List<LottoTicket> createTickets(LottoTicketRequest request) {
        return LottoTicketGenerator.generateTickets(request);
    }

    public void setWinningLotto(WinningLotto winningLotto) {
        this.winningLotto = winningLotto;
    }
}
