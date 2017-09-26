package org.magenta;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class FixtureFactoryAutoMagicallyGeneratePrimitiveTest {

  @Test
  public void auto_magically_generate_string(){
    //setup fixture
    NewFixtureFactory sut = createRootNewFixtureFactory();

    //exercise sut
    sut.newDataSet(String.class).autoMagicallyGenerated(5);

    //verify outcome
    List<String> actual = sut.dataset(String.class).list();


    assertThat(actual).hasSize(5).doesNotContainNull();

  }

  @Test
  public void auto_magically_generate_integer(){
    //setup fixture
    NewFixtureFactory sut = createRootNewFixtureFactory();

    //exercise sut
    sut.newDataSet(Integer.class).autoMagicallyGenerated(5);

    //verify outcome
    List<Integer> actual = sut.dataset(Integer.class).list();


    assertThat(actual).hasSize(5).doesNotContainNull();

  }



  private NewFixtureFactory createRootNewFixtureFactory() {
    return Magenta.newFixture();
  }
}
