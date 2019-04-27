import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketballTest {

    int scoreTeam1 = 0;
    private int scoreTeam2 = 0;

    @Test
    void test() {
        assertThat(getScore()).isEqualToIgnoringCase("000:000");
    }

    @Test
    void testA1() {
        scoreTeamA1();
        assertThat(getScore()).isEqualToIgnoringCase("001:000");
    }

    @Test
    void testA2() {
        scoreTeamA2();
        assertThat(getScore()).isEqualToIgnoringCase("002:000");
    }

    @Test
    void testA3() {
        scoreTeamA3();
        assertThat(getScore()).isEqualToIgnoringCase("003:000");
    }

    private void scoreTeamA3() {
        scoreTeamAbyX(3);
    }

    private void scoreTeamA1() {
        scoreTeamAbyX(1);
    }

    private void scoreTeamA2() {
        scoreTeamAbyX(2);
    }

    private void scoreTeamAbyX(int i) {
        scoreTeam1 += i;
    }

    @Test
    void test3() {
        scoreTeamB1();
        assertThat(getScore()).isEqualToIgnoringCase("000:001");
    }

    private void scoreTeamB1() {
        scoreTeam2++;
    }

    @Test
    void nullenAuffuellen() {
        assertThat(fillNulls(1)).isEqualToIgnoringCase("001");
        assertThat(fillNulls(55)).isEqualToIgnoringCase("055");
        assertThat(fillNulls(155)).isEqualToIgnoringCase("155");
    }

    private String fillNulls(int i) {
        if (i < 10) {
            return "00" + i;
        } else if (i < 100) {
            return "0" + i;
        }
        return String.valueOf(i);
    }



    private String getScore() {
        return fillNulls(scoreTeam1) + ":" + fillNulls(scoreTeam2);
    }

}
