package org.magenta;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.reflect.TypeToken;

public class FixtureFactoryComposedOfTest {

  @Test
  public void testComposedOfWithAnIterable(){

    Integer[] expectedNumbers = new Integer[]{1,2,3,4,5,6,7};
    Iterable<Integer>   integers = Arrays.asList(expectedNumbers);
    NewFixtureFactory fixtures = createRootNewFixtureFactory();

    DataKey<Integer> key = DataKey.of(Integer.class);

    //exercise sut
    fixtures.newDataSet(key).composedOf(integers);
    DataSet<Integer> actual = fixtures.dataset(key);

    //verify outcome
    assertThat(actual).containsExactly(expectedNumbers);
    assertThat(actual.isConstant()).isTrue();
    assertThat(actual.isEmpty()).isFalse();
    assertThat(actual.isGenerated()).isFalse();
    assertThat(actual.getSize()).isEqualTo(expectedNumbers.length);
    assertThat(actual.getType()).isEqualTo(key.getType());
    assertThat(actual.any()).isIn(expectedNumbers);


  }

  @Test
  public void testTransformedComposition(){
    String[] expectedNumbers = new String[]{"1","2","3","4","5","6","7"};
    Iterable<Integer>   integers = Arrays.asList(1,2,3,4,5,6,7);

    NewFixtureFactory fixtures = createRootNewFixtureFactory();

    DataKey<String> key = DataKey.of(String.class);

    //exercise sut
    fixtures.newDataSet(key).transformed((Integer i)->i.toString()).composedOf(integers);
    DataSet<String> actual = fixtures.dataset(key);
    assertThat(actual).containsExactly(expectedNumbers);
    assertThat(actual.isConstant()).isTrue();
    assertThat(actual.isEmpty()).isFalse();
    assertThat(actual.isGenerated()).isFalse();
    assertThat(actual.getSize()).isEqualTo(expectedNumbers.length);
    assertThat(actual.getType()).isEqualTo(key.getType());
    assertThat(actual.any()).isIn(expectedNumbers);

  }

  @Test
  public void testImplicitComposition(){

    Integer[] expectedNumbers = new Integer[]{1,2,3,4,5,6,7};
    NewFixtureFactory fixtures = createRootNewFixtureFactory();

    DataKey<Number> key = DataKey.of(Number.class);

    //exercise sut
    fixtures.newDataSetOf(expectedNumbers);
    DataSet<Number> actual = fixtures.dataset(key);

    //verify outcome
    assertThat(actual).containsExactly(expectedNumbers);
    assertThat(actual.isConstant()).isTrue();
    assertThat(actual.isEmpty()).isFalse();
    assertThat(actual.isGenerated()).isFalse();
    assertThat(actual.getSize()).isEqualTo(expectedNumbers.length);
    assertThat(actual.getType()).isEqualTo(key.getType());
    assertThat(actual.any()).isIn(expectedNumbers);


  }

  @Test
  public void testComposedOfTypeToken(){

    List<Integer>[] expectedSets = new List[]{Arrays.asList(1,2,3), Arrays.asList(4,5,6), Arrays.asList(7,8,9)};

    NewFixtureFactory fixtures = createRootNewFixtureFactory();

    DataKey<List<Integer>> key = DataKey.of(new TypeToken<List<Integer>>(){});

    //exercise sut
    fixtures.newDataSet(key).composedOf(expectedSets);

    DataSet<List<Integer>> actual = fixtures.dataset(key);

    //verify outcome
    assertThat(actual).containsExactly(expectedSets);
    assertThat(actual.isConstant()).isTrue();
    assertThat(actual.isEmpty()).isFalse();
    assertThat(actual.isGenerated()).isFalse();
    assertThat(actual.getSize()).isEqualTo(expectedSets.length);
    assertThat(actual.getType()).isEqualTo(key.getType());
    assertThat(actual.any()).isIn(expectedSets);


  }

  private NewFixtureFactory createRootNewFixtureFactory() {
    return Magenta.newFixture();
  }
}
