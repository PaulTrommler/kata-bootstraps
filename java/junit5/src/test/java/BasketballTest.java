import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BasketballTest {
    int scoreTeam1;
    int scoreTeam2;

    @Test
    void testInitial() {
        assertThat(getScore()).isEqualToIgnoringCase("000:000");
        scoreTeamA1();
        assertThat(getScore()).isEqualToIgnoringCase("001:000");
        scoreTeamB1();
        assertThat(getScore()).isEqualToIgnoringCase("001:001");
    }

    private void scoreTeamB1() {
        scoreTeam2++;
    }

    private void scoreTeamA1() {
        scoreTeam1++;
    }

    private String getScore() {
        return getTeamScoreAsString(scoreTeam1)+":" + getTeamScoreAsString(scoreTeam2);
    }

    private String getTeamScoreAsString(int score) {
        if(score<=0) {
            return "000";
        }
        if(score < 10) {
            return "00" + String.valueOf(score);
        }
        return "000";
    }

}
