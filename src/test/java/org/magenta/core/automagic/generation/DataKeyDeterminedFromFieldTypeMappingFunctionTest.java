package org.magenta.core.automagic.generation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.magenta.DataKey;

public class DataKeyDeterminedFromFieldTypeMappingFunctionTest {

  @Test
  public void testMappingOfSimpleField() throws NoSuchFieldException, SecurityException{

    //setup fixture
    DataKeyDeterminedFromFieldTypeMappingFunction sut = new DataKeyDeterminedFromFieldTypeMappingFunction();
    DataKey<String> expected = DataKey.of("org.magenta.core.automagic.generation.DataKeyDeterminedFromFieldTypeMappingFunctionTest$FakeObject.string", String.class);

    //exercise sut
    DataKey actual = sut.apply(FakeObject.class.getDeclaredField("string"));

    //verify outcome
    assertThat(actual).isEqualTo(expected);

  }

  public static class FakeObject{
    private String string;
  }
}