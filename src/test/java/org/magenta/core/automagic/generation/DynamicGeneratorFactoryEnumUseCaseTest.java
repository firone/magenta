package org.magenta.core.automagic.generation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.magenta.DataKey;
import org.magenta.NewFixtureFactory;
import org.magenta.Magenta;
import org.magenta.core.GenerationStrategy;

import com.google.common.collect.Lists;

public class DynamicGeneratorFactoryEnumUseCaseTest extends AbstractDynamicGeneratorFactoryTest {


  @Test
  public void testGenerationOfAValueObjectWithAnEnum() {
    // setup fixture
    NewFixtureFactory fixture = Magenta.newFixture();

    DynamicGeneratorFactory sut = buildDynamicGeneratorFactory();

    // exercise sut
    List<Car> actual = Lists.newArrayList();

    GenerationStrategy<Car> gen= sut.buildGeneratorOf(DataKey.of(Car.class),fixture, sut).get();

    for (int i = 0; i < 3; i++) {

      actual.add(gen.generate(fixture));
    }

    // verify outcome
    assertThat(actual).extracting("color").containsExactly(Color.RED, Color.BLUE, Color.GREEN);

  }

  public static enum Color {

    RED, BLUE, GREEN;

  }

  public static class Car {

    private Color color;

  }
}
