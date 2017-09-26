package org.magenta;

import org.magenta.core.FixtureContext;
import org.magenta.core.GenerationStrategyFactory;
import org.magenta.core.RestrictionHelper;
import org.magenta.core.automagic.generation.DynamicGeneratorFactory;
import org.magenta.events.DataGenerated;
import org.magenta.random.FluentRandom;

import com.google.common.eventbus.Subscribe;

@Deprecated
public class FixtureFactory<T extends DataSpecification> extends NewFixtureFactory {

  private T spec;
  private DataStoreProvider provider;

  FixtureFactory(org.magenta.NewFixtureFactory parent, GenerationStrategyFactory generationStrategyBuilder, DynamicGeneratorFactory generatorFactory,
      FixtureContext context) {
    super(parent, generationStrategyBuilder, generatorFactory, context);

  }

  public FixtureFactory(org.magenta.NewFixtureFactory factory, T spec) {
    super(factory);
    this.spec = spec;
  }

  @Deprecated
  public static <T extends DataSpecification> FixtureFactory<T> newRoot(String name, T spec, FluentRandom rnd) {
    return newRoot(spec);
  }

  public static <T extends DataSpecification> FixtureFactory<T> newRoot(T spec) {

    org.magenta.NewFixtureFactory factory = Magenta.newFixture();

    FixtureFactory<T> f = new FixtureFactory<>(factory, spec);
    f.newDataSetOf(spec);

    return f;
  }

  @Deprecated
  public T getSpecification() {
    return spec;
  }

  @Deprecated
  public DataStoreProvider getDataStoreProvider(){
    return provider;
  }

  @Deprecated
  public FixtureFactory<T> newNode() {
    return new FixtureFactory<>(this.newChild(), spec);
  }

  @Override
  public FixtureFactory<T> restrictTo(Object first, Object... theRest) {

    FixtureFactory<T> child = newNode();

    RestrictionHelper.applyRestrictions(child, first, theRest);
    return child;
  }

  public void setDataStoreProvider(DataStoreProvider datastoreProvider, boolean persistent) {

    this.provider = datastoreProvider;
    if (persistent) {
      Magenta.eventBus().register(this);
    } else {
      Magenta.eventBus().unregister(this);
    }

  }

  @Subscribe
  public void onNewGeneratedObject(DataGenerated event) {

    DataStore store = this.provider.get(event.getKey());

    if (store != null) {
      store.persist(event.getData());
    }
  }

  public FluentRandom getRandomizer() {
    return FluentRandom.singleton();
  }
}
