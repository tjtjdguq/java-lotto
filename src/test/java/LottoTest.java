import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LottoTest {

    @Test
    @DisplayName("로또번호는 1~45 ")
    public void test1() {
        assertThat(new LottoNumber().getNumber()).isBetween(1, 45);
    }

    @Test
    @DisplayName("로또 티켓은 6개의 로또번호로 구성")
    public void test2() {
        assertThat(new LottoTicket().getLottoNumbers().size()).isEqualTo(6);
        assertThat(new LottoTicket(1, 2, 3, 4, 5, 6).getLottoNumbers().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("로또번호는 중복되지 않음.")
    public void test3() {
        assertThat(assertThrows(IllegalArgumentException.class, () -> new LottoTicket(1, 1, 3, 4, 5, 6)).getMessage())
                .isEqualTo("number of lottoNumbers should be 6");
    }

    @Test
    @DisplayName("로또 당첨금은 하기 규칙에 따라 차등 지급")
    public void test4() {

        //when
        LottoTicket ticket = new LottoTicket(1, 2, 3, 4, 5, 7);
        WinningLotto winningLotto = new WinningLotto(new LottoTicket(1, 2, 3, 4, 5, 8), new LottoNumber(7));

        //then
        assertThat(winningLotto.calcPrizeMoney(ticket)).isEqualTo(PrizeMoney.ThirtyM);

    }
}
