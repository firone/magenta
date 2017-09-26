package org.magenta.core.automagic.generation;

import org.magenta.DataKey;
import org.magenta.NewFixtureFactory;
import org.magenta.core.GenerationStrategy;

import com.google.common.base.Optional;

public interface DynamicGeneratorFactory {

  public <D>  Optional<? extends GenerationStrategy<D>> buildGeneratorOf(DataKey<D> type, NewFixtureFactory fixture, DynamicGeneratorFactory dynamicGeneratorFactory);
}
