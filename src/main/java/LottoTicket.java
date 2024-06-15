import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoTicket {
    private static final int LOTTO_NUMBER_COUNT = 6;
    private List<LottoNumber> lottoNumbers;

    public LottoTicket() {
        lottoNumbers = IntStream.rangeClosed(1, 45)
                .mapToObj(LottoNumber::new)
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(lottoNumbers);
        lottoNumbers = lottoNumbers.stream().limit(LOTTO_NUMBER_COUNT).collect(Collectors.toList());
    }

    public LottoTicket(int...  lottoNumbers) {
        if (Arrays.stream(lottoNumbers).mapToObj(LottoNumber::new).collect(Collectors.toSet()).size() != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException("number of lottoNumbers should be " + LOTTO_NUMBER_COUNT);
        }
        this.lottoNumbers = Arrays.stream(lottoNumbers).mapToObj(LottoNumber::new).collect(Collectors.toList());
    }

    public List<LottoNumber> getLottoNumbers() {
        return lottoNumbers;
    }
}
