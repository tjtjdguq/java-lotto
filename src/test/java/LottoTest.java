import model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LottoTest {

    @Test
    @DisplayName("로또번호는 1~45 ")
    public void test1() {
        assertThat(new LottoNumber().getNumber()).isBetween(LottoNumber.MIN_LOTTO_NUMBER, LottoNumber.MAX_LOTTO_NUMBER);
        assertThat(assertThrows(IllegalArgumentException.class, () -> new LottoNumber(46)).getMessage())
                .isEqualTo("Lotto number should be between 1 and 45");
    }

    @Test
    @DisplayName("로또 티켓은 6개의 로또번호로 구성")
    public void test2() {
        assertThat(new LottoTicket().getLottoNumbers().size()).isEqualTo(LottoTicket.LOTTO_NUMBER_COUNT);
        assertThat(new LottoTicket(1, 2, 3, 4, 5, 6).getLottoNumbers().size()).isEqualTo(LottoTicket.LOTTO_NUMBER_COUNT);
        assertThat(assertThrows(IllegalArgumentException.class, () -> new LottoTicket(1, 2, 3, 4, 5, 6, 7)).getMessage())
                .isEqualTo("number of lottoNumbers should be " + LottoTicket.LOTTO_NUMBER_COUNT);
    }

    @Test
    @DisplayName("로또번호는 중복되지 않음.")
    public void test3() {
        assertThat(assertThrows(IllegalArgumentException.class, () -> new LottoTicket(1, 1, 3, 4, 5, 6)).getMessage())
                .isEqualTo("number of lottoNumbers should be 6");
        assertThat(assertThrows(IllegalArgumentException.class
                , () -> new WinningLotto(new LottoTicket(1, 1, 3, 4, 5, 6), new LottoNumber(6)))
                .getMessage())
                .isEqualTo("number of lottoNumbers should be 6");
    }

    @Test
    @DisplayName("6개 모두 일치 - 2,000,000,000원")
    public void test4() {

        //when
        LottoTicket ticket = new LottoTicket(1, 2, 3, 4, 5, 6);
        WinningLotto winningLotto = new WinningLotto(new LottoTicket(1, 2, 3, 4, 5, 6), new LottoNumber(7));

        //then
        assertThat(winningLotto.calcPrizeMoney(ticket)).isEqualTo(PrizeMoney.TwoB);

    }

    @Test
    @DisplayName("5개 일치 + 보너스 번호 일치 - 30,000,000원")
    public void test5() {

        //when
        LottoTicket ticket = new LottoTicket(1, 2, 3, 4, 5, 6);
        WinningLotto winningLotto = new WinningLotto(new LottoTicket(1, 2, 3, 4, 5, 7), new LottoNumber(6));

        //then
        assertThat(winningLotto.calcPrizeMoney(ticket)).isEqualTo(PrizeMoney.ThirtyM);

    }

    @Test
    @DisplayName("5개 일치 - 150,000원")
    public void test6() {

        //when
        LottoTicket ticket = new LottoTicket(1, 2, 3, 4, 5, 6);
        WinningLotto winningLotto = new WinningLotto(new LottoTicket(1, 2, 3, 4, 5, 7), new LottoNumber(8));

        //then
        assertThat(winningLotto.calcPrizeMoney(ticket)).isEqualTo(PrizeMoney.OneFiftyK);

    }

    @Test
    @DisplayName("4개 일치 - 50,000원")
    public void test7() {

        //when
        LottoTicket ticket = new LottoTicket(1, 2, 3, 4, 5, 6);
        WinningLotto winningLotto = new WinningLotto(new LottoTicket(1, 2, 3, 4, 7, 8), new LottoNumber(9));

        //then
        assertThat(winningLotto.calcPrizeMoney(ticket)).isEqualTo(PrizeMoney.FiftyK);

    }

    @Test
    @DisplayName("3개 일치 - 5,000원")
    public void test8() {

        //when
        LottoTicket ticket = new LottoTicket(1, 2, 3, 4, 5, 6);
        WinningLotto winningLotto = new WinningLotto(new LottoTicket(1, 2, 3, 7, 8, 9), new LottoNumber(10));

        //then
        assertThat(winningLotto.calcPrizeMoney(ticket)).isEqualTo(PrizeMoney.FiveK);

    }

    @ParameterizedTest
    @ValueSource(ints = {100, 50033, 2030})
    @DisplayName("로또 티켓 가격은 1000원이다.=> 로또 티켓 가격 단위에 맞게 금액을 입력 받는다.")
    public void test9(int number) {

        assertThat(assertThrows(IllegalArgumentException.class, () -> LottoTicketGenerator.generateTickets(new LottoTicketRequest(number))).getMessage())
                .isEqualTo("Please enter in increments of " + LottoTicketGenerator.LOTTO_PRICE + " won.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 50000, 908000})
    @DisplayName("로또 티켓 번호는 금액 입력 시 자동 생성한다.")
    public void test10(int price) {
        assertThat(new LottoService().createTickets(new LottoTicketRequest(price)).size()).isEqualTo(price / LottoTicketGenerator.LOTTO_PRICE);
    }

    @Test
    @DisplayName("로또 티켓 번호는 수동 생성할 수 있다.")
    public void test11() {
        //given
        int[][] tickets = new int[][]{{1, 2, 3, 4, 5, 6},
                {7, 8, 9, 10, 11, 12},
                {13, 14, 15, 16, 17, 18}
        };

        //when
        List<LottoTicket> ticketList = Arrays.stream(tickets).map(LottoTicket::new).collect(Collectors.toList());
        //then
        assertThat(ticketList.size()).isEqualTo(tickets.length);

    }

    @Test
    @DisplayName("지불한 금액보다 많은 티켓을 생성할 수 없다")
    public void test12() {
        //given
        int[][] tickets = new int[][]{{1, 2, 3, 4, 5, 6},
                {7, 8, 9, 10, 11, 12},
                {13, 14, 15, 16, 17, 18}
        };
        List<LottoTicket> ticketList = Arrays.stream(tickets).map(LottoTicket::new).collect(Collectors.toList());
        int price=LottoTicketGenerator.LOTTO_PRICE*(ticketList.size()-1);

        //when
        assertThat(
                assertThrows(IllegalArgumentException.class, () -> LottoTicketGenerator.generateTickets(new LottoTicketRequest(ticketList, price))).getMessage()
        ).isEqualTo("purchase price is not enough");
    }

    @Test
    @DisplayName("로또 티켓 번호는 금액 및 수동 생성할 티켓 수 입력 시 나머지는 자동 생성한다.")
    public void test13() {
        //given
        int[][] tickets = new int[][]{{1, 2, 3, 4, 5, 6},
                {7, 8, 9, 10, 11, 12},
                {13, 14, 15, 16, 17, 18}
        };
        List<LottoTicket> manuallyCreatedTickets = Arrays.stream(tickets).map(LottoTicket::new).collect(Collectors.toList());
        int numberOfAutoCreatedTickts=1;
        int price=LottoTicketGenerator.LOTTO_PRICE*(manuallyCreatedTickets.size()+numberOfAutoCreatedTickts);

        //when
        List<LottoTicket> createdTickets= LottoTicketGenerator.generateTickets(new LottoTicketRequest(manuallyCreatedTickets,price));
        createdTickets.removeAll(manuallyCreatedTickets);

        assertThat(createdTickets.size()).isEqualTo(numberOfAutoCreatedTickts);

    }

}
