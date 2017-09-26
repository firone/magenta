package org.magenta;

import org.magenta.core.DataSetImpl;

@Deprecated
public class QualifiedDataSet<D> extends DataSetImpl<D> {

  private DataKey<D> key;

  public QualifiedDataSet(DataKey<D> key, DataSupplier<D> datasetSupplier, boolean postEventEnabled) {
    super(datasetSupplier, postEventEnabled);
    this.key= key;
  }

  public DataKey<D> getKey() {
    return this.key;
  }

}
