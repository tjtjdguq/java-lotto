package model;

import java.util.List;
import java.util.stream.Collectors;

public class WinningLotto {
    private final LottoTicket winNumbers;
    private final LottoNumber bonusNumber;

    public WinningLotto(LottoTicket winNumbers, LottoNumber bonusNumber) {
        if(winNumbers.getLottoNumbers().contains(bonusNumber)){
            throw new IllegalArgumentException("winning lottos numbers are not unique");
        }
        this.winNumbers = winNumbers;
        this.bonusNumber = bonusNumber;
    }

    public PrizeMoney calcPrizeMoney(LottoTicket ticket) {
        int matchCnt = matchCount(ticket);
        if (matchCnt == 6) {
            return PrizeMoney.TwoB;
        } else if (matchCnt == 5 && hasBonusNumber(ticket)) {
            return PrizeMoney.ThirtyM;
        } else if (matchCnt == 5) {
            return PrizeMoney.OneFiftyK;
        } else if (matchCnt == 4) {
            return PrizeMoney.FiftyK;
        } else if (matchCnt == 3) {
            return PrizeMoney.FiveK;
        } else {
            return PrizeMoney.Zero;
        }

    }

    private int matchCount(LottoTicket ticket) {
        List<LottoNumber> lottoNumbers = ticket.getLottoNumbers().stream().map(LottoNumber::clone).collect(Collectors.toList());
        lottoNumbers.retainAll(winNumbers.getLottoNumbers());
        return lottoNumbers.size();
    }

    private boolean hasBonusNumber(LottoTicket ticket) {
        return ticket.getLottoNumbers().contains(bonusNumber);
    }
}
