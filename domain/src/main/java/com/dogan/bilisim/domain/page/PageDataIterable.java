package com.dogan.bilisim.domain.page;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class PageDataIterable<T,E> implements Iterable<T>, Iterator<T> {

  private final FetchFunction<T,E> function;
  private final int fetchSize;

  private List<T> currentItems;
  private int currentIdx;
  private boolean hasNextPack;
  private PageLink nextPackLink;
  private boolean initialized;
  private final E metaData;

  public PageDataIterable(FetchFunction<T, E> function, int fetchSize, E metaData) {
    super();
    this.function = function;
    this.fetchSize = fetchSize;
    this.metaData = metaData;
  }

  @Override
  public Iterator<T> iterator() {
    return this;
  }

  @Override
  public boolean hasNext() {
    if (!initialized) {
      fetch(new PageLink(fetchSize));
      initialized = true;
    }
    if (currentIdx == currentItems.size()) {
      if (hasNextPack) {
        fetch(nextPackLink);
      }
    }
    return currentIdx < currentItems.size();
  }

  private void fetch(PageLink link) {
    AdvancePageData<T,E> pageData = function.fetch(link);
    currentIdx = 0;
    currentItems = pageData.getData();
    hasNextPack = pageData.hasNext();
    nextPackLink = link.nextPageLink();
  }

  @Override
  public T next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    return currentItems.get(currentIdx++);
  }

  public interface FetchFunction<T,E> {

    AdvancePageData<T,E> fetch(PageLink link);
  }
}
