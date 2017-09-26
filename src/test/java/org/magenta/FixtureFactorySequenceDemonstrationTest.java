package org.magenta;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.magenta.testing.domain.playing.cards.Card;
import org.magenta.testing.domain.playing.cards.Kind;

public class FixtureFactorySequenceDemonstrationTest {

  @Test
  public void testGenerateFullCardDeck(){
    NewFixtureFactory fixtures = createCardFixtures();

    //test
    List<Card> cards= fixtures.dataset(Card.class).list();

    //exercise
    assertThat(cards).extracting("kind",Kind.class).containsOnly(Kind.values());
    assertThat(cards).extracting("value",Integer.class).containsOnly(1,2,3,4,5,6,7,8,9,10,11,12,13);
    assertThat(cards).hasSize(52);
  }

  @Test
  public void testGenerateAllFaces(){
    NewFixtureFactory fixtures = createCardFixtures();

    //test
    List<Card> cards= fixtures.restrictTo(11,12,13).dataset(Card.class).list();

    //exercise

    assertThat(cards).extracting("kind",Kind.class).containsOnly(Kind.values());
    assertThat(cards).extracting("value",Integer.class).containsExactly(11,12,13,11,12,13,11,12,13,11,12,13);
    assertThat(cards).hasSize(12);

  }

  @Test
  public void testGenerateAllReds(){
    //setup
    NewFixtureFactory fixtures = createCardFixtures();

    //test
    List<Card> cards= fixtures.restrictTo(Kind.HEART,Kind.DIAMOND).dataset(Card.class).list();

    //exercise

    assertThat(cards).extracting("kind",Kind.class).containsOnly(Kind.HEART,Kind.DIAMOND);
    assertThat(cards).extracting("value",Integer.class).containsOnly(1,2,3,4,5,6,7,8,9,10,11,12,13);
    assertThat(cards).hasSize(26);

  }

  private NewFixtureFactory createCardFixtures() {
    NewFixtureFactory fixtures = Magenta.newFixture();
    fixtures.newDataSetOf(Kind.values());
    fixtures.newDataSetOf(1,2,3,4,5,6,7,8,9,10,11,12,13);
    fixtures.newDataSet(Card.class).autoMagicallyGenerated();
    return fixtures;
  }


}
