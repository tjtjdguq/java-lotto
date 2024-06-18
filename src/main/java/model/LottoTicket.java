package model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoTicket {
    public static final int LOTTO_NUMBER_COUNT = 6;
    private final Set<LottoNumber> lottoNumbers;

    public LottoTicket() {
        List<LottoNumber> lottoNumberList = IntStream.rangeClosed(1, 45)
                .mapToObj(LottoNumber::new)
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(lottoNumberList);
        lottoNumbers = lottoNumberList.stream().limit(LOTTO_NUMBER_COUNT).collect(Collectors.toSet());
    }

    public LottoTicket(int... lottoNumbers) {
        if (Arrays.stream(lottoNumbers).mapToObj(LottoNumber::new).collect(Collectors.toSet()).size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException("number of lottoNumbers should be " + LOTTO_NUMBER_COUNT);
        }
        this.lottoNumbers = Arrays.stream(lottoNumbers).mapToObj(LottoNumber::new).collect(Collectors.toSet());
    }

    public Set<LottoNumber> getLottoNumbers() {
        return lottoNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LottoTicket that = (LottoTicket) o;
        List<LottoNumber> clonedLottoNumbers = lottoNumbers.stream().map(LottoNumber::clone).collect(Collectors.toList());
        return clonedLottoNumbers.removeAll(that.lottoNumbers) && clonedLottoNumbers.isEmpty();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lottoNumbers);
    }
}
