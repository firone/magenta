package org.magenta.core.automagic.generation.provider;

import org.magenta.Fixture;
import org.magenta.FixtureFactory;
import org.magenta.core.SimpleGenerationStrategy;
import org.magenta.core.GenerationStrategy;
import org.magenta.core.GenerationStrategyFactory;
import org.magenta.core.automagic.generation.DynamicGeneratorFactory;


import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.reflect.TypeToken;

public class ConditionalGeneratorFactory implements DynamicGeneratorFactory{

  
  private final Predicate<TypeToken<?>> spec;
  private final Function<? super Fixture,?> generator;
  
  
  // the injector could be used to inject into the generator
  
  public <D> ConditionalGeneratorFactory(Predicate<TypeToken<D>> spec, Supplier<D> generator) {
    super();
    this.spec = (Predicate)spec;
    this.generator = Functions.forSupplier(generator);
  }

  @Override
  public <D> Optional<GenerationStrategy<D>> buildGeneratorOf(TypeToken<D> type, FixtureFactory fixture,
      DynamicGeneratorFactory dynamicGeneratorFactory) {
    
    Function<Fixture,D> g = (Function)generator;
    
    return spec.apply(type) ? Optional.of(new SimpleGenerationStrategy<D>(g,f -> 1)) : Optional.absent();
  }

}
